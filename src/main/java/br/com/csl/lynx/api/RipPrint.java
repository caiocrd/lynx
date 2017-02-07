package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.PrintMethod;

public interface RipPrint extends Serializable {
	
	public void clear();
	public void clearPrint();
	
	public void updateCount();
	public Integer getRipCount();
	public Integer getFilteredRipCount();
	
	public List<Rip> getSelectedRips();
	public void setSelectedRips(List<Rip> selectedRips);
	
	public List<Rip> getFilteredRips();
	public List<Rip> getAllRips();
	
	public StreamedContent print(List<Rip> rips, PrintMethod printMethod);
	
	public StreamedContent printAll();
	public StreamedContent printFiltered();
	public StreamedContent printSelected();
}
