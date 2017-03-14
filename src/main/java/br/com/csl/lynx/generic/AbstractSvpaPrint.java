package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.api.SvpaPrint;
import br.com.csl.lynx.exception.PrintSvpaException;
import br.com.csl.lynx.handler.PrintSvpaHandler;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.PrintMethod;

public abstract class AbstractSvpaPrint extends AbstractSvpaFilter implements SvpaPrint {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{printSvpaHandler}")
	private PrintSvpaHandler printSvpaHandler;

	protected PrintMethod printMethod;
	protected List<Svpa> selectedSvpas;
	
	public void clear() {
		super.clear();
		
		clearPrint();
	}
	
	public void clearPrint() {
		selectedSvpas = new ArrayList<Svpa>();
	}
		
	public StreamedContent print(List<Svpa> svpas, PrintMethod printMethod) {
		StreamedContent pdfFile = null;
		try {
			
			if (printMethod.equals(PrintMethod.EXECUTION))
				pdfFile = printSvpaHandler.print(svpas);
			
			if (printMethod.equals(PrintMethod.SIMPLE))
				pdfFile = printSvpaHandler.printSimple(svpas);
			
		} catch (PrintSvpaException e) {
			addFacesErrorMessage("Não foi possível gerar relatório. Tente novamente!");
		}
		return pdfFile;
	}
	
	public StreamedContent printOne(Svpa svpa) {
		List<Svpa> svpas = new ArrayList<>();
		svpas.add(svpa);
		
		return print(svpas, PrintMethod.EXECUTION);
	}
	
	public StreamedContent printAll() {
		return print(getAllSvpas(), printMethod);
	}
	
	public StreamedContent printFiltered() {
		return print(getFilteredSvpas(), printMethod);
	}
	
	public StreamedContent printSelected() {
		return print(getSelectedSvpas(), printMethod);
	}
	
	public void setPrintSvpaHandler(PrintSvpaHandler printSvpaHandler) {
		this.printSvpaHandler = printSvpaHandler;
	}
	public List<Svpa> getSelectedSvpas() {
		return selectedSvpas;
	}
	public void setSelectedSvpas(List<Svpa> selectedSvpas) {
		this.selectedSvpas = selectedSvpas;
	}

}
