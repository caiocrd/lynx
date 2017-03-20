package br.com.csl.lynx.controller.slp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.facade.SvpaFacade;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractSvpaAction;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ReceiveSvpaController extends AbstractSvpaAction {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{svpaFacade}")
	private SvpaFacade svpaFacade;

	public ReceiveSvpaController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.OPEN));
		printMethod = PrintMethod.EXECUTION;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
		
		updateCount();
	}

	public void receive(List<Svpa> svpas) {
		try {
			svpaFacade.receiveSvpaList(svpas, getLoggedUser());
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível receber a(s) SVPA(s). Tente novamente!");
		}
	}
	
	public StreamedContent printAndReceiveFiltered() {
		StreamedContent pdfFile = printFiltered();
		receive(getFilteredSvpas());
		return pdfFile;				
	}
	
	public StreamedContent printAndReceiveAll() {
		StreamedContent pdfFile = printAll();
		receive(getAllSvpas());
		return pdfFile;
	}
	
	public StreamedContent printAndReceiveSelected() {
		StreamedContent pdfFile = printSelected();
		receive(selectedSvpas);
		clearPrint();
		return pdfFile;
	}

	public void receiveAll() {
		receive(getAllSvpas());
	}
	
	public void receiveOne() {
		List<Svpa> r = new ArrayList<Svpa>();
		r.add(getSvpa());
		receive(r);
		clear();
		addFacesInfoMessage("Registro de recebimento concluído com sucesso!");
	}
	
	public void receiveFiltered() {
		receive(getFilteredSvpas());
	}
	
	public void receiveSelected() {
		receive(selectedSvpas);
		clearPrint();
	}

	public void setSvpaFacade(SvpaFacade svpaFacade) {
		this.svpaFacade = svpaFacade;
	}
	
}