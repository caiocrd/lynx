package br.com.csl.lynx.api;

import java.io.Serializable;

import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Ocorrencia;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Rip;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public interface RipInfo extends Serializable {

	void clear();
	
	void clearDataModels();
	
	Rip getRip();
	void setRip(Rip rip);
	
	DataModel<Rip> getRipDataModel();
	void setRipDataModel(DataModel<Rip> ripDataModel);
	
	DataModel<Movimentacao> getMovimentacaoDataModel();
	void setMovimentacaoDataModel(DataModel<Movimentacao> movimentacaoDataModel);
	
	DataModel<Ocorrencia> getOcorrenciaDataModel();
	void setOcorrenciaDataModel(DataModel<Ocorrencia> ocorrenciaDataModel);
	
	DataModel<QtdServico> getQtdServicoDataModel();
	void setQtdServicoDataModel(DataModel<QtdServico> qtdServicoDataModel);
	
	void setRipService(DataService<Rip> ripService);
	
	void setMovimentacaoService(DataService<Movimentacao> movimentacaoService);
	
	void setOcorrenciaService(DataService<Ocorrencia> ocorrenciaService);
	
	void setQtdServicoService(DataService<QtdServico> qtdServicoService);
	
	void load();
	
}
