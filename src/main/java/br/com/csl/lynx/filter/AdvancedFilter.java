package br.com.csl.lynx.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Zona;
import br.com.csl.lynx.utils.CalendarUtil;

public class AdvancedFilter implements DataFilter {
	
	private static final long serialVersionUID = 1L;
	
	private String cep;
	private String zona;
	private String bairro;
	private String logradouro;
	private String conjunto;
	private String referencia;
	private String solicitante;
	private String telefone;
	private Problem problema;
	private String status;
	private String posteBto;
	private String prioridade;
	private String atendente;
	private Date dataInicio;
	private Date dataFim;
	
	private Boolean filterStatus;
	
	private Class<?> filterClass;
	
	private Criterion primaryFilter;


	public AdvancedFilter(Criterion primaryFilter) {
		this.primaryFilter = primaryFilter;
		filterClass = Movimentacao.class;
		clear();
	}
	
	public void clear() {
		cep = "";
		zona = "";
		bairro = "";
		logradouro = "";
		conjunto = "";
		referencia = "";
		solicitante = "";
		telefone = "";
		problema = null;
		status = "";
		posteBto = "";
		prioridade = "";
		atendente = "";
		
		dataInicio = null;
		dataFim = null;
		
		filterStatus = false;
	}
	
	@Override
	public List<Criterion> getFilters() {
		List<Criterion> filters = new ArrayList<>();
	
		filters.add(primaryFilter);
		
		if (!zona.isEmpty()) {
			filters.add(Restrictions.eq("bairro.zona", Zona.valueOf(zona)));
		}
		
		if (!cep.isEmpty()) {
			filters.add(Restrictions.eq("e.cep", cep));
		}
		
		if (!bairro.isEmpty()) {
			filters.add(Restrictions.eq("bairro.nome", bairro));
		}
		
		if (!logradouro.isEmpty()) {
			filters.add(Restrictions.eq("logradouro.nome", logradouro));
		}

		if (!conjunto.isEmpty()) {
			filters.add(Restrictions.eq("conjunto.nome", conjunto));
		}
		
		if (!referencia.isEmpty()) {
			filters.add(Restrictions.like("p.referencia", referencia, MatchMode.ANYWHERE));
		}
		
		if (!solicitante.isEmpty()) {
			filters.add(Restrictions.like("r.solicitante", solicitante, MatchMode.ANYWHERE));
		}
		
		if (!telefone.isEmpty()) {
			filters.add(Restrictions.eq("r.telefone", telefone));
		}
		
		if (problema != null) {
			filters.add(Restrictions.eq("r.tipoReclamacao", problema));
		}
		
		if (!status.isEmpty()) {
			filters.add(Restrictions.eq("r.status", RipStatus.valueOf(status)));
		}
		
		if (!posteBto.isEmpty()) {
			filters.add(Restrictions.eq("p.numero", posteBto));
		}
		
		if (!prioridade.isEmpty()) {
			filters.add(Restrictions.eq("r.prioridade", Integer.valueOf(prioridade)));
		}
		
		if (!atendente.isEmpty()) {
			filters.add(Restrictions.like("u.nome", atendente, MatchMode.ANYWHERE));
		}
		
		if (dataFim == null && dataInicio != null) {
			filters.add(Restrictions.ge("data", CalendarUtil.getCalendar(dataInicio)));
		} else if (dataFim != null && dataInicio == null) {
			filters.add(Restrictions.le("data", CalendarUtil.getCalendar(dataFim)));
		} else if (dataFim != null && dataInicio != null) {
			filters.add(Restrictions.between("data", CalendarUtil.getCalendar(dataInicio), CalendarUtil.getCalendar(dataFim)));
		}
		
		if (filters.size() > 1)
			filterStatus = true;
		
		return filters;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getConjunto() {
		return conjunto;
	}

	public void setConjunto(String conjunto) {
		this.conjunto = conjunto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Problem getProblema() {
		return problema;
	}

	public void setProblema(Problem problema) {
		this.problema = problema;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPosteBto() {
		return posteBto;
	}

	public void setPosteBto(String posteBto) {
		this.posteBto = posteBto;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getAtendente() {
		return atendente;
	}

	public void setAtendente(String atendente) {
		this.atendente = atendente;
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

	public void setFilterStatus(Boolean filterStatus) {
		this.filterStatus = filterStatus;
	}

	public void setFilterClass(Class<?> filterClass) {
		this.filterClass = filterClass;
	}

	public void setPrimaryFilter(Criterion primaryFilter) {
		this.primaryFilter = primaryFilter;
	}

	public Boolean getFilterStatus() {
		return filterStatus;
	}

	public Class<?> getFilterClass() {
		return filterClass;
	}

	public Criterion getPrimaryFilter() {
		return primaryFilter;
	}

}
