package br.com.csl.lynx.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.api.SvpaInfo;
import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.OcorrenciaSvpa;
import br.com.csl.lynx.model.QtdServicoSvpa;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class AbstractSvpaInfo extends CommonController implements SvpaInfo {

	private static final long serialVersionUID = 1L;
	
	protected Svpa svpa;
	
	@ManagedProperty("#{svpaDataModel}")
	protected DataModel<Svpa> svpaDataModel;
	
	@ManagedProperty("#{ocorrenciaSvpaDataModel}")
	protected DataModel<OcorrenciaSvpa> ocorrenciaDataModel;
	
	@ManagedProperty("#{movimentacaoSvpaDataModel}")
	protected DataModel<MovimentacaoSvpa> movimentacaoDataModel;
	
	@ManagedProperty("#{qtdServicoSvpaDataModel}")
	protected DataModel<QtdServicoSvpa> qtdServicoDataModel;

	@ManagedProperty("#{svpaService}")
	protected DataService<Svpa> svpaService;
	
	@ManagedProperty("#{ocorrenciaSvpaService}")
	protected DataService<OcorrenciaSvpa> ocorrenciaService;
	
	@ManagedProperty("#{movimentacaoSvpaService}")
	protected DataService<MovimentacaoSvpa> movimentacaoService;
	
	@ManagedProperty("#{qtdServicoSvpaService}")
	protected DataService<QtdServicoSvpa> qtdServicoService;

	public void clear() {
		svpa = new Svpa();
		
		clearDataModels();
	}
	
	public void clearDataModels() {
		svpaDataModel.removeRestraints();
		ocorrenciaDataModel.removeRestraints();
		movimentacaoDataModel.removeRestraints();
		qtdServicoDataModel.removeRestraints();
	}
	
	public void load() {
		ocorrenciaDataModel.newRestraints("svpa", svpa);
		movimentacaoDataModel.newRestraints("svpa", svpa);
		qtdServicoDataModel.newRestraints("svpa", svpa);
	}
	
	public Svpa getSvpa() {
		return svpa;
	}

	public void setSvpa(Svpa svpa) {
		this.svpa = svpa;
	}
	
	public DataModel<Svpa> getSvpaDataModel() {
		return svpaDataModel;
	}
	
	public void setSvpaDataModel(DataModel<Svpa> svpaDataModel) {
		this.svpaDataModel = svpaDataModel;
	}
	
	public DataModel<OcorrenciaSvpa> getOcorrenciaDataModel() {
		return ocorrenciaDataModel;
	}

	public void setOcorrenciaDataModel(DataModel<OcorrenciaSvpa> ocorrenciaDataModel) {
		this.ocorrenciaDataModel = ocorrenciaDataModel;
	}

	public DataModel<MovimentacaoSvpa> getMovimentacaoDataModel() {
		return movimentacaoDataModel;
	}

	public void setMovimentacaoDataModel(
			DataModel<MovimentacaoSvpa> movimentacaoDataModel) {
		this.movimentacaoDataModel = movimentacaoDataModel;
	}

	public DataModel<QtdServicoSvpa> getQtdServicoDataModel() {
		return qtdServicoDataModel;
	}

	public void setQtdServicoDataModel(DataModel<QtdServicoSvpa> qtdServicoDataModel) {
		this.qtdServicoDataModel = qtdServicoDataModel;
	}

	public void setSvpaService(DataService<Svpa> svpaService) {
		this.svpaService = svpaService;
	}

	public void setOcorrenciaService(DataService<OcorrenciaSvpa> ocorrenciaService) {
		this.ocorrenciaService = ocorrenciaService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSvpa> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}
	
	public void setQtdServicoService(DataService<QtdServicoSvpa> qtdServicoService) {
		this.qtdServicoService = qtdServicoService;
	}
}
