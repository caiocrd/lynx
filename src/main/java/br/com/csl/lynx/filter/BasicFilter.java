package br.com.csl.lynx.filter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.model.Rip;

public class BasicFilter implements DataFilter {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean filterStatus;
	
	private Class<?> filterClass;
	
	private Criterion primaryFilter;

	public BasicFilter(Criterion primaryFilter) {
		this.primaryFilter = primaryFilter;
		filterClass = Rip.class;
		clear();
	}
	
	public void clear() {
		filterStatus = true;
	}
	
	@Override
	public List<Criterion> getFilters() {
		List<Criterion> filters = new ArrayList<>();
		if (filterClass.equals(Rip.class)) {
			filters.add(primaryFilter);
		}
		return filters;
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
