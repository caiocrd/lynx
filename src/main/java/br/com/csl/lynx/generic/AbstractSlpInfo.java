package br.com.csl.lynx.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.api.SlpInfo;
import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.OcorrenciaSlp;
import br.com.csl.lynx.model.QtdServicoSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class AbstractSlpInfo extends CommonController implements SlpInfo {

	private static final long serialVersionUID = 1L;
	
	protected Slp slp;
	
	@ManagedProperty("#{slpDataModel}")
	protected DataModel<Slp> slpDataModel;
	
	@ManagedProperty("#{ocorrenciaSlpDataModel}")
	protected DataModel<OcorrenciaSlp> ocorrenciaDataModel;
	
	@ManagedProperty("#{movimentacaoSlpDataModel}")
	protected DataModel<MovimentacaoSlp> movimentacaoDataModel;
	
	@ManagedProperty("#{qtdServicoSlpDataModel}")
	protected DataModel<QtdServicoSlp> qtdServicoDataModel;

	@ManagedProperty("#{slpService}")
	protected DataService<Slp> slpService;
	
	@ManagedProperty("#{ocorrenciaSlpService}")
	protected DataService<OcorrenciaSlp> ocorrenciaService;
	
	@ManagedProperty("#{movimentacaoSlpService}")
	protected DataService<MovimentacaoSlp> movimentacaoService;
	
	@ManagedProperty("#{qtdServicoSlpService}")
	protected DataService<QtdServicoSlp> qtdServicoService;

	public void clear() {
		slp = new Slp();
		
		clearDataModels();
	}
	
	public void clearDataModels() {
		slpDataModel.removeRestraints();
		ocorrenciaDataModel.removeRestraints();
		movimentacaoDataModel.removeRestraints();
		qtdServicoDataModel.removeRestraints();
	}
	
	public void load() {
		ocorrenciaDataModel.newRestraints("slp", slp);
		movimentacaoDataModel.newRestraints("slp", slp);
		qtdServicoDataModel.newRestraints("slp", slp);
	}
	
	public Slp getSlp() {
		return slp;
	}

	public void setSlp(Slp slp) {
		this.slp = slp;
	}
	
	public DataModel<Slp> getSlpDataModel() {
		return slpDataModel;
	}
	
	public void setSlpDataModel(DataModel<Slp> slpDataModel) {
		this.slpDataModel = slpDataModel;
	}
	
	public DataModel<OcorrenciaSlp> getOcorrenciaDataModel() {
		return ocorrenciaDataModel;
	}

	public void setOcorrenciaDataModel(DataModel<OcorrenciaSlp> ocorrenciaDataModel) {
		this.ocorrenciaDataModel = ocorrenciaDataModel;
	}

	public DataModel<MovimentacaoSlp> getMovimentacaoDataModel() {
		return movimentacaoDataModel;
	}

	public void setMovimentacaoDataModel(
			DataModel<MovimentacaoSlp> movimentacaoDataModel) {
		this.movimentacaoDataModel = movimentacaoDataModel;
	}

	public DataModel<QtdServicoSlp> getQtdServicoDataModel() {
		return qtdServicoDataModel;
	}

	public void setQtdServicoDataModel(DataModel<QtdServicoSlp> qtdServicoDataModel) {
		this.qtdServicoDataModel = qtdServicoDataModel;
	}

	public void setSlpService(DataService<Slp> slpService) {
		this.slpService = slpService;
	}

	public void setOcorrenciaService(DataService<OcorrenciaSlp> ocorrenciaService) {
		this.ocorrenciaService = ocorrenciaService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSlp> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}
	
	public void setQtdServicoService(DataService<QtdServicoSlp> qtdServicoService) {
		this.qtdServicoService = qtdServicoService;
	}
}
