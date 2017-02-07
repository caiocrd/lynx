package br.com.csl.lynx.controller.service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@ViewScoped
public class ServicoController extends CommonController {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{servicoDataModel}")
	private DataModel<Servico> servicoDataModel;
	
	@ManagedProperty("#{servicoService}")
	private DataService<Servico> servicoService;
	
	private Servico servico;

	public ServicoController() {
		clear();
	}
	
	public void clear() {
		servico = new Servico();
	}
	
	public void remove(Servico servico) {
		if (servico.getId() == null) {
			addFacesErrorMessage("Serviço Inválido.");
			clear();
			return;
		}
		
		try {
			servicoService.remove(servico);
			addFacesInfoMessage("Serviço removido com sucesso.");
			
		} catch (ServiceException e) {
			addFacesErrorMessage("Erro ao remover serviço.");
			e.printStackTrace();
		}
		finally {
			clear();
		}
	}
	
	public void save() {
		try {	
			if (servico.getId() == null) {
		 		if (servicoService.count(Restrictions.like("nome", servico.getNome())) == 0) {
		 			servicoService.save(servico);
		 			addFacesInfoMessage("Serviço cadastrado com sucesso.");
		 			clear();
		 		} else {
		 			addFacesErrorMessage("Serviço já cadastrado.");
		 		}
			}
			else {
				servicoService.save(servico);
				addFacesInfoMessage("Serviço atualizado com sucesso.");
				clear();
			}
		} catch (ServiceException e) {
			if (servico.getId() == null)
				addFacesErrorMessage("Erro ao cadastrar serviço.");
			else 
				addFacesErrorMessage("Erro ao atualizar serviço.");
			
			e.printStackTrace();
		}
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public void setServicoService(DataService<Servico> servicoService) {
		this.servicoService = servicoService;
	}

}
