package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface DataFilter extends Serializable {
	
	Boolean getFilterStatus();
	
	List<Criterion> getFilters();
	Class<?> getFilterClass(); 
	
	Criterion getPrimaryFilter();
	
	void clear();
}
