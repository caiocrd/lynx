package br.com.csl.lynx.controller.report;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
		
		
	}
	
	public void carregar(){
		dados = new ArrayList<Dados>();
		List<RelatorioGeral> relatorio = carregarRelatorio();
		RelatorioGeral relatorioGeral = new RelatorioGeral();
		
		
		for (RelatorioGeral relatorioZona : relatorio) {
			PieChartModel grafico = new PieChartModel();
			
			grafico.set("Abertas: " + relatorioZona.getAbertas() + " - " + relatorioZona.getAbertas().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getAbertas());
			relatorioGeral.setAbertas(relatorioGeral.getAbertas().add(relatorioZona.getAbertas()));
			
			grafico.set("À Executar: " + relatorioZona.getaExecutar() + " - " + relatorioZona.getaExecutar().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP), relatorioZona.getaExecutar());
			relatorioGeral.setaExecutar(relatorioGeral.getaExecutar().add(relatorioZona.getaExecutar()));
			
			grafico.set("Avaliar: " + relatorioZona.getAvaliar() + " - " + relatorioZona.getAvaliar().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getAvaliar());
			relatorioGeral.setAvaliar(relatorioGeral.getAvaliar().add(relatorioZona.getAvaliar()));
			
			grafico.set("Adequada: " + relatorioZona.getAdequada() + " - " + relatorioZona.getAdequada().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getAdequada());
			relatorioGeral.setAdequada(relatorioGeral.getAdequada().add(relatorioZona.getAdequada()));
			
			grafico.set("Aguardando Estorno: " + relatorioZona.getAguardandoEstorno() + " - " + relatorioZona.getAguardandoEstorno().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getAguardandoEstorno());
			relatorioGeral.setAguardandoEstorno(relatorioGeral.getAguardandoEstorno().add(relatorioZona.getAguardandoEstorno()));
			
			grafico.set("Estornada: " + relatorioZona.getEstornada() + " - " + relatorioZona.getEstornada().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getEstornada());
			relatorioGeral.setEstornada(relatorioGeral.getEstornada().add(relatorioZona.getEstornada()));
			
			grafico.set("Com FBK Negativo: " + relatorioZona.getFeedbackNegativo() + " - " + relatorioZona.getFeedbackNegativo().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getFeedbackNegativo());
			relatorioGeral.setFeedbackNegativo(relatorioGeral.getFeedbackNegativo().add(relatorioZona.getFeedbackNegativo()));
			
			grafico.set("Aguardando Finalização: " + relatorioZona.getAguardandoFinalizacao() + " - " + relatorioZona.getAguardandoFinalizacao().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getAguardandoFinalizacao());
			relatorioGeral.setAguardandoFinalizacao(relatorioGeral.getAguardandoFinalizacao().add(relatorioZona.getAguardandoFinalizacao()));
			
			grafico.set("Finalizadas: " + relatorioZona.getFinalizada() + " - " + relatorioZona.getFinalizada().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getFinalizada());
			relatorioGeral.setFinalizada(relatorioGeral.getFinalizada().add(relatorioZona.getFinalizada()));
			
			grafico.set("Finalizadas no prazo: " + relatorioZona.getFinalizadaDentroPrazo()  + " - " + relatorioZona.getFinalizadaDentroPrazo().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getFinalizadaDentroPrazo());
			relatorioGeral.setFinalizadaDentroPrazo(relatorioGeral.getFinalizadaDentroPrazo().add(relatorioZona.getFinalizadaDentroPrazo()));
			
			grafico.set("Canceladas: " + relatorioZona.getCancelada() + " - " + relatorioZona.getCancelada().multiply(new BigDecimal(100)).divide(relatorioZona.getTotal(), 2, RoundingMode.HALF_UP) + "%", relatorioZona.getCancelada());
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
			if(dataFim == null || dataInicio ==  null){
				addFacesErrorMessage("Escolha das datas de inicio e fim");
				return null;
			}
			Query q = ripDAO.getSession().createSQLQuery("SELECT b.zona, sum(CASE WHEN r.status = 'OPEN' THEN 1 ELSE 0 END) AS abertas, "
					+ "sum(CASE WHEN r.status = 'EXECUTING' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS aExecutar, "
					+ "sum(CASE WHEN r.status = 'EVALUATING' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS avaliar, "
					+ "sum(CASE WHEN r.status = 'ADEQUATING' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS adequada, "
					+ "sum(CASE WHEN r.status = 'REVERSING' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS aguardandoEstorno, "
					+ "sum(CASE WHEN r.status = 'REVERSED' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS estornada, "
					+ "sum(CASE WHEN r.status = 'EVALUATING_FEEDBACK' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS feedbackNegativo, "
					+ "sum(CASE WHEN r.status = 'DONE' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS aguardandoFinalizacao, "
					+ "sum(CASE WHEN r.status = 'CLOSED' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS finalizada, "
					+ "sum(CASE WHEN (r.status = 'CLOSED' AND mm.data < r.previsao AND mm.movimento = 'CLOSE') THEN 1 ELSE 0 END) AS finalizadaDentroPrazo, "
					+ "sum(CASE WHEN r.status = 'CANCELED' and m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS cancelada, "
					+ "sum(CASE WHEN m.movimento = 'OPEN' THEN 1 ELSE 0 END) AS total "
					+ "from rip r "
					+ "JOIN movimentacao m ON (m.rip_id = r.id and m.movimento = 'OPEN') LEFT JOIN movimentacao mm ON (mm.rip_id = r.id and mm.movimento = 'CLOSE') "
					+ "JOIN endereco e ON r.endereco_id = e.id "
					+ "JOIN bairro b on e.bairro_id = b.id  WHERE DATE(m.data) BETWEEN :INICIO AND :FIM group by b.zona")
					.setParameter("INICIO", dataInicio).setParameter("FIM", dataFim).setResultTransformer(Transformers.aliasToBean(RelatorioGeral.class));
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