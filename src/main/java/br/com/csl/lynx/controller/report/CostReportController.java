package br.com.csl.lynx.controller.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintRipException;
import br.com.csl.lynx.facade.RipFacade;
import br.com.csl.lynx.generic.AbstractRipInfo;
import br.com.csl.lynx.handler.MovementHandler;
import br.com.csl.lynx.handler.PrintRipHandler;
import br.com.csl.lynx.model.CostReport;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.ReportTarget;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.RipCostReport;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.model.ServicoReport;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Zona;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class CostReportController extends AbstractRipInfo {

	private static final long serialVersionUID = 1L;
	
	private List<Date> oldReports;
	
	private Date oldReport;
	
	private List<ReportTarget> target;
	
	private Problem problema;
	
	private String prioridade;
	private List<String> zonas;
	private List<String> bairros;
	private List<String> conjuntos;
	
	private Date dataInicio;
	private Date dataFim;
	
	private Boolean filterStatus;
	private Boolean checkPass;
	
	private String password;
	
	private List<Rip> rips;
	
	private List<RipCostReport> ripCostReports;
	private List<ServicoReport> servicoReports;
	private CostReport costReport;
	
	@ManagedProperty("#{movementHandler}")
	private MovementHandler movementHandler;
	
	@ManagedProperty("#{printRipHandler}")
	private PrintRipHandler printRipHandler;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	@ManagedProperty("#{ripFacade}")
	private RipFacade ripFacade;
	
	@PostConstruct
	public void init() {
		clear();
		
		oldReports = new ArrayList<>();
		
		List<String> dates = new ArrayList<>(); 
		
		for (Movimentacao mov : movimentacaoService.list(Restrictions.eq("movimento", Movement.PAY))) {
			if (!dates.contains(CalendarUtil.dateToString(mov.getData().getTime()))) {
				dates.add(CalendarUtil.dateToString(mov.getData().getTime()));
				oldReports.add(CalendarUtil.getStripTime(mov.getData()).getTime());
			}
		}
	}

	public void clear() {
		problema = null;
		
		prioridade = "";		
		
		dataInicio = null;
		dataFim = null;
		oldReport = null;
		
		password = "";
		
		filterStatus = false;
		checkPass = true;
		
		bairros = new ArrayList<>();
		zonas = new ArrayList<>();
		conjuntos = new ArrayList<>();
		
		clearReport();
	}
	
	public void fetchOldReports() {
		if (oldReport == null) {
			addFacesErrorMessage("Relatório inválido");
			return;
		}
		
		target = new ArrayList<>();
		List<Criterion> filters = new ArrayList<>();
		
		clearReport();
		filterStatus = false;
		
		Calendar endCal = CalendarUtil.getCalendar(oldReport);
		endCal.add(Calendar.DATE, 1);
		
		filters.add(Restrictions.between("data", CalendarUtil.getCalendar(oldReport), endCal));
		
		filters.add(Restrictions.eq("movimento", Movement.PAY));
		
		target.add(new ReportTarget("Relatório gerado no dia", CalendarUtil.dateToYYYYString(oldReport)));
		
		Criterion fullRipFilter = Restrictions.eq("status", RipStatus.PAYED);
		
		filterStatus = true;
		
		rips = ripFacade.getRipWithMovements(fullRipFilter, getConjunction(filters, null));
		
		if (rips == null || rips.isEmpty()) {
			filterStatus = false;
			addFacesErrorMessage("Nenhuma rip encontrada.", "O relatório não pôde ser gerado.");
			return;
		}
		
		populateReport();
		
	}
	
	public void clearReport() {
		rips = new ArrayList<>();
		target = new ArrayList<>();
		ripCostReports = new ArrayList<>();
		servicoReports = new ArrayList<>();
		costReport = new CostReport();
	}
	
	public void prepareReport() {
		List<Criterion> movFilters = new ArrayList<>();
		List<Criterion> ripFilters = new ArrayList<>();
		List<Criterion> location = new ArrayList<>();
		
		clearReport();
		filterStatus = false;
		
		movFilters.add(Restrictions.eq("movimento", Movement.CLOSE));
		ripFilters.add(Restrictions.eq("status", RipStatus.CLOSED));
		
		if (problema != null) {
			ripFilters.add(Restrictions.eq("tipoReclamacao", problema));
			target.add(new ReportTarget("Requisição", problema.getLabel()));
		}
		
		if (!prioridade.isEmpty()) {
			ripFilters.add(Restrictions.eq("prioridade", Integer.valueOf(prioridade)));
			target.add(new ReportTarget("Prioridade", prioridade));
		}
		
		if (!zonas.isEmpty()) {
			for (String zona : zonas) {
				location.add(Restrictions.eq("bairro.zona", Zona.valueOf(zona)));
				target.add(new ReportTarget("Zona", Zona.valueOf(zona).getLabel()));
			}
		}
		
		if (!bairros.isEmpty()) {
			for (String bairro : bairros) {
				location.add(Restrictions.eq("bairro.nome", bairro));
				target.add(new ReportTarget("Bairro", bairro));
			}
		}

		if (!conjuntos.isEmpty()) {
			for (String conjunto : conjuntos) {
				location.add(Restrictions.eq("conjunto.nome", conjunto));
				target.add(new ReportTarget("Conjunto", conjunto));
			}
		}
		
		if (dataFim == null && dataInicio != null) {
			movFilters.add(Restrictions.ge("data", CalendarUtil.getCalendar(dataInicio)));
			target.add(new ReportTarget("A partir de", CalendarUtil.dateToYYYYString(dataInicio)));
		} else if (dataFim != null && dataInicio == null) {
			Calendar endCal = CalendarUtil.getCalendar(dataFim);
			endCal.add(Calendar.DATE, 1);
			
			movFilters.add(Restrictions.lt("data", endCal));
			target.add(new ReportTarget("Até", CalendarUtil.dateToYYYYString(dataFim)));
		} else if (dataFim != null && dataInicio != null) {
			Calendar endCal = CalendarUtil.getCalendar(dataFim);
			endCal.add(Calendar.DATE, 1);
			
			movFilters.add(Restrictions.between("data", CalendarUtil.getCalendar(dataInicio), endCal));
			target.add(new ReportTarget("No período de", CalendarUtil.dateToYYYYString(dataInicio) + " até " + CalendarUtil.dateToYYYYString(dataFim)));
		}
		
		if (ripFilters.size() == 0 && location.size() == 0 && movFilters.size() == 0) {
			addFacesErrorMessage("Nenhum filtro foi aplicado");
			return;
		}
		
		Criterion fullRipFilter = getConjunction(ripFilters, location);
		
		filterStatus = true;
		
		rips = ripFacade.getRipWithMovements(fullRipFilter, getConjunction(movFilters, null));
		
		if (rips == null || rips.isEmpty()) {
			filterStatus = false;
			addFacesErrorMessage("Nenhuma rip encontrada.", "O relatório não pôde ser gerado.");
			return;
		}
		
		populateReport();
	}

	public void populateReport() {
		List<Servico> servicos = new ArrayList<>();
		costReport.setTarget(target);
		
		for (Rip rip : rips) {
			BigDecimal ripTotal = BigDecimal.ZERO; 
			
			RipCostReport ripCostReport = new RipCostReport(rip);
			
			ripCostReport.setServicos(rip.getQtdServicos());
			
			for (QtdServico qtdServico : ripCostReport.getServicos()) {
				qtdServico.setCost(qtdServico.getServico().getValor().multiply(new BigDecimal(qtdServico.getQtd())));
				ripTotal = ripTotal.add(qtdServico.getCost());
				
				if (!servicos.contains(qtdServico.getServico())) {
					ServicoReport servicoReport = new ServicoReport(qtdServico.getServico(), qtdServico.getQtd(), qtdServico.getCost());
					servicos.add(qtdServico.getServico());
					servicoReports.add(servicoReport);
				} else {
					for (ServicoReport aux : servicoReports) {
						if (aux.getServico().equals(qtdServico.getServico())) {
							aux.addQtd(qtdServico.getQtd());
							aux.addTotal(qtdServico.getCost());
						}
					}
				}
			}
			ripCostReport.setTotal(ripTotal);
			costReport.addTotal(ripTotal);
			ripCostReports.add(ripCostReport);
		}
		
		costReport.setRipCostReports(ripCostReports);
		costReport.setServicoReports(servicoReports);
		
	}
	
	public Conjunction getConjunction(List<Criterion> filters, List<Criterion> location) {
		Conjunction conjunction = new Conjunction();
		
		if (filters != null && filters.size() != 0)
		for (Criterion aux : filters) {
			conjunction.add(aux);
		}
		
		if (location != null && location.size() != 0) {
			Disjunction disjunction = Restrictions.disjunction();	
			
			for (Criterion aux : location) {
				disjunction.add(aux);
			}
			
			conjunction.add(disjunction);
		}

		return conjunction;
	}
	
	public StreamedContent print() {
		StreamedContent pdf = null;
		
		try {
			pdf = printRipHandler.printCostReport(costReport);
			
			ripFacade.payRipList(rips, getLoggedUser());
		} catch (PrintRipException e) {
			addFacesErrorMessage("Não foi possível gerar o arquivo!");
			e.printStackTrace();
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível registrar o pagamento de todas as rips!");
			e.printStackTrace();
		}
		
		checkPass = true;
		
		return pdf;
	}
	
	public void checkUser() {
		if (password == null) {
			return;
		}
		
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		
		if (passwordEncryptor.checkPassword(password, userSession.getUser().getPassword())) {
			addFacesInfoMessage("Dados corretos. Impressão liberada por 2 minutos.");
			checkPass = false;
		} else {
			addFacesErrorMessage("Senha incorreta!");
			password = "";
		}
	}

	public List<Date> getOldReports() {
		return oldReports;
	}

	public void setOldReports(List<Date> oldReports) {
		this.oldReports = oldReports;
	}

	public Date getOldReport() {
		return oldReport;
	}

	public void setOldReport(Date oldReport) {
		this.oldReport = oldReport;
	}

	public List<ReportTarget> getTarget() {
		return target;
	}

	public void setTarget(List<ReportTarget> target) {
		this.target = target;
	}

	public Problem getProblema() {
		return problema;
	}

	public void setProblema(Problem problema) {
		this.problema = problema;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public List<String> getZonas() {
		return zonas;
	}

	public void setZonas(List<String> zonas) {
		this.zonas = zonas;
	}

	public List<String> getBairros() {
		return bairros;
	}

	public void setBairros(List<String> bairros) {
		this.bairros = bairros;
	}

	public List<String> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(List<String> conjuntos) {
		this.conjuntos = conjuntos;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Boolean getFilterStatus() {
		return filterStatus;
	}

	public void setFilterStatus(Boolean filterStatus) {
		this.filterStatus = filterStatus;
	}

	public List<Rip> getRips() {
		return rips;
	}

	public void setRips(List<Rip> rips) {
		this.rips = rips;
	}

	public List<RipCostReport> getRipCostReports() {
		return ripCostReports;
	}

	public void setRipCostReports(List<RipCostReport> ripCostReports) {
		this.ripCostReports = ripCostReports;
	}

	public List<ServicoReport> getServicoReports() {
		return servicoReports;
	}

	public void setServicoReports(List<ServicoReport> servicoReports) {
		this.servicoReports = servicoReports;
	}

	public CostReport getCostReport() {
		return costReport;
	}

	public void setCostReport(CostReport costReport) {
		this.costReport = costReport;
	}

	public void setMovementHandler(MovementHandler movementHandler) {
		this.movementHandler = movementHandler;
	}

	public void setPrintRipHandler(PrintRipHandler printRipHandler) {
		this.printRipHandler = printRipHandler;
	}

	public Boolean getCheckPass() {
		return checkPass;
	}

	public void setCheckPass(Boolean checkPass) {
		this.checkPass = checkPass;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
	
	public void setRipFacade(RipFacade ripFacade) {
		this.ripFacade = ripFacade;
	}

}