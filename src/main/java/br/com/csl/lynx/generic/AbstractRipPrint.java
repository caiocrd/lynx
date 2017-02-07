package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.api.RipPrint;
import br.com.csl.lynx.exception.PrintRipException;
import br.com.csl.lynx.handler.PrintRipHandler;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.PrintMethod;

public abstract class AbstractRipPrint extends AbstractRipFilter implements RipPrint {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{printRipHandler}")
	private PrintRipHandler printRipHandler;

	protected PrintMethod printMethod;
	protected List<Rip> selectedRips;
	
	public void clear() {
		super.clear();
		
		clearPrint();
	}
	
	public void clearPrint() {
		selectedRips = new ArrayList<Rip>();
	}
		
	public StreamedContent print(List<Rip> rips, PrintMethod printMethod) {
		StreamedContent pdfFile = null;
		try {
			
			if (printMethod.equals(PrintMethod.EXECUTION))
				pdfFile = printRipHandler.print(rips);
			
			if (printMethod.equals(PrintMethod.SIMPLE))
				pdfFile = printRipHandler.printSimple(rips);
			
		} catch (PrintRipException e) {
			addFacesErrorMessage("Não foi possível gerar relatório. Tente novamente!");
		}
		return pdfFile;
	}
	
	public StreamedContent printOne(Rip rip) {
		List<Rip> rips = new ArrayList<>();
		rips.add(rip);
		
		return print(rips, PrintMethod.EXECUTION);
	}
	
	public StreamedContent printAll() {
		return print(getAllRips(), printMethod);
	}
	
	public StreamedContent printFiltered() {
		return print(getFilteredRips(), printMethod);
	}
	
	public StreamedContent printSelected() {
		return print(getSelectedRips(), printMethod);
	}
	
	public void setPrintRipHandler(PrintRipHandler printRipHandler) {
		this.printRipHandler = printRipHandler;
	}
	public List<Rip> getSelectedRips() {
		return selectedRips;
	}
	public void setSelectedRips(List<Rip> selectedRips) {
		this.selectedRips = selectedRips;
	}

}
