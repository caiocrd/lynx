package br.com.csl.lynx.controller.generic;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.generic.AbstractSiepAction;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public abstract class CallCenterSiepAbstractController extends AbstractSiepAction {

	private static final long serialVersionUID = 1L;
	
	public CallCenterSiepAbstractController() {
		printMethod = PrintMethod.SIMPLE;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void close() {
		try {
			movementHandler.close(siep, obs);
			
			clear();
			addFacesInfoMessage("Siep fechada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar fechamento. Tente novamente.");
		}
	}
	
	public void reverse() {
		try {
			movementHandler.reverse(siep, obs);
			
			clear();
			addFacesInfoMessage("Siep estornada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar estorno. Tente novamente.");
		}
	}
	
}
