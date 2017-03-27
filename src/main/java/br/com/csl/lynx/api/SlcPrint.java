package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.support.PrintMethod;

public interface SlcPrint extends Serializable {
	
	public void clear();
	public void clearPrint();
	
	public void updateCount();
	public Integer getSlcCount();
	public Integer getFilteredSlcCount();
	
	public List<Slc> getSelectedSlcs();
	public void setSelectedSlcs(List<Slc> selectedSlcs);
	
	public List<Slc> getFilteredSlcs();
	public List<Slc> getAllSlcs();
	
	public StreamedContent print(List<Slc> slcs, PrintMethod printMethod);
	
	public StreamedContent printAll();
	public StreamedContent printFiltered();
	public StreamedContent printSelected();
}
