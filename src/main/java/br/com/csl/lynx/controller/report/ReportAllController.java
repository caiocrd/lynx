package br.com.csl.lynx.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.primefaces.model.chart.PieChartModel;

import br.com.csl.lynx.model.Dados;
import br.com.csl.lynx.model.RelatorioGeral;
import br.com.csl.lynx.model.Rip;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataAccess;

@ManagedBean
@ViewScoped
public class ReportAllController extends CommonController {

	private static final long serialVersionUID = 1L;
	
	
	
	@ManagedProperty("#{ripDAO}")
	private DataAccess<Rip> ripDAO;
	
	private Date dataInicio;
	private Date dataFim;
	private List<Dados> dados;
	
	
	@PostConstruct
	public void init() {
		dados = null;
		carregar();
		
	}
	
	public void carregar(){
		dados = new ArrayList<Dados>();
		List<RelatorioGeral> relatorio = carregarRelatorio();
		RelatorioGeral relatorioGeral = new RelatorioGeral();
		
		
		for (RelatorioGeral relatorioZona : relatorio) {
			PieChartModel grafico = new PieChartModel();
			
			grafico.set("Abertas: " + relatorioZona.getAbertas(), relatorioZona.getAbertas());
			relatorioGeral.setAbertas(relatorioGeral.getAbertas().add(relatorioZona.getAbertas()));
			
			grafico.set("À Executar: " + relatorioZona.getaExecutar(), relatorioZona.getaExecutar());
			relatorioGeral.setaExecutar(relatorioGeral.getaExecutar().add(relatorioZona.getaExecutar()));
			
			grafico.set("Avaliar: " + relatorioZona.getAvaliar(), relatorioZona.getAvaliar());
			relatorioGeral.setAvaliar(relatorioGeral.getAvaliar().add(relatorioZona.getAvaliar()));
			
			grafico.set("Adequada: " + relatorioZona.getAdequada(), relatorioZona.getAdequada());
			relatorioGeral.setAdequada(relatorioGeral.getAdequada().add(relatorioZona.getAdequada()));
			
			grafico.set("Aguardando Estorno: " + relatorioZona.getAguardandoEstorno(), relatorioZona.getAguardandoEstorno());
			relatorioGeral.setAguardandoEstorno(relatorioGeral.getAguardandoEstorno().add(relatorioZona.getAguardandoEstorno()));
			
			grafico.set("Estornada: " + relatorioZona.getEstornada(), relatorioZona.getEstornada());
			relatorioGeral.setEstornada(relatorioGeral.getEstornada().add(relatorioZona.getEstornada()));
			
			grafico.set("Com FBK Negativo: " + relatorioZona.getFeedbackNegativo(), relatorioZona.getFeedbackNegativo());
			relatorioGeral.setFeedbackNegativo(relatorioGeral.getFeedbackNegativo().add(relatorioZona.getFeedbackNegativo()));
			
			grafico.set("Aguardando Finalização: " + relatorioZona.getAguardandoFinalizacao(), relatorioZona.getAguardandoFinalizacao());
			relatorioGeral.setAguardandoFinalizacao(relatorioGeral.getAguardandoFinalizacao().add(relatorioZona.getAguardandoFinalizacao()));
			
			grafico.set("Finalizadas: " + relatorioZona.getFinalizada(), relatorioZona.getFinalizada());
			relatorioGeral.setFinalizada(relatorioGeral.getFinalizada().add(relatorioZona.getFinalizada()));
			
			grafico.set("Canceladas: " + relatorioZona.getCancelada(), relatorioZona.getCancelada());
			relatorioGeral.setCancelada(relatorioGeral.getCancelada().add(relatorioZona.getCancelada()));
			
			Dados dado = new Dados();
			dado.setTitulo(relatorioZona.getZona());
			dado.setGrafico(grafico);
			dados.add(dado);
			
		}
		
//		relatorioGeral.setZona("GERAL");
//		Dados geral = new Dados();
//		geral.setGrafico(grafico);
//		dados.add(relatorioGeral)
	}
	
	private List<RelatorioGeral> carregarRelatorio(){
		try {
			
			Query q = ripDAO.getSession().createSQLQuery("SELECT b.zona, sum(CASE WHEN r.status = 'OPEN' THEN 1 ELSE 0 END) AS abertas, "
					+ "sum(CASE WHEN r.status = 'EXECUTING' THEN 1 ELSE 0 END) AS aExecutar, "
					+ "sum(CASE WHEN r.status = 'EVALUATING' THEN 1 ELSE 0 END) AS avaliar, "
					+ "sum(CASE WHEN r.status = 'ADEQUATING' THEN 1 ELSE 0 END) AS adequada, "
					+ "sum(CASE WHEN r.status = 'REVERSING' THEN 1 ELSE 0 END) AS aguardandoEstorno, "
					+ "sum(CASE WHEN r.status = 'REVERSED' THEN 1 ELSE 0 END) AS estornada, "
					+ "sum(CASE WHEN r.status = 'EVALUATING_FEEDBACK' THEN 1 ELSE 0 END) AS feedbackNegativo, "
					+ "sum(CASE WHEN r.status = 'DONE' THEN 1 ELSE 0 END) AS aguardandoFinalizacao, "
					+ "sum(CASE WHEN r.status = 'CLOSED' THEN 1 ELSE 0 END) AS finalizada, "
					+ "sum(CASE WHEN r.status = 'CANCELED' THEN 1 ELSE 0 END) AS cancelada "
					+ "from rip r "
					+ "JOIN movimentacao m ON m.rip_id = r.id  JOIN endereco e ON r.endereco_id = e.id "
					+ "JOIN bairro b on e.bairro_id = b.id  WHERE m.movimento = 'OPEN'  group by b.zona")
					.setResultTransformer(Transformers.aliasToBean(RelatorioGeral.class));
			List<RelatorioGeral> resultado = q.list();
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Dados> getDados() {
		return dados;
	}

	public void setDados(List<Dados> dados) {
		this.dados = dados;
	}

	public void setRipDAO(DataAccess<Rip> ripDAO) {
		this.ripDAO = ripDAO;
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
	
	
	
	
	
}