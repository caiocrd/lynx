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

import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class ExecutorSlpHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;

	private List<Slp> toReceive;
	private Integer totalToReceive;
	
	private List<Slp> toAdequate;
	private Integer totalToAdequate;
	
	private List<Slp> inExecution;
	private Integer totalInExecution;
	
	private List<Slp> toExpire;
	private Integer totalToExpire;
	
	private List<Slp> expired;
	private Integer totalExpired;	

	@ManagedProperty("#{slpService}")
	private DataService<Slp> slpService;

	@PostConstruct
	public void init() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		now.setLenient(false);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		
		toReceive = slpService.list(0, 6, Restrictions.eq("status", RipStatus.OPEN), Order.desc("id"));
		totalToReceive = slpService.count(Restrictions.eq("status", RipStatus.OPEN));
		
		toAdequate = slpService.list(0, 6, Restrictions.eq("status", RipStatus.ADEQUATING), Order.desc("id"));
		totalToAdequate = slpService.count(Restrictions.eq("status", RipStatus.ADEQUATING));
		
		inExecution = slpService.list(0, 6, Restrictions.eq("status", RipStatus.EXECUTING), Order.desc("id"));
		totalInExecution = slpService.count(Restrictions.eq("status", RipStatus.EXECUTING));
		
		toExpire = slpService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)), Order.desc("id"));
		totalToExpire = slpService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)));

		expired = slpService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)), Order.desc("id"));
		totalExpired = slpService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)));
	}

	public List<Slp> getToReceive() {
		return toReceive;
	}

	public void setToReceive(List<Slp> toReceive) {
		this.toReceive = toReceive;
	}

	public Integer getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(Integer totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public List<Slp> getToAdequate() {
		return toAdequate;
	}

	public void setToAdequate(List<Slp> toAdequate) {
		this.toAdequate = toAdequate;
	}

	public Integer getTotalToAdequate() {
		return totalToAdequate;
	}

	public void setTotalToAdequate(Integer totalToAdequate) {
		this.totalToAdequate = totalToAdequate;
	}

	public List<Slp> getInExecution() {
		return inExecution;
	}

	public void setInExecution(List<Slp> inExecution) {
		this.inExecution = inExecution;
	}

	public Integer getTotalInExecution() {
		return totalInExecution;
	}

	public void setTotalInExecution(Integer totalInExecution) {
		this.totalInExecution = totalInExecution;
	}

	public List<Slp> getToExpire() {
		return toExpire;
	}

	public void setToExpire(List<Slp> toExpire) {
		this.toExpire = toExpire;
	}

	public Integer getTotalToExpire() {
		return totalToExpire;
	}

	public void setTotalToExpire(Integer totalToExpire) {
		this.totalToExpire = totalToExpire;
	}

	public List<Slp> getExpired() {
		return expired;
	}

	public void setExpired(List<Slp> expired) {
		this.expired = expired;
	}

	public Integer getTotalExpired() {
		return totalExpired;
	}

	public void setTotalExpired(Integer totalExpired) {
		this.totalExpired = totalExpired;
	}

	public void setSlpService(DataService<Slp> slpService) {
		this.slpService = slpService;
	}


}
