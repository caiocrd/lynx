package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import br.com.csl.lynx.model.Rip;

public interface RipFilter extends Serializable {
	
	void clear();
	
	void clearFilter();
	
	public void filter();
	
	public void updateCount();
	
	void find();
	
	public List<Rip> getFilteredRips();
	public List<Rip> getAllRips();
	
	public Integer getFilteredRipCount();
	public Integer getRipCount();
	
	public DataFilter getFilter();
}
