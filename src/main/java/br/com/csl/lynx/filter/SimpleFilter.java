package br.com.csl.lynx.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.Problem;
import br.com.csl.lynx.support.Zona;
import br.com.csl.lynx.utils.CalendarUtil;

public class SimpleFilter implements DataFilter {
	
	private static final long serialVersionUID = 1L;
	
	private Zona zona;
	private String bairro;
	private String logradouro;
	private String conjunto;
	private Problem problema;
	private Date dataInicio;
	private Date dataFim;
	
	private Boolean filterStatus;
	
	private Class<?> filterClass;
	
	private Criterion primaryFilter;

	public SimpleFilter(Criterion primaryFilter) {
		this.primaryFilter = primaryFilter;
		filterClass = Rip.class;
		clear();
	}
	
	public void clear() {
		zona = null;
		bairro = "";
		logradouro = "";
		conjunto = "";
		problema = null;
		
		dataInicio = null;
		dataFim = null;
		
		filterStatus = false;
	}
	
	@Override
	public List<Criterion> getFilters() {
		List<Criterion> filters = new ArrayList<>();
		if (filterClass.equals(Rip.class)) {
	
			filters.add(primaryFilter);
			
			filterStatus = false;
			
			if (zona != null) {
				filters.add(Restrictions.eq("bairro.zona", zona));
				filterStatus = true;
			}
			
			if (!bairro.isEmpty()) {
				filters.add(Restrictions.eq("bairro.nome", bairro));
				filterStatus = true;
			}
			
			if (!logradouro.isEmpty()) {
				filters.add(Restrictions.eq("logradouro.nome", logradouro));
				filterStatus = true;
			}
	
			if (!conjunto.isEmpty()) {
				filters.add(Restrictions.eq("conjunto.nome", conjunto));
				filterStatus = true;
			}
			
			if (problema != null) {
				filters.add(Restrictions.eq("tipoReclamacao", problema));
				filterStatus = true;
			}
			
			if (dataFim == null && dataInicio != null) {
				filters.add(Restrictions.ge("previsao", CalendarUtil.getCalendar(dataInicio)));
				filterStatus = true;
			} else if (dataFim != null && dataInicio == null) {
				filters.add(Restrictions.le("previsao", CalendarUtil.getCalendar(dataFim)));
				filterStatus = true;
			} else if (dataFim != null && dataInicio != null) {
				filters.add(Restrictions.between("previsao", CalendarUtil.getCalendar(dataInicio), CalendarUtil.getCalendar(dataFim)));
				filterStatus = true;
			}
		}
		return filters;
	}
	
	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
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

	public Problem getProblema() {
		return problema;
	}

	public void setProblema(Problem problema) {
		this.problema = problema;
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

	public Class<?> getFilterClass() {
		return filterClass;
	}

	public Criterion getPrimaryFilter() {
		return primaryFilter;
	}

}
