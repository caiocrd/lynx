package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import br.com.csl.lynx.model.Slp;

public interface SlpFilter extends Serializable {
	
	void clear();
	
	void clearFilter();
	
	public void filter();
	
	public void updateCount();
	
	void find();
	
	public List<Slp> getFilteredSlps();
	public List<Slp> getAllSlps();
	
	public Integer getFilteredSlpCount();
	public Integer getSlpCount();
	
	public DataFilter getFilter();
}
