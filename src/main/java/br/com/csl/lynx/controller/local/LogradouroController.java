package br.com.csl.lynx.controller.local;

import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.facade.EnderecoFacade;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Endereco;
import br.com.csl.lynx.model.Logradouro;
import br.com.csl.lynx.model.LogradouroBairro;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class LogradouroController extends CommonController {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{logradouroDataModel}")
	private DataModel<Logradouro> logradouroDataModel;

	@ManagedProperty("#{logradouroService}")
	private DataService<Logradouro> logradouroService;

	@ManagedProperty("#{logradouroBairroService}")
	private DataService<LogradouroBairro> logradouroBairroService;

	@ManagedProperty("#{enderecoService}")
	private DataService<Endereco> enderecoService;
	
	@ManagedProperty("#{enderecoFacade}")
	private EnderecoFacade enderecoFacade;
	
	private String bairroNome;
	private Bairro bairro;
	private Logradouro logradouro;
	
	private Logradouro selection;

	public LogradouroController() {
		clear();
	}

	public void clear() {
		bairro = new Bairro();
		selection = null;
		logradouro = new Logradouro();
		logradouro.setBairros(new HashSet<Bairro>());
	}

	public void remove(Logradouro logradouro) {
		if (logradouro.getId() == null) {
			addFacesErrorMessage("Logradouro Inválido.");
			clear();
			return;
		}

		try {
			List<Endereco> enderecos = enderecoService.list("fk.logradouro", logradouro, null);
			
			for (Endereco aux : enderecos) {
				enderecoService.remove(aux);
			}
			
			logradouro.setBairros(new HashSet<Bairro>());
			
			logradouroService.save(logradouro);
			logradouroService.remove(logradouro);
			addFacesInfoMessage("Logradouro removido com sucesso.");
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao remover logradouro.");
			e.printStackTrace();
		} finally {
			clear();
		}
	}

	public void save() {
		if (logradouro.getBairros().size() == 0) {
			addFacesErrorMessage("Inclua bairros no logradouro.");
			return;
		}
		
		try {
			if (logradouro.getId() == null) {
				if (logradouroService.find("nome", logradouro.getNome()) == null) {
					logradouroService.save(logradouro);
					addFacesInfoMessage("Logradouro cadastrado com sucesso.");
					clear();
				} else {
					addFacesErrorMessage("Logradouro já cadastrado.");
				}
			} else {
				logradouroService.save(logradouro);
				addFacesInfoMessage("Logradouro atualizado com sucesso.");
				clear();
			}
		} catch (ServiceException e) {
			if (bairro.getId() == null)
				addFacesErrorMessage("Erro ao cadastrar logradouro.");
			else
				addFacesErrorMessage("Erro ao atualizar logradouro.");

			e.printStackTrace();
		}
	}
	
	public void selectBairro() {
		if (!bairroNome.isEmpty()) {
			bairro = enderecoFacade.getBairroService().find("nome", bairroNome);
		} else {
			bairro = new Bairro();
		}
	}	
	
	public void removeBairro(Bairro bairro) {
		logradouro.getBairros().remove(bairro);
	}
	
	public void select() {
		if (selection != null) {
			logradouro = selection;
		}
	}

	public DataModel<Logradouro> getLogradouroDataModel() {
		return logradouroDataModel;
	}

	public void setLogradouroDataModel(DataModel<Logradouro> logradouroDataModel) {
		this.logradouroDataModel = logradouroDataModel;
	}

	public String getBairroNome() {
		return bairroNome;
	}

	public void setBairroNome(String bairroNome) {
		this.bairroNome = bairroNome;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public void setLogradouroService(DataService<Logradouro> logradouroService) {
		this.logradouroService = logradouroService;
	}

	public void setLogradouroBairroService(
			DataService<LogradouroBairro> logradouroBairroService) {
		this.logradouroBairroService = logradouroBairroService;
	}

	public void setEnderecoService(DataService<Endereco> enderecoService) {
		this.enderecoService = enderecoService;
	}

	public void setEnderecoFacade(EnderecoFacade enderecoFacade) {
		this.enderecoFacade = enderecoFacade;
	}

	public Logradouro getSelection() {
		return selection;
	}

	public void setSelection(Logradouro selection) {
		this.selection = selection;
	}

}
