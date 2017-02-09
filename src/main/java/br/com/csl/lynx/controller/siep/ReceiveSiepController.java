package br.com.csl.lynx.controller.siep;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.StreamedContent;

import br.com.csl.lynx.facade.SiepFacade;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractSiepAction;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ReceiveSiepController extends AbstractSiepAction {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{siepFacade}")
	private SiepFacade siepFacade;

	public ReceiveSiepController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.OPEN));
		printMethod = PrintMethod.EXECUTION;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
		
		updateCount();
	}

	public void receive(List<Siep> sieps) {
		try {
			siepFacade.receiveSiepList(sieps, getLoggedUser());
		} catch (ServiceException e) {
			addFacesErrorMessage("Não foi possível receber a(s) SIEP(s). Tente novamente!");
		}
	}
	
	public StreamedContent printAndReceiveFiltered() {
		StreamedContent pdfFile = printFiltered();
		receive(getFilteredSieps());
		return pdfFile;				
	}
	
	public StreamedContent printAndReceiveAll() {
		StreamedContent pdfFile = printAll();
		receive(getAllSieps());
		return pdfFile;
	}
	
	public StreamedContent printAndReceiveSelected() {
		StreamedContent pdfFile = printSelected();
		receive(selectedSieps);
		clearPrint();
		return pdfFile;
	}

	public void receiveAll() {
		receive(getAllSieps());
	}
	
	public void receiveOne() {
		List<Siep> r = new ArrayList<Siep>();
		r.add(getSiep());
		receive(r);
		clear();
		addFacesInfoMessage("Registro de recebimento concluído com sucesso!");
	}
	
	public void receiveFiltered() {
		receive(getFilteredSieps());
	}
	
	public void receiveSelected() {
		receive(selectedSieps);
		clearPrint();
	}

	public void setSiepFacade(SiepFacade siepFacade) {
		this.siepFacade = siepFacade;
	}
	
}