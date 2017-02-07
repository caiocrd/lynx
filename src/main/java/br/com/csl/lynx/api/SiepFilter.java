package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import br.com.csl.lynx.model.Siep;

public interface SiepFilter extends Serializable {
	
	void clear();
	
	void clearFilter();
	
	public void filter();
	
	public void updateCount();
	
	void find();
	
	public List<Siep> getFilteredSieps();
	public List<Siep> getAllSieps();
	
	public Integer getFilteredSiepCount();
	public Integer getSiepCount();
	
	public DataFilter getFilter();
}
