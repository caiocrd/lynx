package br.com.csl.lynx.api;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.PrintMethod;

public interface SvpaPrint extends Serializable {
	
	public void clear();
	public void clearPrint();
	
	public void updateCount();
	public Integer getSvpaCount();
	public Integer getFilteredSvpaCount();
	
	public List<Svpa> getSelectedSvpas();
	public void setSelectedSvpas(List<Svpa> selectedSvpas);
	
	public List<Svpa> getFilteredSvpas();
	public List<Svpa> getAllSvpas();
	
	public StreamedContent print(List<Svpa> svpas, PrintMethod printMethod);
	
	public StreamedContent printAll();
	public StreamedContent printFiltered();
	public StreamedContent printSelected();
}
