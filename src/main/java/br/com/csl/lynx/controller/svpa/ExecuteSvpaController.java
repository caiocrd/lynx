package br.com.csl.lynx.controller.svpa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.ExecutorSvpaAbstractController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ExecuteSvpaController extends ExecutorSvpaAbstractController {

	private static final long serialVersionUID = 1L;

	public ExecuteSvpaController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.EXECUTING));
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}

	public void execute() {
		try {
			svpa.setQtdServicos(qtdServicos);

			movementHandler.execute(svpa, obs, fotoHandler.getMovFolder());
			
			svpaService.save(svpa);

			clear();
			addFacesInfoMessage("Registro de execução concluído com sucesso!");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar execução. Tente novamente!");
		}
	}

}
