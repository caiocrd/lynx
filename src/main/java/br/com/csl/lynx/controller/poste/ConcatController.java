package br.com.csl.lynx.controller.poste;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Poste;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

/**
 * @author Bruno Henrique de Castro
 * 
 */
@ViewScoped
@ManagedBean
public class ConcatController extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{posteDataModel}")
	private DataModel<Poste> fromPosteDataModel;
	
	@ManagedProperty("#{posteDataModel}")
	private DataModel<Poste> toPosteDataModel;

	@ManagedProperty("#{posteService}")
	private DataService<Poste> posteService;

	@ManagedProperty("#{ripService}")
	private DataService<Rip> ripService;

	private Poste from;
	private Poste to;

	@PostConstruct
	public void clear() {
		from = new Poste();
		to = new Poste();
		
		fromPosteDataModel.removeRestraints();
		toPosteDataModel.removeRestraints();
		toPosteDataModel.getRestraints().add(Restrictions.ne("numero", "XXX-XXX"));
	}

	public void fromSelect() {
		if (from != null)
			toPosteDataModel.getRestraints().add(Restrictions.ne("id", from.getId()));
	}
	
	public void concat() {
		if ((from.getId() == null) || (to.getId() == null)) {
			addFacesErrorMessage("Selecione um poste de cada tabela para concatená-los.");
			return;
		}
		
		Integer fromRipCount = ripService.count(
				Restrictions.and(
						Restrictions.eq("poste", from),
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CANCELED),
								Restrictions.ne("status", RipStatus.CLOSED))));
		
		Integer toRipCount = ripService.count(
				Restrictions.and(
						Restrictions.eq("poste", to),
						Restrictions.and(
								Restrictions.ne("status", RipStatus.CANCELED),
								Restrictions.ne("status", RipStatus.CLOSED))));
		
		if (fromRipCount > 0 && toRipCount > 0) {
			addFacesErrorMessage("Não foi possível concatenar.", "Ambos os postes possuem RIPs em andamento.");
			return;			
		}
		
		List<Rip> rips = ripService.list(Restrictions.eq("poste", from));
		
		try {
			for (Rip aux : rips) {
				aux.setPoste(to);
				aux.setEndereco(to.getEndereco());
				ripService.save(aux);
			}
			
			posteService.remove(from.getId());
			
			addFacesInfoMessage("Postes concatenados com sucesso.");
			clear();
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao concatenar os postes.");
			e.printStackTrace();
		} finally {
			clear();
		}
		
	}

	public DataModel<Poste> getFromPosteDataModel() {
		return fromPosteDataModel;
	}

	public void setFromPosteDataModel(DataModel<Poste> fromPosteDataModel) {
		this.fromPosteDataModel = fromPosteDataModel;
	}

	public DataModel<Poste> getToPosteDataModel() {
		return toPosteDataModel;
	}

	public void setToPosteDataModel(DataModel<Poste> toPosteDataModel) {
		this.toPosteDataModel = toPosteDataModel;
	}

	public Poste getFrom() {
		return from;
	}

	public void setFrom(Poste from) {
		this.from = from;
	}

	public Poste getTo() {
		return to;
	}

	public void setTo(Poste to) {
		this.to = to;
	}

	public void setPosteService(DataService<Poste> posteService) {
		this.posteService = posteService;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}
	
}
