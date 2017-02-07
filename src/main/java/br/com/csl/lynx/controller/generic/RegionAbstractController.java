package br.com.csl.lynx.controller.generic;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.exception.MovementException;
import br.com.csl.lynx.exception.RipException;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.session.UserSession;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.Zona;
import br.com.csl.utils.exception.ServiceException;

public abstract class RegionAbstractController extends AbstractRipAction {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{userSession}")
	private UserSession userSession;
	
	protected Criterion restraints;
	
	public RegionAbstractController() {
		printMethod = PrintMethod.SIMPLE;
	}
	
	public void setRestraints() {
		List<Role> roles = userSession.getPermissions();
		Disjunction disjunction = Restrictions.disjunction();
		for (Role aux : roles) {
			if ((aux.getName().equals(Zona.ZONA_NORTE.name()))
					|| (aux.getName().equals(Zona.ZONA_SUL.name()))
					|| (aux.getName().equals(Zona.ZONA_LESTE.name()))
					|| (aux.getName().equals(Zona.ZONA_OESTE.name()))) {
				disjunction.add(Restrictions.eq("bairro.zona",
						Zona.valueOf(aux.getName())));
			} else if (aux.getName().equals("REGIAO")) {
				disjunction = null;
				break;
			}
		}
		
		restraints = disjunction;
		
		//restraints = Restrictions.and(disjunction, Restrictions.eq("movimento", Movement.OPEN));
	
	}

	public void toAdequate() {
		try {
			movementHandler.evaluate(rip);
			movementHandler.toAdequate(rip, obs, fotoHandler.getMovFolder());

			clear();
			addFacesInfoMessage("Avaliação concluída com sucesso. RIP enviada para adequação.");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar avaliação. Tente novamente!");
		}
	}

	public void done() {
		try {
			movementHandler.evaluate(rip);
			movementHandler.done(rip, obs, fotoHandler.getMovFolder());

			clear();
			addFacesInfoMessage("Avaliação concluída com sucesso. RIP enviada para finalização!");
		} catch (ServiceException | MovementException | RipException e) {
			addFacesErrorMessage("Erro ao registrar avaliação. Tente novamente!");
		}
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

}
