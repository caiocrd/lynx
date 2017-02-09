package br.com.csl.lynx.controller.siep;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.ExecutorSiepAbstractController;
import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.model.QtdServicoSiep;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class AdequateSiepController extends ExecutorSiepAbstractController {

	private static final long serialVersionUID = 1L;

	public AdequateSiepController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.ADEQUATING));
	}
	
	@PostConstruct
	public void clear() {
		super.clear();
	}
	
	public void adequate() {
		try {
			for (QtdServicoSiep aux : siep.getQtdServicos()) {
				if (!qtdServicos.contains(aux)) {
					qtdServicoService.remove(aux.getId());
				}
			}
			
			siep.setQtdServicos(qtdServicos);
			
			movementHandler.adequate(siep, obs, fotoHandler.getMovFolder());

			clear();
			addFacesInfoMessage("Registro de adequação concluído com sucesso.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar adequação. Tente novamente!");
		}

	}

}
