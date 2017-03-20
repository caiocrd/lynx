package br.com.csl.lynx.controller.slp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.facade.SlpFacade;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractSlpAction;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ReceiveSlpController extends AbstractSlpAction {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{slpFacade}")
	private SlpFacade slpFacade;

	public ReceiveSlpController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.OPEN));
		printMethod = PrintMethod.EXECUTION;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
		
		updateCount();
	}

	public void receive(List<Slp> slps) {
		try {
			slpFacade.receiveSlpList(slps, getLoggedUser());
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível receber a(s) SLP(s). Tente novamente!");
		}
	}
	
	public StreamedContent printAndReceiveFiltered() {
		StreamedContent pdfFile = printFiltered();
		receive(getFilteredSlps());
		return pdfFile;				
	}
	
	public StreamedContent printAndReceiveAll() {
		StreamedContent pdfFile = printAll();
		receive(getAllSlps());
		return pdfFile;
	}
	
	public StreamedContent printAndReceiveSelected() {
		StreamedContent pdfFile = printSelected();
		receive(selectedSlps);
		clearPrint();
		return pdfFile;
	}

	public void receiveAll() {
		receive(getAllSlps());
	}
	
	public void receiveOne() {
		List<Slp> r = new ArrayList<Slp>();
		r.add(getSlp());
		receive(r);
		clear();
		addFacesInfoMessage("Registro de recebimento concluído com sucesso!");
	}
	
	public void receiveFiltered() {
		receive(getFilteredSlps());
	}
	
	public void receiveSelected() {
		receive(selectedSlps);
		clearPrint();
	}

	public void setSlpFacade(SlpFacade slpFacade) {
		this.slpFacade = slpFacade;
	}
	
}