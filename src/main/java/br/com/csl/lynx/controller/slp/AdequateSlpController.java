package br.com.csl.lynx.controller.slp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.ExecutorSlpAbstractController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.model.QtdServicoSlp;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class AdequateSlpController extends ExecutorSlpAbstractController {

	private static final long serialVersionUID = 1L;

	public AdequateSlpController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.ADEQUATING));
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void adequate() {
		try {
			for (QtdServicoSlp aux : slp.getQtdServicos()) {
				if (!qtdServicos.contains(aux)) {
					qtdServicoService.remove(aux.getId());
				}
			}
			
			slp.setQtdServicos(qtdServicos);
			
			movementHandler.adequate(slp, obs, fotoHandler.getMovFolder());

			clear();
			addFacesInfoMessage("Registro de adequação concluído com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar adequação. Tente novamente!");
		}

	}

}
