package br.com.csl.lynx.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.api.SiepInfo;
import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.OcorrenciaSiep;
import br.com.csl.lynx.model.QtdServico;
import br.com.csl.lynx.model.Siep;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class AbstractSiepInfo extends CommonController implements SiepInfo {

	private static final long serialVersionUID = 1L;
	
	protected Siep siep;
	
	@ManagedProperty("#{siepDataModel}")
	protected DataModel<Siep> siepDataModel;
	
	@ManagedProperty("#{ocorrenciaSiepDataModel}")
	protected DataModel<OcorrenciaSiep> ocorrenciaDataModel;
	
	@ManagedProperty("#{movimentacaoSiepDataModel}")
	protected DataModel<MovimentacaoSiep> movimentacaoDataModel;
	
	@ManagedProperty("#{qtdServicoDataModel}")
	protected DataModel<QtdServico> qtdServicoDataModel;

	@ManagedProperty("#{siepService}")
	protected DataService<Siep> siepService;
	
	@ManagedProperty("#{ocorrenciaSiepService}")
	protected DataService<OcorrenciaSiep> ocorrenciaService;
	
	@ManagedProperty("#{movimentacaoSiepService}")
	protected DataService<MovimentacaoSiep> movimentacaoService;
	
	@ManagedProperty("#{qtdServicoService}")
	protected DataService<QtdServico> qtdServicoService;

	public void clear() {
		siep = new Siep();
		
		clearDataModels();
	}
	
	public void clearDataModels() {
		siepDataModel.removeRestraints();
		ocorrenciaDataModel.removeRestraints();
		movimentacaoDataModel.removeRestraints();
		qtdServicoDataModel.removeRestraints();
	}
	
	public void load() {
		ocorrenciaDataModel.newRestraints("siep", siep);
		movimentacaoDataModel.newRestraints("siep", siep);
		qtdServicoDataModel.newRestraints("siep", siep);
	}
	
	public Siep getSiep() {
		return siep;
	}

	public void setSiep(Siep siep) {
		this.siep = siep;
	}
	
	public DataModel<Siep> getSiepDataModel() {
		return siepDataModel;
	}
	
	public void setSiepDataModel(DataModel<Siep> siepDataModel) {
		this.siepDataModel = siepDataModel;
	}
	
	public DataModel<OcorrenciaSiep> getOcorrenciaDataModel() {
		return ocorrenciaDataModel;
	}

	public void setOcorrenciaDataModel(DataModel<OcorrenciaSiep> ocorrenciaDataModel) {
		this.ocorrenciaDataModel = ocorrenciaDataModel;
	}

	public DataModel<MovimentacaoSiep> getMovimentacaoDataModel() {
		return movimentacaoDataModel;
	}

	public void setMovimentacaoDataModel(
			DataModel<MovimentacaoSiep> movimentacaoDataModel) {
		this.movimentacaoDataModel = movimentacaoDataModel;
	}

	public DataModel<QtdServico> getQtdServicoDataModel() {
		return qtdServicoDataModel;
	}

	public void setQtdServicoDataModel(DataModel<QtdServico> qtdServicoDataModel) {
		this.qtdServicoDataModel = qtdServicoDataModel;
	}

	public void setSiepService(DataService<Siep> siepService) {
		this.siepService = siepService;
	}

	public void setOcorrenciaService(DataService<OcorrenciaSiep> ocorrenciaService) {
		this.ocorrenciaService = ocorrenciaService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSiep> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}
	
	public void setQtdServicoService(DataService<QtdServico> qtdServicoService) {
		this.qtdServicoService = qtdServicoService;
	}
}
