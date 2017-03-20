package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.PrintMethod;

public interface SlpPrint extends Serializable {
	
	public void clear();
	public void clearPrint();
	
	public void updateCount();
	public Integer getSlpCount();
	public Integer getFilteredSlpCount();
	
	public List<Slp> getSelectedSlps();
	public void setSelectedSlps(List<Slp> selectedSlps);
	
	public List<Slp> getFilteredSlps();
	public List<Slp> getAllSlps();
	
	public StreamedContent print(List<Slp> slps, PrintMethod printMethod);
	
	public StreamedContent printAll();
	public StreamedContent printFiltered();
	public StreamedContent printSelected();
}
