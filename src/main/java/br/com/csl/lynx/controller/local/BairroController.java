package br.com.csl.lynx.controller.local;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.LogradouroBairro;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class BairroController extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{bairroDataModel}")
	private DataModel<Bairro> bairroDataModel;
	
	@ManagedProperty("#{bairroService}")
	private DataService<Bairro> bairroService;
	
	@ManagedProperty("#{logradouroBairroService}")
	private DataService<LogradouroBairro> logradouroBairroService;
	
	private Bairro bairro;
	private Bairro selection;

	public BairroController() {
		clear();
	}
	
	public void clear() {
		bairro = new Bairro();
		selection = null;
	}
	
	public void remove(Bairro bairro) {
		if (bairro.getId() == null) {
			addFacesErrorMessage("Bairro Inválido!");
			clear();
			return;
		}
		
		if (logradouroBairroService.count("bairro", bairro) != 0) {
			addFacesErrorMessage("O bairro possui logradouros cadastrados!");
			clear();
			return;
		}
		
		try {
			bairroService.remove(bairro);
			addFacesInfoMessage("Bairro removido com sucesso!");
			clear();
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao remover bairro!");
			clear();
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {	
				if (bairro.getId() == null) {
			 		if (bairroService.list(Restrictions.ilike("nome", bairro.getNome())).isEmpty()) {
			 			bairroService.save(bairro);
			 			addFacesInfoMessage("Bairro cadastrado com sucesso!");
			 			clear();
			 		} else {
			 			addFacesErrorMessage("Bairro já cadastrado!");
			 		}
				}
				else {
					bairroService.save(bairro);
					addFacesInfoMessage("Bairro atualizado com sucesso!");
					clear();
				}
		} catch (ServiceException e) {
			if (bairro.getId() == null)
				addFacesErrorMessage("Erro ao cadastrar bairro!");
			else 
				addFacesErrorMessage("Erro ao atualizar bairro!");
			
			e.printStackTrace();
		}
	}
	
	public void select() {
		if (selection != null) {
			bairro = selection;
		}
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Bairro getSelection() {
		return selection;
	}

	public void setSelection(Bairro selection) {
		this.selection = selection;
	}

	public DataModel<Bairro> getBairroDataModel() {
		return bairroDataModel;
	}

	public void setBairroDataModel(DataModel<Bairro> bairroDataModel) {
		this.bairroDataModel = bairroDataModel;
	}

	public void setBairroService(DataService<Bairro> bairroService) {
		this.bairroService = bairroService;
	}

	public void setLogradouroBairroService(DataService<LogradouroBairro> logradouroBairroService) {
		this.logradouroBairroService = logradouroBairroService;
	}

}
