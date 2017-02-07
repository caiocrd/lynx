package br.com.csl.lynx.controller.poste;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.exception.PosteException;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.Poste;
import br.com.csl.lynx.model.Rip;
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
public class PosteController extends CommonController {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{posteDataModel}")
	private DataModel<Poste> posteDataModel;

	@ManagedProperty("#{posteService}")
	private DataService<Poste> posteService;
	
	@ManagedProperty("#{ripService}")
	private DataService<Rip> ripService;

	@ManagedProperty("#{enderecoHandler}")
	private EnderecoHandler enderecoHandler;

	private Poste poste;
	private Poste selection;

	@PostConstruct
	public void clear() {
		selection = null;
		poste = new Poste();
		poste.setNumero("");
		poste.setReferencia("");

		enderecoHandler.clear();
		posteDataModel.removeRestraints();
	}

	
	public void excluir(){
		poste = posteService.find(poste.getId());
		if(poste.getRips() != null && !poste.getRips().isEmpty())
			addFacesErrorMessage("O postes não pode ser excluído pois já existem RIPs cadastradas para o mesmo");
		else{
			posteService.remove(poste);
			this.clear();
			addFacesInfoMessage("Poste removido com sucesso!");
		}
	}
	
	public void save() {
		try {
			poste.setEndereco(enderecoHandler.providedEndereco());
			
			if (posteService.count("numero", poste.getNumero()) != 0) {
				if(!posteService.find("id", poste.getId()).getId().equals(poste.getId())){
					addFacesErrorMessage("Poste com mesmo BTO já cadastrado!");
					return;
				}
			}
			
			Integer chkPoste = 0;
					
			if (poste.getId() != null)
				chkPoste = posteService.count(Restrictions.and(
							Restrictions.ne("id", poste.getId()), 
							Restrictions.and(Restrictions.eq("endereco", poste.getEndereco()), Restrictions.like("referencia", poste.getReferencia()))));	
			else
				chkPoste = posteService.count(Restrictions.and(Restrictions.eq("endereco", poste.getEndereco()), Restrictions.like("referencia", poste.getReferencia())));
			
			if (chkPoste != 0) {
				addFacesErrorMessage("Poste com mesma referência já cadastrado.");
				return;
			}
				
				posteService.save(poste);
				
				if (poste.getId() != null) {
					List<Rip> rips = ripService.list(Restrictions.eq("poste", poste));
					if (rips != null && !rips.isEmpty()) {
						for (Rip rip : rips) {
							rip.setEndereco(poste.getEndereco());
							ripService.save(rip);
						}
					}
				}
				
				if (poste.getId() == null)
					addFacesInfoMessage("Poste adicionado com sucesso!");
				else
					addFacesInfoMessage("Poste atualizado com sucesso!");
		} catch (ServiceException e) {
			if (poste.getId() == null)
				addFacesErrorMessage("Não foi possível adicionar o poste. Tente novamente.");
			else
				addFacesErrorMessage("Não foi possível atualizar o poste. Tente novamente.");
		} finally {
			clear();
		}
	}

	public Poste providePoste() throws PosteException {
		if (!poste.getReferencia().isEmpty() && poste.getId() == null) {
			if (poste.getNumero().isEmpty()) {
				poste.setNumero("XXX-XXX");
			}
			
			poste.setEndereco(enderecoHandler.providedEndereco());
			
			List<Poste> chkPoste = posteService.list(Restrictions.and(Restrictions.eq("endereco", poste.getEndereco()), Restrictions.eq("referencia", poste.getReferencia())));
			
			if (chkPoste.isEmpty()) {	
				poste = posteService.save(poste);
			} else {
				poste = chkPoste.get(0);
				throw new PosteException("Poste com mesma referência já cadastrado.");
			}
		}
		
		return poste;
	}

	public void checkPoste() {
		if (!poste.getNumero().isEmpty() && !poste.getNumero().equals("XXX-XXX")) {
			Poste existingPoste = posteService
					.find("numero", poste.getNumero());
			if (existingPoste != null) {
				poste = existingPoste;
				selectPoste();
			}
		}
	}
	
	public void selectPoste() {
		if (poste == null)
			return;
		
		enderecoHandler.setEndereco(poste.getEndereco());
		enderecoHandler.selectEndereco();
	}

	public void select() {
		if (selection != null) {
			poste = selection;
			selectPoste();
		}
	}
	public void doFilter() {
		posteDataModel.removeRestraints();
		if (enderecoHandler.getEndereco() != null) {
			posteDataModel.addRestraint("endereco",
					enderecoHandler.getEndereco());
		} else {
			if (enderecoHandler.getLogradouro() != null) {
				posteDataModel.addRestraint("e.fk.logradouro",
						enderecoHandler.getLogradouro());
			}

			if (enderecoHandler.getBairro() != null) {
				posteDataModel.addRestraint("e.fk.bairro",
						enderecoHandler.getBairro());
			}

			if (enderecoHandler.getZonaNome() != null
					&& enderecoHandler.getZonaNome() != null) {
				posteDataModel.addRestraint("bairro.zona",
						enderecoHandler.getZonaNome());
			}

			if (enderecoHandler.getConjunto() != null) {
				posteDataModel.addRestraint("e.conjunto",
						enderecoHandler.getConjunto());
			}

		}
	}

	public DataModel<Poste> getPosteDataModel() {
		return posteDataModel;
	}

	public void setPosteDataModel(DataModel<Poste> posteDataModel) {
		this.posteDataModel = posteDataModel;
	}

	public DataService<Poste> getPosteService() {
		return posteService;
	}

	public void setPosteService(DataService<Poste> posteService) {
		this.posteService = posteService;
	}

	public EnderecoHandler getEnderecoHandler() {
		return enderecoHandler;
	}

	public void setEnderecoHandler(EnderecoHandler enderecoHandler) {
		this.enderecoHandler = enderecoHandler;
	}

	public Poste getPoste() {
		return poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}

	public Poste getSelection() {
		return selection;
	}

	public void setSelection(Poste selection) {
		this.selection = selection;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}

}
