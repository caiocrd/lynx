package br.com.csl.lynx.api;

import java.util.List;

import br.com.csl.lynx.model.QtdServicoSvpa;
import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.data.DataModel;

public interface ServiceSvpaProvider {

	void newQtdServico(Servico servico);
	
	List<QtdServicoSvpa> getQtdServicos();
	
	DataModel<Servico> getServicoDataModel();
	void setServicoDataModel(DataModel<Servico> servicoDataModel);
}
