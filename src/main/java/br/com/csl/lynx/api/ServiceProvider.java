package br.com.csl.lynx.api;

import java.util.List;

import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.data.DataModel;

public interface ServiceProvider {

	void newQtdServico(Servico servico);
	
	List<QtdServico> getQtdServicos();
	
	DataModel<Servico> getServicoDataModel();
	void setServicoDataModel(DataModel<Servico> servicoDataModel);
}
