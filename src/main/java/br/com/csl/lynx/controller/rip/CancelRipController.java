package br.com.csl.lynx.controller.rip;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class CancelRipController extends AbstractRipAction {

	private static final long serialVersionUID = 1L;
	
	public CancelRipController() {
		filter = new SimpleFilter(Restrictions.and(Restrictions.ne("status", RipStatus.PAYED), Restrictions.and(Restrictions.ne("status", RipStatus.CLOSED), Restrictions.ne("status", RipStatus.CANCELED))));
		printMethod = PrintMethod.SIMPLE;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void cancel() {
		try {
			movementHandler.cancel(rip, obs);
			
			clear();
			addFacesInfoMessage("Rip cancelada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar fechamento. Tente novamente.");
		}
	}
	
}
