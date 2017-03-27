package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Slc;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class CallCenterHomeSlcController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
 
	private List<Slc> latestOpenSlcs; 
	private List<Slc> latestEndedSlcs;
	private List<Slc> latestReverseSlcs;
	
	@ManagedProperty("#{slcService}") 
	private DataService<Slc> slcService;
	
	@PostConstruct
	public void init() {
		
		latestOpenSlcs = slcService.list(0, 6, Order.desc("id"));

		latestEndedSlcs = slcService.list(0, 6, Restrictions.eq("status", RipStatus.CLOSED), Order.desc("id"));
		
		latestReverseSlcs = slcService.list(0, 6, Restrictions.eq("status", RipStatus.REVERSING), Order.desc("id"));
	}


	public List<Slc> getLatestOpenSlcs() {
		return latestOpenSlcs;
	}

	public void setLatestOpenSlcs(List<Slc> latestOpenSlcs) {
		this.latestOpenSlcs = latestOpenSlcs;
	}

	public List<Slc> getLatestEndedSlcs() {
		return latestEndedSlcs;
	}

	public void setLatestEndedSlcs(List<Slc> latestEndedSlcs) {
		this.latestEndedSlcs = latestEndedSlcs;
	}
	
	public List<Slc> getLatestReverseSlcs() {
		return latestReverseSlcs;
	}

	public void setLatestReverseSlcs(List<Slc> latestReverseSlcs) {
		this.latestReverseSlcs = latestReverseSlcs;
	}
	
	public void setSlcService(DataService<Slc> slcService) {
		this.slcService = slcService;
	}

}
