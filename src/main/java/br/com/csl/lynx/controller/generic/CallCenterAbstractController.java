package br.com.csl.lynx.controller.generic;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public abstract class CallCenterAbstractController extends AbstractRipAction {

	private static final long serialVersionUID = 1L;
	
	public CallCenterAbstractController() {
		printMethod = PrintMethod.SIMPLE;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void close() {
		try {
			movementHandler.close(rip, obs);
			
			clear();
			addFacesInfoMessage("Rip fechada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar fechamento. Tente novamente.");
		}
	}
	
	public void reverse() {
		try {
			movementHandler.reverse(rip, obs);
			
			clear();
			addFacesInfoMessage("Rip estornada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar estorno. Tente novamente.");
		}
	}
	
}
