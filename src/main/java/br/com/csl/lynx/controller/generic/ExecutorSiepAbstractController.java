package br.com.csl.lynx.controller.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.ServiceSiepProvider;
import br.com.csl.lynx.generic.AbstractSiepAction;
import br.com.csl.lynx.model.QtdServicoSiep;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataModel;

public abstract class ExecutorSiepAbstractController extends AbstractSiepAction implements ServiceSiepProvider{

	private static final long serialVersionUID = 1L;

	protected List<QtdServicoSiep> qtdServicos; 
	
	@ManagedProperty("#{servicoDataModel}")
	protected DataModel<Servico> servicoDataModel;

	public ExecutorSiepAbstractController() {
		printMethod = PrintMethod.EXECUTION;
	}
	
	public void clear() {
		super.clear();
		
		qtdServicos = new ArrayList<QtdServicoSiep>();
	}

	public void newQtdServico(Servico servico) {
		for (QtdServicoSiep aux : qtdServicos) {
			if (aux.getServico().equals(servico)) {
				addFacesErrorMessage("Serviço já adicionado.");
				return;
			}
		}
		
		QtdServicoSiep qtdServico = new QtdServicoSiep();
		qtdServico.setQtd(0);
		qtdServico.setServico(servico);
		
		qtdServicos.add(qtdServico);
	}
	
	public void load() {
		super.load();
		
		siep.setQtdServicos(qtdServicoService.list(Restrictions.eq("siep", siep)));
		qtdServicos.addAll(siep.getQtdServicos());
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public List<QtdServicoSiep> getQtdServicos() {
		return qtdServicos;
	}

}
