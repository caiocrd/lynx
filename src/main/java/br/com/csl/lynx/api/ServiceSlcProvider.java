package br.com.csl.lynx.api;

import java.util.List;

import br.com.csl.lynx.model.QtdServicoSlc;
import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.data.DataModel;

public interface ServiceSlcProvider {

	void newQtdServico(Servico servico);
	
	List<QtdServicoSlc> getQtdServicos();
	
	DataModel<Servico> getServicoDataModel();
	void setServicoDataModel(DataModel<Servico> servicoDataModel);
}
