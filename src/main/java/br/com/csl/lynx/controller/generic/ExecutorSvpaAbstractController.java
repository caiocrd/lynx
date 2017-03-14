package br.com.csl.lynx.controller.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.ServiceSvpaProvider;
import br.com.csl.lynx.generic.AbstractSvpaAction;
import br.com.csl.lynx.model.QtdServicoSvpa;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataModel;

public abstract class ExecutorSvpaAbstractController extends AbstractSvpaAction implements ServiceSvpaProvider{

	private static final long serialVersionUID = 1L;

	protected List<QtdServicoSvpa> qtdServicos; 
	
	@ManagedProperty("#{servicoDataModel}")
	protected DataModel<Servico> servicoDataModel;

	public ExecutorSvpaAbstractController() {
		printMethod = PrintMethod.EXECUTION;
	}
	
	public void clear() {
		super.clear();
		
		qtdServicos = new ArrayList<QtdServicoSvpa>();
	}

	public void newQtdServico(Servico servico) {
		for (QtdServicoSvpa aux : qtdServicos) {
			if (aux.getServico().equals(servico)) {
				addFacesErrorMessage("Serviço já adicionado.");
				return;
			}
		}
		
		QtdServicoSvpa qtdServico = new QtdServicoSvpa();
		qtdServico.setQtd(0);
		qtdServico.setServico(servico);
		
		qtdServicos.add(qtdServico);
	}
	
	public void load() {
		super.load();
		
		svpa.setQtdServicos(qtdServicoService.list(Restrictions.eq("svpa", svpa)));
		qtdServicos.addAll(svpa.getQtdServicos());
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public List<QtdServicoSvpa> getQtdServicos() {
		return qtdServicos;
	}

}
