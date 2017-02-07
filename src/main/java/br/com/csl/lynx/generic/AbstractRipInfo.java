package br.com.csl.lynx.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.api.RipInfo;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Ocorrencia;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Rip;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class AbstractRipInfo extends CommonController implements RipInfo {

	private static final long serialVersionUID = 1L;
	
	protected Rip rip;
	
	@ManagedProperty("#{ripDataModel}")
	protected DataModel<Rip> ripDataModel;
	
	@ManagedProperty("#{ocorrenciaDataModel}")
	protected DataModel<Ocorrencia> ocorrenciaDataModel;
	
	@ManagedProperty("#{movimentacaoDataModel}")
	protected DataModel<Movimentacao> movimentacaoDataModel;
	
	@ManagedProperty("#{qtdServicoDataModel}")
	protected DataModel<QtdServico> qtdServicoDataModel;

	@ManagedProperty("#{ripService}")
	protected DataService<Rip> ripService;
	
	@ManagedProperty("#{ocorrenciaService}")
	protected DataService<Ocorrencia> ocorrenciaService;
	
	@ManagedProperty("#{movimentacaoService}")
	protected DataService<Movimentacao> movimentacaoService;
	
	@ManagedProperty("#{qtdServicoService}")
	protected DataService<QtdServico> qtdServicoService;

	public void clear() {
		rip = new Rip();
		
		clearDataModels();
	}
	
	public void clearDataModels() {
		ripDataModel.removeRestraints();
		ocorrenciaDataModel.removeRestraints();
		movimentacaoDataModel.removeRestraints();
		qtdServicoDataModel.removeRestraints();
	}
	
	public void load() {
		ocorrenciaDataModel.newRestraints("rip", rip);
		movimentacaoDataModel.newRestraints("rip", rip);
		qtdServicoDataModel.newRestraints("rip", rip);
	}
	
	public Rip getRip() {
		return rip;
	}

	public void setRip(Rip rip) {
		this.rip = rip;
	}
	
	public DataModel<Rip> getRipDataModel() {
		return ripDataModel;
	}
	
	public void setRipDataModel(DataModel<Rip> ripDataModel) {
		this.ripDataModel = ripDataModel;
	}
	
	public DataModel<Ocorrencia> getOcorrenciaDataModel() {
		return ocorrenciaDataModel;
	}

	public void setOcorrenciaDataModel(DataModel<Ocorrencia> ocorrenciaDataModel) {
		this.ocorrenciaDataModel = ocorrenciaDataModel;
	}

	public DataModel<Movimentacao> getMovimentacaoDataModel() {
		return movimentacaoDataModel;
	}

	public void setMovimentacaoDataModel(
			DataModel<Movimentacao> movimentacaoDataModel) {
		this.movimentacaoDataModel = movimentacaoDataModel;
	}

	public DataModel<QtdServico> getQtdServicoDataModel() {
		return qtdServicoDataModel;
	}

	public void setQtdServicoDataModel(DataModel<QtdServico> qtdServicoDataModel) {
		this.qtdServicoDataModel = qtdServicoDataModel;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}

	public void setOcorrenciaService(DataService<Ocorrencia> ocorrenciaService) {
		this.ocorrenciaService = ocorrenciaService;
	}

	public void setMovimentacaoService(DataService<Movimentacao> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}
	
	public void setQtdServicoService(DataService<QtdServico> qtdServicoService) {
		this.qtdServicoService = qtdServicoService;
	}
}
