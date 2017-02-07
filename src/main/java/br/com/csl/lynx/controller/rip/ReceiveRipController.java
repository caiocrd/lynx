package br.com.csl.lynx.controller.rip;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.facade.RipFacade;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ReceiveRipController extends AbstractRipAction {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{ripFacade}")
	private RipFacade ripFacade;

	public ReceiveRipController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.OPEN));
		printMethod = PrintMethod.EXECUTION;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
		
		updateCount();
	}

	public void receive(List<Rip> rips) {
		try {
			ripFacade.receiveRipList(rips, getLoggedUser());
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível receber a(s) RIP(s). Tente novamente!");
		}
	}
	
	public StreamedContent printAndReceiveFiltered() {
		StreamedContent pdfFile = printFiltered();
		receive(getFilteredRips());
		return pdfFile;				
	}
	
	public StreamedContent printAndReceiveAll() {
		StreamedContent pdfFile = printAll();
		receive(getAllRips());
		return pdfFile;
	}
	
	public StreamedContent printAndReceiveSelected() {
		StreamedContent pdfFile = printSelected();
		receive(selectedRips);
		clearPrint();
		return pdfFile;
	}

	public void receiveAll() {
		receive(getAllRips());
	}
	
	public void receiveOne() {
		List<Rip> r = new ArrayList<Rip>();
		r.add(getRip());
		receive(r);
		clear();
		addFacesInfoMessage("Registro de recebimento concluído com sucesso!");
	}
	
	public void receiveFiltered() {
		receive(getFilteredRips());
	}
	
	public void receiveSelected() {
		receive(selectedRips);
		clearPrint();
	}

	public void setRipFacade(RipFacade ripFacade) {
		this.ripFacade = ripFacade;
	}
	
}