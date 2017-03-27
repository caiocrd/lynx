package br.com.csl.lynx.controller.slc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.facade.SlcFacade;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractSlcAction;
import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ReceiveSlcController extends AbstractSlcAction {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{slcFacade}")
	private SlcFacade slcFacade;

	public ReceiveSlcController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.OPEN));
		printMethod = PrintMethod.EXECUTION;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
		
		updateCount();
	}

	public void receive(List<Slc> slcs) {
		try {
			slcFacade.receiveSlcList(slcs, getLoggedUser());
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível receber a(s) SLC(s). Tente novamente!");
		}
	}
	
	public StreamedContent printAndReceiveFiltered() {
		StreamedContent pdfFile = printFiltered();
		receive(getFilteredSlcs());
		return pdfFile;				
	}
	
	public StreamedContent printAndReceiveAll() {
		StreamedContent pdfFile = printAll();
		receive(getAllSlcs());
		return pdfFile;
	}
	
	public StreamedContent printAndReceiveSelected() {
		StreamedContent pdfFile = printSelected();
		receive(selectedSlcs);
		clearPrint();
		return pdfFile;
	}

	public void receiveAll() {
		receive(getAllSlcs());
	}
	
	public void receiveOne() {
		List<Slc> r = new ArrayList<Slc>();
		r.add(getSlc());
		receive(r);
		clear();
		addFacesInfoMessage("Registro de recebimento concluído com sucesso!");
	}
	
	public void receiveFiltered() {
		receive(getFilteredSlcs());
	}
	
	public void receiveSelected() {
		receive(selectedSlcs);
		clearPrint();
	}

	public void setSlcFacade(SlcFacade slcFacade) {
		this.slcFacade = slcFacade;
	}
	
}