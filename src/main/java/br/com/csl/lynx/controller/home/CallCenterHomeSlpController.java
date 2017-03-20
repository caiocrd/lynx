package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class CallCenterHomeSlpController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
 
	private List<Slp> latestOpenSlps; 
	private List<Slp> latestEndedSlps;
	private List<Slp> latestReverseSlps;
	
	@ManagedProperty("#{slpService}") 
	private DataService<Slp> slpService;
	
	@PostConstruct
	public void init() {
		
		latestOpenSlps = slpService.list(0, 6, Order.desc("id"));

		latestEndedSlps = slpService.list(0, 6, Restrictions.eq("status", RipStatus.CLOSED), Order.desc("id"));
		
		latestReverseSlps = slpService.list(0, 6, Restrictions.eq("status", RipStatus.REVERSING), Order.desc("id"));
	}


	public List<Slp> getLatestOpenSlps() {
		return latestOpenSlps;
	}

	public void setLatestOpenSlps(List<Slp> latestOpenSlps) {
		this.latestOpenSlps = latestOpenSlps;
	}

	public List<Slp> getLatestEndedSlps() {
		return latestEndedSlps;
	}

	public void setLatestEndedSlps(List<Slp> latestEndedSlps) {
		this.latestEndedSlps = latestEndedSlps;
	}
	
	public List<Slp> getLatestReverseSlps() {
		return latestReverseSlps;
	}

	public void setLatestReverseSlps(List<Slp> latestReverseSlps) {
		this.latestReverseSlps = latestReverseSlps;
	}
	
	public void setSlpService(DataService<Slp> slpService) {
		this.slpService = slpService;
	}

}
