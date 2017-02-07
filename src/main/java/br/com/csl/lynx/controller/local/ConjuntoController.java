package br.com.csl.lynx.controller.local;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.facade.EnderecoFacade;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ConjuntoController extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{conjuntoDataModel}")
	private DataModel<Conjunto> conjuntoDataModel;
	
	@ManagedProperty("#{enderecoFacade}")
	private EnderecoFacade enderecoFacade;
	
	private String bairroNome;
	private Conjunto conjunto;
	
	private Conjunto selection;

	public ConjuntoController() {
		clear();
	}
	
	public void clear() {
		bairroNome = "";
		conjunto = new Conjunto();
		selection = null;
	}
	
	public void remove(Conjunto conjunto) {
		if (conjunto.getId() == null) {
			addFacesErrorMessage("Conjunto Inválido!");
			clear();
			return;
		}
		
		if (enderecoFacade.count(Restrictions.eq("conjunto", conjunto)) != 0) {
			addFacesErrorMessage("O conjunto está registrado em endereços!");
			return;
		}
		
		try {
			enderecoFacade.getConjuntoService().remove(conjunto);
			addFacesInfoMessage("Conjunto removido com sucesso!");
			clear();
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao remover conjunto!");
			clear();
			e.printStackTrace();
		}
	}
	
	public void save() {
		Bairro bairro = null;
		
		if (!bairroNome.isEmpty())
			bairro = enderecoFacade.getBairroService().find("nome", bairroNome);
		
		if (bairro == null) {
			addFacesErrorMessage("Bairro inválido!");
			return;
		}
		
		conjunto.setBairro(bairro);
		
		try {	
				if (conjunto.getId() == null) {
			 		if (enderecoFacade.getConjuntoService().find("nome", conjunto.getNome()) == null) {
			 			enderecoFacade.getConjuntoService().save(conjunto);
			 			addFacesInfoMessage("Conjunto cadastrado com sucesso!");
			 			clear();
			 		} else {
			 			addFacesErrorMessage("Conjunto já cadastrado!");
			 		}
				}
				else {
					enderecoFacade.getConjuntoService().save(conjunto);
					addFacesInfoMessage("Conjunto atualizado com sucesso!");
					clear();
				}
		} catch (ServiceException e) {
			if (bairro.getId() == null)
				addFacesErrorMessage("Erro ao cadastrar conjunto!");
			else 
				addFacesErrorMessage("Erro ao atualizar conjunto!");
			
			e.printStackTrace();
		}
	}
	
	public void select() {
		if (selection != null) {
			conjunto = selection;
			bairroNome = conjunto.getBairro().getNome();
		}
	}

	public DataModel<Conjunto> getConjuntoDataModel() {
		return conjuntoDataModel;
	}

	public void setConjuntoDataModel(DataModel<Conjunto> conjuntoDataModel) {
		this.conjuntoDataModel = conjuntoDataModel;
	}

	public String getBairroNome() {
		return bairroNome;
	}

	public void setBairroNome(String bairroNome) {
		this.bairroNome = bairroNome;
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

	public Conjunto getSelection() {
		return selection;
	}

	public void setSelection(Conjunto selection) {
		this.selection = selection;
	}

	public void setEnderecoFacade(EnderecoFacade enderecoFacade) {
		this.enderecoFacade = enderecoFacade;
	}

}
