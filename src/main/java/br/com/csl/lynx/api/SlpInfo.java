package br.com.csl.lynx.api;

import java.io.Serializable;

import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.OcorrenciaSlp;
import br.com.csl.lynx.model.QtdServicoSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public interface SlpInfo extends Serializable {

	void clear();
	
	void clearDataModels();
	
	Slp getSlp();
	void setSlp(Slp slp);
	
	DataModel<Slp> getSlpDataModel();
	void setSlpDataModel(DataModel<Slp> slpDataModel);
	
	DataModel<MovimentacaoSlp> getMovimentacaoDataModel();
	void setMovimentacaoDataModel(DataModel<MovimentacaoSlp> movimentacaoDataModel);
	
	DataModel<OcorrenciaSlp> getOcorrenciaDataModel();
	void setOcorrenciaDataModel(DataModel<OcorrenciaSlp> ocorrenciaDataModel);
	
	DataModel<QtdServicoSlp> getQtdServicoDataModel();
	void setQtdServicoDataModel(DataModel<QtdServicoSlp> qtdServicoDataModel);
	
	void setSlpService(DataService<Slp> slpService);
	
	void setMovimentacaoService(DataService<MovimentacaoSlp> movimentacaoService);
	
	void setOcorrenciaService(DataService<OcorrenciaSlp> ocorrenciaService);
	
	void setQtdServicoService(DataService<QtdServicoSlp> qtdServicoService);
	
	void load();
	
}
