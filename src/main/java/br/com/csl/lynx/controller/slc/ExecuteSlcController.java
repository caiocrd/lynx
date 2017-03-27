package br.com.csl.lynx.controller.slc;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.ExecutorSlcAbstractController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ExecuteSlcController extends ExecutorSlcAbstractController {

	private static final long serialVersionUID = 1L;

	public ExecuteSlcController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.EXECUTING));
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}

	public void execute() {
		try {
			slc.setQtdServicos(qtdServicos);

			movementHandler.execute(slc, obs, fotoHandler.getMovFolder());
			
			slcService.save(slc);

			clear();
			addFacesInfoMessage("Registro de execução concluído com sucesso!");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar execução. Tente novamente!");
		}
	}

}
