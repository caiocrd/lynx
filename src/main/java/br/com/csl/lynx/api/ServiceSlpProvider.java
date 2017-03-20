package br.com.csl.lynx.api;

import java.util.List;

import br.com.csl.lynx.model.QtdServicoSlp;
import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.data.DataModel;

public interface ServiceSlpProvider {

	void newQtdServico(Servico servico);
	
	List<QtdServicoSlp> getQtdServicos();
	
	DataModel<Servico> getServicoDataModel();
	void setServicoDataModel(DataModel<Servico> servicoDataModel);
}
