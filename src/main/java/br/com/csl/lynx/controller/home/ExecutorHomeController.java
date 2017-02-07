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

import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class ExecutorHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;

	private List<Rip> toReceive;
	private Integer totalToReceive;
	
	private List<Rip> toAdequate;
	private Integer totalToAdequate;
	
	private List<Rip> inExecution;
	private Integer totalInExecution;
	
	private List<Rip> toExpire;
	private Integer totalToExpire;
	
	private List<Rip> expired;
	private Integer totalExpired;	

	@ManagedProperty("#{ripService}")
	private DataService<Rip> ripService;

	@PostConstruct
	public void init() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		now.setLenient(false);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		
		toReceive = ripService.list(0, 6, Restrictions.eq("status", RipStatus.OPEN), Order.desc("id"));
		totalToReceive = ripService.count(Restrictions.eq("status", RipStatus.OPEN));
		
		toAdequate = ripService.list(0, 6, Restrictions.eq("status", RipStatus.ADEQUATING), Order.desc("id"));
		totalToAdequate = ripService.count(Restrictions.eq("status", RipStatus.ADEQUATING));
		
		inExecution = ripService.list(0, 6, Restrictions.eq("status", RipStatus.EXECUTING), Order.desc("id"));
		totalInExecution = ripService.count(Restrictions.eq("status", RipStatus.EXECUTING));
		
		toExpire = ripService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)), Order.desc("id"));
		totalToExpire = ripService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)));

		expired = ripService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)), Order.desc("id"));
		totalExpired = ripService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)));
	}

	public List<Rip> getToReceive() {
		return toReceive;
	}

	public void setToReceive(List<Rip> toReceive) {
		this.toReceive = toReceive;
	}

	public Integer getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(Integer totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public List<Rip> getToAdequate() {
		return toAdequate;
	}

	public void setToAdequate(List<Rip> toAdequate) {
		this.toAdequate = toAdequate;
	}

	public Integer getTotalToAdequate() {
		return totalToAdequate;
	}

	public void setTotalToAdequate(Integer totalToAdequate) {
		this.totalToAdequate = totalToAdequate;
	}

	public List<Rip> getInExecution() {
		return inExecution;
	}

	public void setInExecution(List<Rip> inExecution) {
		this.inExecution = inExecution;
	}

	public Integer getTotalInExecution() {
		return totalInExecution;
	}

	public void setTotalInExecution(Integer totalInExecution) {
		this.totalInExecution = totalInExecution;
	}

	public List<Rip> getToExpire() {
		return toExpire;
	}

	public void setToExpire(List<Rip> toExpire) {
		this.toExpire = toExpire;
	}

	public Integer getTotalToExpire() {
		return totalToExpire;
	}

	public void setTotalToExpire(Integer totalToExpire) {
		this.totalToExpire = totalToExpire;
	}

	public List<Rip> getExpired() {
		return expired;
	}

	public void setExpired(List<Rip> expired) {
		this.expired = expired;
	}

	public Integer getTotalExpired() {
		return totalExpired;
	}

	public void setTotalExpired(Integer totalExpired) {
		this.totalExpired = totalExpired;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}


}
