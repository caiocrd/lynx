package br.com.csl.lynx.controller.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.ServiceSlpProvider;
import br.com.csl.lynx.generic.AbstractSlpAction;
import br.com.csl.lynx.model.QtdServicoSlp;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataModel;

public abstract class ExecutorSlpAbstractController extends AbstractSlpAction implements ServiceSlpProvider{

	private static final long serialVersionUID = 1L;

	protected List<QtdServicoSlp> qtdServicos; 
	
	@ManagedProperty("#{servicoDataModel}")
	protected DataModel<Servico> servicoDataModel;

	public ExecutorSlpAbstractController() {
		printMethod = PrintMethod.EXECUTION;
	}
	
	public void clear() {
		super.clear();
		
		qtdServicos = new ArrayList<QtdServicoSlp>();
	}

	public void newQtdServico(Servico servico) {
		for (QtdServicoSlp aux : qtdServicos) {
			if (aux.getServico().equals(servico)) {
				addFacesErrorMessage("Serviço já adicionado.");
				return;
			}
		}
		
		QtdServicoSlp qtdServico = new QtdServicoSlp();
		qtdServico.setQtd(0);
		qtdServico.setServico(servico);
		
		qtdServicos.add(qtdServico);
	}
	
	public void load() {
		super.load();
		
		slp.setQtdServicos(qtdServicoService.list(Restrictions.eq("slp", slp)));
		qtdServicos.addAll(slp.getQtdServicos());
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public List<QtdServicoSlp> getQtdServicos() {
		return qtdServicos;
	}

}
