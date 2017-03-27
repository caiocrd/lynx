package br.com.csl.lynx.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.api.SlcInfo;
import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.OcorrenciaSlc;
import br.com.csl.lynx.model.QtdServicoSlc;
import br.com.csl.lynx.model.Slc;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class AbstractSlcInfo extends CommonController implements SlcInfo {

	private static final long serialVersionUID = 1L;
	
	protected Slc slc;
	
	@ManagedProperty("#{slcDataModel}")
	protected DataModel<Slc> slcDataModel;
	
	@ManagedProperty("#{ocorrenciaSlcDataModel}")
	protected DataModel<OcorrenciaSlc> ocorrenciaDataModel;
	
	@ManagedProperty("#{movimentacaoSlcDataModel}")
	protected DataModel<MovimentacaoSlc> movimentacaoDataModel;
	
	@ManagedProperty("#{qtdServicoSlcDataModel}")
	protected DataModel<QtdServicoSlc> qtdServicoDataModel;

	@ManagedProperty("#{slcService}")
	protected DataService<Slc> slcService;
	
	@ManagedProperty("#{ocorrenciaSlcService}")
	protected DataService<OcorrenciaSlc> ocorrenciaService;
	
	@ManagedProperty("#{movimentacaoSlcService}")
	protected DataService<MovimentacaoSlc> movimentacaoService;
	
	@ManagedProperty("#{qtdServicoSlcService}")
	protected DataService<QtdServicoSlc> qtdServicoService;

	public void clear() {
		slc = new Slc();
		
		clearDataModels();
	}
	
	public void clearDataModels() {
		slcDataModel.removeRestraints();
		ocorrenciaDataModel.removeRestraints();
		movimentacaoDataModel.removeRestraints();
		qtdServicoDataModel.removeRestraints();
	}
	
	public void load() {
		ocorrenciaDataModel.newRestraints("slc", slc);
		movimentacaoDataModel.newRestraints("slc", slc);
		qtdServicoDataModel.newRestraints("slc", slc);
	}
	
	public Slc getSlc() {
		return slc;
	}

	public void setSlc(Slc slc) {
		this.slc = slc;
	}
	
	public DataModel<Slc> getSlcDataModel() {
		return slcDataModel;
	}
	
	public void setSlcDataModel(DataModel<Slc> slcDataModel) {
		this.slcDataModel = slcDataModel;
	}
	
	public DataModel<OcorrenciaSlc> getOcorrenciaDataModel() {
		return ocorrenciaDataModel;
	}

	public void setOcorrenciaDataModel(DataModel<OcorrenciaSlc> ocorrenciaDataModel) {
		this.ocorrenciaDataModel = ocorrenciaDataModel;
	}

	public DataModel<MovimentacaoSlc> getMovimentacaoDataModel() {
		return movimentacaoDataModel;
	}

	public void setMovimentacaoDataModel(
			DataModel<MovimentacaoSlc> movimentacaoDataModel) {
		this.movimentacaoDataModel = movimentacaoDataModel;
	}

	public DataModel<QtdServicoSlc> getQtdServicoDataModel() {
		return qtdServicoDataModel;
	}

	public void setQtdServicoDataModel(DataModel<QtdServicoSlc> qtdServicoDataModel) {
		this.qtdServicoDataModel = qtdServicoDataModel;
	}

	public void setSlcService(DataService<Slc> slcService) {
		this.slcService = slcService;
	}

	public void setOcorrenciaService(DataService<OcorrenciaSlc> ocorrenciaService) {
		this.ocorrenciaService = ocorrenciaService;
	}

	public void setMovimentacaoService(DataService<MovimentacaoSlc> movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}
	
	public void setQtdServicoService(DataService<QtdServicoSlc> qtdServicoService) {
		this.qtdServicoService = qtdServicoService;
	}
}
