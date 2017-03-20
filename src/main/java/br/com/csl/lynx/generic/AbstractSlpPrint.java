package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.api.SlpPrint;
import br.com.csl.lynx.exception.PrintSlpException;
import br.com.csl.lynx.handler.PrintSlpHandler;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.PrintMethod;

public abstract class AbstractSlpPrint extends AbstractSlpFilter implements SlpPrint {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{printSlpHandler}")
	private PrintSlpHandler printSlpHandler;

	protected PrintMethod printMethod;
	protected List<Slp> selectedSlps;
	
	public void clear() {
		super.clear();
		
		clearPrint();
	}
	
	public void clearPrint() {
		selectedSlps = new ArrayList<Slp>();
	}
		
	public StreamedContent print(List<Slp> slps, PrintMethod printMethod) {
		StreamedContent pdfFile = null;
		try {
			
			if (printMethod.equals(PrintMethod.EXECUTION))
				pdfFile = printSlpHandler.print(slps);
			
			if (printMethod.equals(PrintMethod.SIMPLE))
				pdfFile = printSlpHandler.printSimple(slps);
			
		} catch (PrintSlpException e) {
			addFacesErrorMessage("Não foi possível gerar relatório. Tente novamente!");
		}
		return pdfFile;
	}
	
	public StreamedContent printOne(Slp slp) {
		List<Slp> slps = new ArrayList<>();
		slps.add(slp);
		
		return print(slps, PrintMethod.EXECUTION);
	}
	
	public StreamedContent printAll() {
		return print(getAllSlps(), printMethod);
	}
	
	public StreamedContent printFiltered() {
		return print(getFilteredSlps(), printMethod);
	}
	
	public StreamedContent printSelected() {
		return print(getSelectedSlps(), printMethod);
	}
	
	public void setPrintSlpHandler(PrintSlpHandler printSlpHandler) {
		this.printSlpHandler = printSlpHandler;
	}
	public List<Slp> getSelectedSlps() {
		return selectedSlps;
	}
	public void setSelectedSlps(List<Slp> selectedSlps) {
		this.selectedSlps = selectedSlps;
	}

}
