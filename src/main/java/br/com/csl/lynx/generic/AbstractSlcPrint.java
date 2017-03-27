package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.api.SlcPrint;
import br.com.csl.lynx.exception.PrintSlcException;
import br.com.csl.lynx.handler.PrintSlcHandler;
import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.support.PrintMethod;

public abstract class AbstractSlcPrint extends AbstractSlcFilter implements SlcPrint {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{printSlcHandler}")
	private PrintSlcHandler printSlcHandler;

	protected PrintMethod printMethod;
	protected List<Slc> selectedSlcs;
	
	public void clear() {
		super.clear();
		
		clearPrint();
	}
	
	public void clearPrint() {
		selectedSlcs = new ArrayList<Slc>();
	}
		
	public StreamedContent print(List<Slc> slcs, PrintMethod printMethod) {
		StreamedContent pdfFile = null;
		try {
			
			if (printMethod.equals(PrintMethod.EXECUTION))
				pdfFile = printSlcHandler.print(slcs);
			
			if (printMethod.equals(PrintMethod.SIMPLE))
				pdfFile = printSlcHandler.printSimple(slcs);
			
		} catch (PrintSlcException e) {
			addFacesErrorMessage("Não foi possível gerar relatório. Tente novamente!");
		}
		return pdfFile;
	}
	
	public StreamedContent printOne(Slc slc) {
		List<Slc> slcs = new ArrayList<>();
		slcs.add(slc);
		
		return print(slcs, PrintMethod.EXECUTION);
	}
	
	public StreamedContent printAll() {
		return print(getAllSlcs(), printMethod);
	}
	
	public StreamedContent printFiltered() {
		return print(getFilteredSlcs(), printMethod);
	}
	
	public StreamedContent printSelected() {
		return print(getSelectedSlcs(), printMethod);
	}
	
	public void setPrintSlcHandler(PrintSlcHandler printSlcHandler) {
		this.printSlcHandler = printSlcHandler;
	}
	public List<Slc> getSelectedSlcs() {
		return selectedSlcs;
	}
	public void setSelectedSlcs(List<Slc> selectedSlcs) {
		this.selectedSlcs = selectedSlcs;
	}

}
