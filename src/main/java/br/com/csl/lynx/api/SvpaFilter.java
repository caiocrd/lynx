package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import br.com.csl.lynx.model.Svpa;

public interface SvpaFilter extends Serializable {
	
	void clear();
	
	void clearFilter();
	
	public void filter();
	
	public void updateCount();
	
	void find();
	
	public List<Svpa> getFilteredSvpas();
	public List<Svpa> getAllSvpas();
	
	public Integer getFilteredSvpaCount();
	public Integer getSvpaCount();
	
	public DataFilter getFilter();
}
