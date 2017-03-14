package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class CallCenterHomeSvpaController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
 
	private List<Svpa> latestOpenSvpas; 
	private List<Svpa> latestEndedSvpas;
	private List<Svpa> latestReverseSvpas;
	
	@ManagedProperty("#{svpaService}") 
	private DataService<Svpa> svpaService;
	
	@PostConstruct
	public void init() {
		
		latestOpenSvpas = svpaService.list(0, 6, Order.desc("id"));

		latestEndedSvpas = svpaService.list(0, 6, Restrictions.eq("status", RipStatus.CLOSED), Order.desc("id"));
		
		latestReverseSvpas = svpaService.list(0, 6, Restrictions.eq("status", RipStatus.REVERSING), Order.desc("id"));
	}


	public List<Svpa> getLatestOpenSvpas() {
		return latestOpenSvpas;
	}

	public void setLatestOpenSvpas(List<Svpa> latestOpenSvpas) {
		this.latestOpenSvpas = latestOpenSvpas;
	}

	public List<Svpa> getLatestEndedSvpas() {
		return latestEndedSvpas;
	}

	public void setLatestEndedSvpas(List<Svpa> latestEndedSvpas) {
		this.latestEndedSvpas = latestEndedSvpas;
	}
	
	public List<Svpa> getLatestReverseSvpas() {
		return latestReverseSvpas;
	}

	public void setLatestReverseSvpas(List<Svpa> latestReverseSvpas) {
		this.latestReverseSvpas = latestReverseSvpas;
	}
	
	public void setSvpaService(DataService<Svpa> svpaService) {
		this.svpaService = svpaService;
	}

}
