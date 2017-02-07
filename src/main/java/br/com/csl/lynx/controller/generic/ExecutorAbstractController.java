package br.com.csl.lynx.controller.generic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.ServiceProvider;
import br.com.csl.lynx.generic.AbstractRipAction;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Servico;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataModel;

public abstract class ExecutorAbstractController extends AbstractRipAction implements ServiceProvider {

	private static final long serialVersionUID = 1L;

	protected List<QtdServico> qtdServicos; 
	
	@ManagedProperty("#{servicoDataModel}")
	protected DataModel<Servico> servicoDataModel;

	public ExecutorAbstractController() {
		printMethod = PrintMethod.EXECUTION;
	}
	
	public void clear() {
		super.clear();
		
		qtdServicos = new ArrayList<QtdServico>();
	}

	public void newQtdServico(Servico servico) {
		for (QtdServico aux : qtdServicos) {
			if (aux.getServico().equals(servico)) {
				addFacesErrorMessage("Serviço já adicionado.");
				return;
			}
		}
		
		QtdServico qtdServico = new QtdServico();
		qtdServico.setQtd(0);
		qtdServico.setServico(servico);
		
		qtdServicos.add(qtdServico);
	}
	
	public void load() {
		super.load();
		
		rip.setQtdServicos(qtdServicoService.list(Restrictions.eq("rip", rip)));
		qtdServicos.addAll(rip.getQtdServicos());
	}

	public DataModel<Servico> getServicoDataModel() {
		return servicoDataModel;
	}

	public void setServicoDataModel(DataModel<Servico> servicoDataModel) {
		this.servicoDataModel = servicoDataModel;
	}

	public List<QtdServico> getQtdServicos() {
		return qtdServicos;
	}

}
