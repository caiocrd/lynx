package br.com.csl.lynx.controller.home;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
public class ExecutorSvpaHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;

	private List<Siep> toReceive;
	private Integer totalToReceive;
	
	private List<Siep> toAdequate;
	private Integer totalToAdequate;
	
	private List<Siep> inExecution;
	private Integer totalInExecution;
	
	private List<Siep> toExpire;
	private Integer totalToExpire;
	
	private List<Siep> expired;
	private Integer totalExpired;	

	@ManagedProperty("#{siepService}")
	private DataService<Siep> siepService;

	@PostConstruct
	public void init() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		now.setLenient(false);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		
		toReceive = siepService.list(0, 6, Restrictions.eq("status", RipStatus.OPEN), Order.desc("id"));
		totalToReceive = siepService.count(Restrictions.eq("status", RipStatus.OPEN));
		
		toAdequate = siepService.list(0, 6, Restrictions.eq("status", RipStatus.ADEQUATING), Order.desc("id"));
		totalToAdequate = siepService.count(Restrictions.eq("status", RipStatus.ADEQUATING));
		
		inExecution = siepService.list(0, 6, Restrictions.eq("status", RipStatus.EXECUTING), Order.desc("id"));
		totalInExecution = siepService.count(Restrictions.eq("status", RipStatus.EXECUTING));
		
		toExpire = siepService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)), Order.desc("id"));
		totalToExpire = siepService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)));

		expired = siepService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)), Order.desc("id"));
		totalExpired = siepService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)));
	}

	public List<Siep> getToReceive() {
		return toReceive;
	}

	public void setToReceive(List<Siep> toReceive) {
		this.toReceive = toReceive;
	}

	public Integer getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(Integer totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public List<Siep> getToAdequate() {
		return toAdequate;
	}

	public void setToAdequate(List<Siep> toAdequate) {
		this.toAdequate = toAdequate;
	}

	public Integer getTotalToAdequate() {
		return totalToAdequate;
	}

	public void setTotalToAdequate(Integer totalToAdequate) {
		this.totalToAdequate = totalToAdequate;
	}

	public List<Siep> getInExecution() {
		return inExecution;
	}

	public void setInExecution(List<Siep> inExecution) {
		this.inExecution = inExecution;
	}

	public Integer getTotalInExecution() {
		return totalInExecution;
	}

	public void setTotalInExecution(Integer totalInExecution) {
		this.totalInExecution = totalInExecution;
	}

	public List<Siep> getToExpire() {
		return toExpire;
	}

	public void setToExpire(List<Siep> toExpire) {
		this.toExpire = toExpire;
	}

	public Integer getTotalToExpire() {
		return totalToExpire;
	}

	public void setTotalToExpire(Integer totalToExpire) {
		this.totalToExpire = totalToExpire;
	}

	public List<Siep> getExpired() {
		return expired;
	}

	public void setExpired(List<Siep> expired) {
		this.expired = expired;
	}

	public Integer getTotalExpired() {
		return totalExpired;
	}

	public void setTotalExpired(Integer totalExpired) {
		this.totalExpired = totalExpired;
	}

	public void setSiepService(DataService<Siep> siepService) {
		this.siepService = siepService;
	}


}
