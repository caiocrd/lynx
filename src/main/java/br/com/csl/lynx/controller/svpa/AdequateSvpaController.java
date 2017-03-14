package br.com.csl.lynx.controller.svpa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.ExecutorSvpaAbstractController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.model.QtdServicoSvpa;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class AdequateSvpaController extends ExecutorSvpaAbstractController {

	private static final long serialVersionUID = 1L;

	public AdequateSvpaController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.ADEQUATING));
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void adequate() {
		try {
			for (QtdServicoSvpa aux : svpa.getQtdServicos()) {
				if (!qtdServicos.contains(aux)) {
					qtdServicoService.remove(aux.getId());
				}
			}
			
			svpa.setQtdServicos(qtdServicos);
			
			movementHandler.adequate(svpa, obs, fotoHandler.getMovFolder());

			clear();
			addFacesInfoMessage("Registro de adequação concluído com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar adequação. Tente novamente!");
		}

	}

}
