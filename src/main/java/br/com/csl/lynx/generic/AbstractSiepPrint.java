package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.api.SiepPrint;
import br.com.csl.lynx.exception.PrintSiepException;
import br.com.csl.lynx.handler.PrintSiepHandler;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.PrintMethod;

public abstract class AbstractSiepPrint extends AbstractSiepFilter implements SiepPrint {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{printSiepHandler}")
	private PrintSiepHandler printSiepHandler;

	protected PrintMethod printMethod;
	protected List<Siep> selectedSieps;
	
	public void clear() {
		super.clear();
		
		clearPrint();
	}
	
	public void clearPrint() {
		selectedSieps = new ArrayList<Siep>();
	}
		
	public StreamedContent print(List<Siep> sieps, PrintMethod printMethod) {
		StreamedContent pdfFile = null;
		try {
			
			if (printMethod.equals(PrintMethod.EXECUTION))
				pdfFile = printSiepHandler.print(sieps);
			
			if (printMethod.equals(PrintMethod.SIMPLE))
				pdfFile = printSiepHandler.printSimple(sieps);
			
		} catch (PrintSiepException e) {
			addFacesErrorMessage("Não foi possível gerar relatório. Tente novamente!");
		}
		return pdfFile;
	}
	
	public StreamedContent printOne(Siep siep) {
		List<Siep> sieps = new ArrayList<>();
		sieps.add(siep);
		
		return print(sieps, PrintMethod.EXECUTION);
	}
	
	public StreamedContent printAll() {
		return print(getAllSieps(), printMethod);
	}
	
	public StreamedContent printFiltered() {
		return print(getFilteredSieps(), printMethod);
	}
	
	public StreamedContent printSelected() {
		return print(getSelectedSieps(), printMethod);
	}
	
	public void setPrintSiepHandler(PrintSiepHandler printSiepHandler) {
		this.printSiepHandler = printSiepHandler;
	}
	public List<Siep> getSelectedSieps() {
		return selectedSieps;
	}
	public void setSelectedSieps(List<Siep> selectedSieps) {
		this.selectedSieps = selectedSieps;
	}

}
