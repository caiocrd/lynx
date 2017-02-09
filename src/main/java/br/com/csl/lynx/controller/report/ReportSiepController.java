package br.com.csl.lynx.controller.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.exception.PrintSiepException;
import br.com.csl.lynx.facade.EnderecoFacade;
import br.com.csl.lynx.facade.SiepFacade;
import br.com.csl.lynx.generic.AbstractSiepInfo;
import br.com.csl.lynx.handler.PrintSiepHandler;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.ProblemReport;
import br.com.csl.lynx.model.Report;
import br.com.csl.lynx.model.ReportTarget;
import br.com.csl.lynx.model.RipReport;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.model.RipReport;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.StatusReport;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Types;
import br.com.csl.lynx.support.Zona;
import br.com.csl.lynx.utils.CalendarUtil;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class ReportSiepController extends AbstractSiepInfo {

	private static final long serialVersionUID = 1L;
	
	private boolean zonaDisabled;
	
	private List<Bairro> bairroList;
	private List<Conjunto> conjuntoList;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	@ManagedProperty("#{printSiepHandler}")
	private PrintSiepHandler printSiepHandler;
	
	private List<ReportTarget> target;
	
	private Types type;
	private Problem problema;
	private RipStatus status;
	
	private String prioridade;
	private String posteBto;
	private List<String> zonas;
	private List<String> bairros;
	private List<String> conjuntos;
	private String logradouro;
	
	private String usuario;
	
	private String solicitante;
	
	private Date dataInicio;
	private Date dataFim;
	
	private Boolean filterStatus;
	
	private List<Siep> sieps;
	
	private List<ProblemReport> problemReports;
	private List<StatusReport> statusReports;
	private List<RipReport> ripReports;
	private Report report;
	
	private List<Usuario> usuarios;
	
	@ManagedProperty("#{usuarioService}")
	private DataService<Usuario> usuarioService;
	
	@ManagedProperty("#{enderecoFacade}")
	private EnderecoFacade enderecoFacade;
	
	@ManagedProperty("#{siepFacade}")
	private SiepFacade siepFacade;
	
	@PostConstruct
	public void init() {
		clear();
		
		boolean manager = false;
		
		List<Role> roles = userSession.getPermissions();
		for (Role aux : roles) {
			if ((aux.getName().equals(Zona.ZONA_NORTE.name()))
					|| (aux.getName().equals(Zona.ZONA_SUL.name()))
					|| (aux.getName().equals(Zona.ZONA_LESTE.name()))
					|| (aux.getName().equals(Zona.ZONA_OESTE.name()))) {
				zonas.add(aux.getName());
			} else if (aux.getName().equals(Permission.REGIAO.name()) ||
					aux.getName().equals(Permission.DIRECAO.name()) ||
					aux.getName().equals(Permission.CALLCENTER.name()) ||
					aux.getName().equals(Permission.PRESTADORA.name())) {
				manager = true;
				break;
			}
		}
		
		if (!manager) {
			if(zonas.size()>0) {
				zonaDisabled = true;
				zonas.get(0);
				bairroList = enderecoFacade.getBairroService().list("zona", Zona.valueOf(zonas.get(0)), null);
				conjuntoList = enderecoFacade.getConjuntoService().listAliased("bairro.zona", Zona.valueOf(zonas.get(0)), null);
			}else {
				addFacesErrorMessage("Nenhuma zona selecionada para este usuário!");
			}
		} else {
			zonaDisabled = false;
			bairroList = enderecoFacade.getBairroService().list();
			conjuntoList = enderecoFacade.getConjuntoService().list();
		}
		

	}

	public void clear() {
		type = null;
		problema = null;
		status = null;
		
		prioridade = "";		
		posteBto = "";
		logradouro = "";
		
		dataInicio = null;
		dataFim = null;
		
		usuario = "";
		solicitante = "";
		
		filterStatus = false;
		
		bairros = new ArrayList<>();
		
		if (!zonaDisabled)
			zonas = new ArrayList<>();
			
		conjuntos = new ArrayList<>();
		
		clearReport();
	}
	
	public void clearReport() {
		sieps = new ArrayList<>();
		target = new ArrayList<>();
		problemReports = new ArrayList<>();
		statusReports = new ArrayList<>();
		ripReports = new ArrayList<>();
		report = new Report();
	}
	
	public void createReport() {
		List<Criterion> movFilters = new ArrayList<>();
		List<Criterion> siepFilters = new ArrayList<>();
		List<Criterion> location = new ArrayList<>();
		
		clearReport();
		filterStatus = false;
		
		if (type != null) {
			movFilters.add(Restrictions.eq("movimento", Movement.valueOf(type.name())));
			target.add(new ReportTarget("Movimentação", type.getLabel()));
		} else {
			dataFim = null;
			dataInicio = null;
		}
		
		if (problema != null) {
			siepFilters.add(Restrictions.eq("tipoReclamacao", problema));
			target.add(new ReportTarget("Requisição", problema.getLabel()));
			problemReports.add(new ProblemReport(problema));
		} else {
			for (Problem aux : Problem.values())
				problemReports.add(new ProblemReport(aux));
		}
		
		if (status != null) {
			siepFilters.add(Restrictions.eq("status", status));
			target.add(new ReportTarget("Estado", status.getLabel()));
			statusReports.add(new StatusReport(status));
		} else {
			for (RipStatus aux : RipStatus.values())
				statusReports.add(new StatusReport(aux));
		}
		
		if (!prioridade.isEmpty()) {
			siepFilters.add(Restrictions.eq("prioridade", Integer.valueOf(prioridade)));
			target.add(new ReportTarget("Prioridade", prioridade));
		}
		
		if (!posteBto.isEmpty()) {
			siepFilters.add(Restrictions.eq("p.numero", posteBto));
			target.add(new ReportTarget("Poste", posteBto));
		}
		
		if (!zonas.isEmpty()) {
			for (String zona : zonas) {
				if (zonaDisabled) {
					siepFilters.add(Restrictions.eq("bairro.zona", Zona.valueOf(zona)));
				} else {
					location.add(Restrictions.eq("bairro.zona", Zona.valueOf(zona)));
				}
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
		
		if (!logradouro.isEmpty()) {
			location.add(Restrictions.eq("logradouro.nome", logradouro));
			target.add(new ReportTarget("Logradouro", logradouro));
		}

		if (!usuario.isEmpty()) {
			movFilters.add(Restrictions.like("u.nome", usuario, MatchMode.ANYWHERE));
			target.add(new ReportTarget("Usuário", usuario));
		}
		
		if (!solicitante.isEmpty()) {
			siepFilters.add(Restrictions.like("solicitante", solicitante, MatchMode.ANYWHERE));
			target.add(new ReportTarget("Solicitante", solicitante));
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
		
		if (siepFilters.size() == 0 && location.size() == 0 && movFilters.size() == 0) {
			addFacesErrorMessage("Nenhum filtro foi aplicado");
			return;
		}
		
		Criterion fullSiepFilter = getConjunction(siepFilters, location);
		
		filterStatus = true;
		
		sieps = siepFacade.getSiepWithMovements(fullSiepFilter, getConjunction(movFilters, null));
		
		if (sieps == null || sieps.isEmpty()) {
			filterStatus = false;
			addFacesErrorMessage("Nenhuma siep encontrada.", "O relatório não pôde ser gerado.");
			return;
		}
		
		populateReport();
		
	}
	
	public void populateReport() {
		report.setTarget(target);
		report.setTotal(sieps.size());
		
		for (Siep siep : sieps) {
			List<Movement> movements = new ArrayList<>();
			
			RipReport ripReport = new RipReport(siep.getId(), siep.getPrevisao().getTime());
			ripReport.setOccurences(siep.getOcorrencias().size());

			if (ripReport.getOccurrences() > 0) {							
				ripReport.setLastOccurrence(CalendarUtil.dateToString(siep.getOcorrencias().get(siep.getOcorrencias().size() - 1).getData().getTime()));
			}
			
			Boolean expired = false;
			
			for (MovimentacaoSiep movimentacao : siep.getMovimentacoes()) {
				
				ripReport.setDates(movimentacao);
				
				if (movimentacao.getMovimento().equals(Movement.EXECUTE))
					if (siep.getPrevisao().before(movimentacao.getData()))
						expired = true;
				
				if (movements.contains(movimentacao.getMovimento())) {
					continue;
				}
				
				movements.add(movimentacao.getMovimento());
				
				report.increment(movimentacao.getMovimento());
				
				for (StatusReport statusReport : statusReports)
					if (siep.getStatus().equals(statusReport.getStatus()))
						statusReport.increment(movimentacao.getMovimento());
			
				for (ProblemReport problemReport : problemReports)
					if (siep.getTipoReclamacao().equals(problemReport.getProblem()))
						problemReport.increment(movimentacao.getMovimento());	
			}
			
			if (!movements.contains(Movement.EXECUTE) && siep.getStatus() != RipStatus.CANCELED && siep.getPrevisao().before(CalendarUtil.getToday()))
				expired = true;
			
			if (expired)
				report.incrementExpired();
			
			report.increment(siep.getStatus());
			
			for (ProblemReport problemReport : problemReports) {
				if (siep.getTipoReclamacao().equals(problemReport.getProblem())) {
					problemReport.incrementTotal();
					problemReport.increment(siep.getStatus());
					if (expired)
						problemReport.incrementExpired();
				}
			}
			
			for (StatusReport statusReport : statusReports) {
				if (siep.getStatus().equals(statusReport.getStatus())) {
					statusReport.incrementTotal();
					if (expired)
						statusReport.incrementExpired();
				}
			}
			
			ripReports.add(ripReport);
	
		}
	    Iterator<ProblemReport> iteratorPR = problemReports.iterator();
	    
	    while (iteratorPR.hasNext()) {
	    	if (iteratorPR.next().emptyCheck()) {
	    		iteratorPR.remove();
	    	}
	    }
	    
	    Iterator<StatusReport> iteratorSR = statusReports.iterator();
	    
	    while (iteratorSR.hasNext()) {
	    	if (iteratorSR.next().emptyCheck()) {
	    		iteratorSR.remove();
	    	}
	    }
		
		report.setStatusReports(statusReports);
		report.setProblemReports(problemReports);
		report.setRipReports(ripReports);
		report.setSieps(sieps);
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
	
	public List<String> loadUsuarios(String query) {
		List<String> results = new ArrayList<String>();
		List<Criterion> criterions = new ArrayList<Criterion>();
		Conjunction conjunction = new Conjunction();

		if (query != null && !query.isEmpty()) {

			String[] words = query.split("[ ]+");

			if (words.length > 0) {
				for (String aux : words) {
					conjunction.add(Restrictions.like("nome", aux, MatchMode.ANYWHERE));
				}

				criterions.add(conjunction);

				for (Usuario aux : usuarioService.list(0, 5, criterions, null)) {
					results.add(aux.getNome());
				}
			}
		}

		return results;
	}
	
	public List<String> loadSolicitantes(String query) {
		List<String> results = new ArrayList<String>();
		List<Criterion> criterions = new ArrayList<Criterion>();
		Conjunction conjunction = new Conjunction();

		if (query != null && !query.isEmpty()) {

			String[] words = query.split("[ ]+");

			if (words.length > 0) {
				for (String aux : words) {
					conjunction.add(Restrictions.like("solicitante", aux, MatchMode.ANYWHERE));
				}

				criterions.add(conjunction);

				for (Siep aux : siepService.list(0, 5, criterions, null)) {
					results.add(aux.getSolicitante());
				}
			}
		}

		return results;
	}
	
	public StreamedContent print() {
		StreamedContent pdf = null;
		
		try {
			 pdf = printSiepHandler.printReport(report);
		} catch (PrintSiepException e) {
			addFacesErrorMessage("Não foi possível gerar o arquivo!");
			e.printStackTrace();
		}
		
		return pdf;
	}
	
	public List<ReportTarget> getTarget() {
		return target;
	}

	public void setTarget(List<ReportTarget> target) {
		this.target = target;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Problem getProblema() {
		return problema;
	}

	public void setProblema(Problem problema) {
		this.problema = problema;
	}

	public RipStatus getStatus() {
		return status;
	}

	public void setStatus(RipStatus status) {
		this.status = status;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getPosteBto() {
		return posteBto;
	}

	public void setPosteBto(String posteBto) {
		this.posteBto = posteBto;
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
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

	public List<Siep> getSieps() {
		return sieps;
	}

	public void setSieps(List<Siep> sieps) {
		this.sieps = sieps;
	}

	public List<ProblemReport> getProblemReports() {
		return problemReports;
	}

	public void setProblemReports(List<ProblemReport> problemReports) {
		this.problemReports = problemReports;
	}

	public List<StatusReport> getStatusReports() {
		return statusReports;
	}

	public void setStatusReports(List<StatusReport> statusReports) {
		this.statusReports = statusReports;
	}

	public List<RipReport> getRipReports() {
		return ripReports;
	}

	public void setRipReports(List<RipReport> ripReports) {
		this.ripReports = ripReports;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void setPrintSiepHandler(PrintSiepHandler printSiepHandler) {
		this.printSiepHandler = printSiepHandler;
	}

	public void setUsuarioService(DataService<Usuario> usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	public Boolean getZonaDisabled() {
		return zonaDisabled;
	}

	public void setZonaDisabled(Boolean zonaDisabled) {
		this.zonaDisabled = zonaDisabled;
	}

	public List<Bairro> getBairroList() {
		return bairroList;
	}

	public void setBairroList(List<Bairro> bairroList) {
		this.bairroList = bairroList;
	}

	public List<Conjunto> getConjuntoList() {
		return conjuntoList;
	}

	public void setConjuntoList(List<Conjunto> conjuntoList) {
		this.conjuntoList = conjuntoList;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public void setEnderecoFacade(EnderecoFacade enderecoFacade) {
		this.enderecoFacade = enderecoFacade;
	}

	public void setSiepFacade(SiepFacade siepFacade) {
		this.siepFacade = siepFacade;
	}

}