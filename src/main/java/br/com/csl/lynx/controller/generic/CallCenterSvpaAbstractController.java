package br.com.csl.lynx.controller.generic;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.generic.AbstractSvpaAction;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public abstract class CallCenterSvpaAbstractController extends AbstractSvpaAction {

	private static final long serialVersionUID = 1L;
	
	public CallCenterSvpaAbstractController() {
		printMethod = PrintMethod.SIMPLE;
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void close() {
		try {
			movementHandler.close(svpa, obs);
			
			clear();
			addFacesInfoMessage("Svpa fechada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar fechamento. Tente novamente.");
		}
	}
	
	public void reverse() {
		try {
			movementHandler.reverse(svpa, obs);
			
			clear();
			addFacesInfoMessage("Svpa estornada com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar estorno. Tente novamente.");
		}
	}
	
}
