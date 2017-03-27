package br.com.csl.lynx.controller.generic;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.generic.AbstractSlcAction;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public abstract class CallCenterSlcAbstractController extends AbstractSlcAction {

	private static final long serialVersionUID = 1L;
	
	public CallCenterSlcAbstractController() {
		printMethod = PrintMethod.SIMPLE;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void close() {
		try {
			movementHandler.close(slc, obs);
			
			clear();
			addFacesInfoMessage("Slc fechada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar fechamento. Tente novamente.");
		}
	}
	
	public void reverse() {
		try {
			movementHandler.reverse(slc, obs);
			
			clear();
			addFacesInfoMessage("Slc estornada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar estorno. Tente novamente.");
		}
	}
	
}
