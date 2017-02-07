package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.PrintMethod;

public interface SiepPrint extends Serializable {
	
	public void clear();
	public void clearPrint();
	
	public void updateCount();
	public Integer getSiepCount();
	public Integer getFilteredSiepCount();
	
	public List<Siep> getSelectedSieps();
	public void setSelectedSieps(List<Siep> selectedSieps);
	
	public List<Siep> getFilteredSieps();
	public List<Siep> getAllSieps();
	
	public StreamedContent print(List<Siep> sieps, PrintMethod printMethod);
	
	public StreamedContent printAll();
	public StreamedContent printFiltered();
	public StreamedContent printSelected();
}
