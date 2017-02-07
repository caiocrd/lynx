package br.com.csl.lynx.api;

import java.io.Serializable;

import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.OcorrenciaSiep;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Siep;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public interface SiepInfo extends Serializable {

	void clear();
	
	void clearDataModels();
	
	Siep getSiep();
	void setSiep(Siep siep);
	
	DataModel<Siep> getSiepDataModel();
	void setSiepDataModel(DataModel<Siep> siepDataModel);
	
	DataModel<MovimentacaoSiep> getMovimentacaoDataModel();
	void setMovimentacaoDataModel(DataModel<MovimentacaoSiep> movimentacaoDataModel);
	
	DataModel<OcorrenciaSiep> getOcorrenciaDataModel();
	void setOcorrenciaDataModel(DataModel<OcorrenciaSiep> ocorrenciaDataModel);
	
	DataModel<QtdServico> getQtdServicoDataModel();
	void setQtdServicoDataModel(DataModel<QtdServico> qtdServicoDataModel);
	
	void setSiepService(DataService<Siep> siepService);
	
	void setMovimentacaoService(DataService<MovimentacaoSiep> movimentacaoService);
	
	void setOcorrenciaService(DataService<OcorrenciaSiep> ocorrenciaService);
	
	void setQtdServicoService(DataService<QtdServico> qtdServicoService);
	
	void load();
	
}
