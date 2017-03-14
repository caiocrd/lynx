package br.com.csl.lynx.api;

import java.io.Serializable;

import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.OcorrenciaSvpa;
import br.com.csl.lynx.model.QtdServicoSvpa;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public interface SvpaInfo extends Serializable {

	void clear();
	
	void clearDataModels();
	
	Svpa getSvpa();
	void setSvpa(Svpa siep);
	
	DataModel<Svpa> getSvpaDataModel();
	void setSvpaDataModel(DataModel<Svpa> siepDataModel);
	
	DataModel<MovimentacaoSvpa> getMovimentacaoDataModel();
	void setMovimentacaoDataModel(DataModel<MovimentacaoSvpa> movimentacaoDataModel);
	
	DataModel<OcorrenciaSvpa> getOcorrenciaDataModel();
	void setOcorrenciaDataModel(DataModel<OcorrenciaSvpa> ocorrenciaDataModel);
	
	DataModel<QtdServicoSvpa> getQtdServicoDataModel();
	void setQtdServicoDataModel(DataModel<QtdServicoSvpa> qtdServicoDataModel);
	
	void setSvpaService(DataService<Svpa> siepService);
	
	void setMovimentacaoService(DataService<MovimentacaoSvpa> movimentacaoService);
	
	void setOcorrenciaService(DataService<OcorrenciaSvpa> ocorrenciaService);
	
	void setQtdServicoService(DataService<QtdServicoSvpa> qtdServicoService);
	
	void load();
	
}
