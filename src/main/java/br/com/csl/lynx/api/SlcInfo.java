package br.com.csl.lynx.api;

import java.io.Serializable;

import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.OcorrenciaSlc;
import br.com.csl.lynx.model.QtdServicoSlc;
import br.com.csl.lynx.model.Slc;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public interface SlcInfo extends Serializable {

	void clear();
	
	void clearDataModels();
	
	Slc getSlc();
	void setSlc(Slc slc);
	
	DataModel<Slc> getSlcDataModel();
	void setSlcDataModel(DataModel<Slc> slcDataModel);
	
	DataModel<MovimentacaoSlc> getMovimentacaoDataModel();
	void setMovimentacaoDataModel(DataModel<MovimentacaoSlc> movimentacaoDataModel);
	
	DataModel<OcorrenciaSlc> getOcorrenciaDataModel();
	void setOcorrenciaDataModel(DataModel<OcorrenciaSlc> ocorrenciaDataModel);
	
	DataModel<QtdServicoSlc> getQtdServicoDataModel();
	void setQtdServicoDataModel(DataModel<QtdServicoSlc> qtdServicoDataModel);
	
	void setSlcService(DataService<Slc> slcService);
	
	void setMovimentacaoService(DataService<MovimentacaoSlc> movimentacaoService);
	
	void setOcorrenciaService(DataService<OcorrenciaSlc> ocorrenciaService);
	
	void setQtdServicoService(DataService<QtdServicoSlc> qtdServicoService);
	
	void load();
	
}
