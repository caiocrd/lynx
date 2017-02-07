package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class CallCenterHomeSiepController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
 
	private List<Siep> latestOpenSieps; 
	private List<Siep> latestEndedSieps;
	private List<Siep> latestReverseSieps;
	
	@ManagedProperty("#{siepService}") 
	private DataService<Siep> siepService;
	
	@PostConstruct
	public void init() {
		
		latestOpenSieps = siepService.list(0, 6, Order.desc("id"));

		latestEndedSieps = siepService.list(0, 6, Restrictions.eq("status", RipStatus.CLOSED), Order.desc("id"));
		
		latestReverseSieps = siepService.list(0, 6, Restrictions.eq("status", RipStatus.REVERSING), Order.desc("id"));
	}


	public List<Siep> getLatestOpenSieps() {
		return latestOpenSieps;
	}

	public void setLatestOpenSieps(List<Siep> latestOpenSieps) {
		this.latestOpenSieps = latestOpenSieps;
	}

	public List<Siep> getLatestEndedSieps() {
		return latestEndedSieps;
	}

	public void setLatestEndedSieps(List<Siep> latestEndedSieps) {
		this.latestEndedSieps = latestEndedSieps;
	}
	
	public List<Siep> getLatestReverseSieps() {
		return latestReverseSieps;
	}

	public void setLatestReverseSieps(List<Siep> latestReverseSieps) {
		this.latestReverseSieps = latestReverseSieps;
	}
	
	public void setSiepService(DataService<Siep> siepService) {
		this.siepService = siepService;
	}

}
