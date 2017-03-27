package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import br.com.csl.lynx.model.Slc;

public interface SlcFilter extends Serializable {
	
	void clear();
	
	void clearFilter();
	
	public void filter();
	
	public void updateCount();
	
	void find();
	
	public List<Slc> getFilteredSlcs();
	public List<Slc> getAllSlcs();
	
	public Integer getFilteredSlcCount();
	public Integer getSlcCount();
	
	public DataFilter getFilter();
}
