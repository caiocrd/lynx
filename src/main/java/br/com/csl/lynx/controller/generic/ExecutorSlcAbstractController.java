package br.com.csl.lynx.controller.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.ServiceSlcProvider;
import br.com.csl.lynx.generic.AbstractSlcAction;
import br.com.csl.lynx.model.QtdServicoSlc;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataModel;

public abstract class ExecutorSlcAbstractController extends AbstractSlcAction implements ServiceSlcProvider{

	private static final long serialVersionUID = 1L;

	protected List<QtdServicoSlc> qtdServicos; 
	
	@ManagedProperty("#{servicoDataModel}")
	protected DataModel<Servico> servicoDataModel;

	public ExecutorSlcAbstractController() {
		printMethod = PrintMethod.EXECUTION;
	}
	
	public void clear() {
		super.clear();
		
		qtdServicos = new ArrayList<QtdServicoSlc>();
	}

	public void newQtdServico(Servico servico) {
		for (QtdServicoSlc aux : qtdServicos) {
			if (aux.getServico().equals(servico)) {
				addFacesErrorMessage("Serviço já adicionado.");
				return;
			}
		}
		
		QtdServicoSlc qtdServico = new QtdServicoSlc();
		qtdServico.setQtd(0);
		qtdServico.setServico(servico);
		
		qtdServicos.add(qtdServico);
	}
	
	public void load() {
		super.load();
		
		slc.setQtdServicos(qtdServicoService.list(Restrictions.eq("slc", slc)));
		qtdServicos.addAll(slc.getQtdServicos());
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public List<QtdServicoSlc> getQtdServicos() {
		return qtdServicos;
	}
}
