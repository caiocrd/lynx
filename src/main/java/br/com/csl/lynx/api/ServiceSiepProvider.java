package br.com.csl.lynx.api;

import java.util.List;

import br.com.csl.lynx.model.QtdServicoSiep;
import br.com.csl.lynx.model.Servico;
import br.com.csl.utils.data.DataModel;

public interface ServiceSiepProvider {

	void newQtdServico(Servico servico);
	
	List<QtdServicoSiep> getQtdServicos();
	
	DataModel<Servico> getServicoDataModel();
	void setServicoDataModel(DataModel<Servico> servicoDataModel);
}
