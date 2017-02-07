package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Poste;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class CallCenterHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
 
	private List<Poste> latestPostes; 
	private List<Rip> latestOpenRips; 
	private List<Rip> latestEndedRips;
	private List<Rip> latestReverseRips;
	
	@ManagedProperty("#{ripService}") 
	private DataService<Rip> ripService;
	
	@ManagedProperty("#{posteService}")
	private DataService<Poste> posteService;
	
	@PostConstruct
	public void init() {
		latestPostes = posteService.list(0, 6, Order.desc("id"));
		
		latestOpenRips = ripService.list(0, 6, Order.desc("id"));

		latestEndedRips = ripService.list(0, 6, Restrictions.eq("status", RipStatus.CLOSED), Order.desc("id"));
		
		latestReverseRips = ripService.list(0, 6, Restrictions.eq("status", RipStatus.REVERSING), Order.desc("id"));
	}

	public List<Poste> getLatestPostes() {
		return latestPostes;
	}

	public void setLatestPostes(List<Poste> latestPostes) {
		this.latestPostes = latestPostes;
	}

	public List<Rip> getLatestOpenRips() {
		return latestOpenRips;
	}

	public void setLatestOpenRips(List<Rip> latestOpenRips) {
		this.latestOpenRips = latestOpenRips;
	}

	public List<Rip> getLatestEndedRips() {
		return latestEndedRips;
	}

	public void setLatestEndedRips(List<Rip> latestEndedRips) {
		this.latestEndedRips = latestEndedRips;
	}
	
	public List<Rip> getLatestReverseRips() {
		return latestReverseRips;
	}

	public void setLatestReverseRips(List<Rip> latestReverseRips) {
		this.latestReverseRips = latestReverseRips;
	}
	
	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}

	public void setPosteService(DataService<Poste> posteService) {
		this.posteService = posteService;
	}

}
