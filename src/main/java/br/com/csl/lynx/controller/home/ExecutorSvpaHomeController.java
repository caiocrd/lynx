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

import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class ExecutorSvpaHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;

	private List<Svpa> toReceive;
	private Integer totalToReceive;
	
	private List<Svpa> toAdequate;
	private Integer totalToAdequate;
	
	private List<Svpa> inExecution;
	private Integer totalInExecution;
	
	private List<Svpa> toExpire;
	private Integer totalToExpire;
	
	private List<Svpa> expired;
	private Integer totalExpired;	

	@ManagedProperty("#{svpaService}")
	private DataService<Svpa> svpaService;

	@PostConstruct
	public void init() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		
		now.setLenient(false);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		
		toReceive = svpaService.list(0, 6, Restrictions.eq("status", RipStatus.OPEN), Order.desc("id"));
		totalToReceive = svpaService.count(Restrictions.eq("status", RipStatus.OPEN));
		
		toAdequate = svpaService.list(0, 6, Restrictions.eq("status", RipStatus.ADEQUATING), Order.desc("id"));
		totalToAdequate = svpaService.count(Restrictions.eq("status", RipStatus.ADEQUATING));
		
		inExecution = svpaService.list(0, 6, Restrictions.eq("status", RipStatus.EXECUTING), Order.desc("id"));
		totalInExecution = svpaService.count(Restrictions.eq("status", RipStatus.EXECUTING));
		
		toExpire = svpaService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)), Order.desc("id"));
		totalToExpire = svpaService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.eq("previsao", now)));

		expired = svpaService.list(0, 6, Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)), Order.desc("id"));
		totalExpired = svpaService.count(Restrictions.and(
				Restrictions.or(Restrictions.eq("status", RipStatus.OPEN), Restrictions.eq("status", RipStatus.EXECUTING)), 
				Restrictions.lt("previsao", now)));
	}

	public List<Svpa> getToReceive() {
		return toReceive;
	}

	public void setToReceive(List<Svpa> toReceive) {
		this.toReceive = toReceive;
	}

	public Integer getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(Integer totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public List<Svpa> getToAdequate() {
		return toAdequate;
	}

	public void setToAdequate(List<Svpa> toAdequate) {
		this.toAdequate = toAdequate;
	}

	public Integer getTotalToAdequate() {
		return totalToAdequate;
	}

	public void setTotalToAdequate(Integer totalToAdequate) {
		this.totalToAdequate = totalToAdequate;
	}

	public List<Svpa> getInExecution() {
		return inExecution;
	}

	public void setInExecution(List<Svpa> inExecution) {
		this.inExecution = inExecution;
	}

	public Integer getTotalInExecution() {
		return totalInExecution;
	}

	public void setTotalInExecution(Integer totalInExecution) {
		this.totalInExecution = totalInExecution;
	}

	public List<Svpa> getToExpire() {
		return toExpire;
	}

	public void setToExpire(List<Svpa> toExpire) {
		this.toExpire = toExpire;
	}

	public Integer getTotalToExpire() {
		return totalToExpire;
	}

	public void setTotalToExpire(Integer totalToExpire) {
		this.totalToExpire = totalToExpire;
	}

	public List<Svpa> getExpired() {
		return expired;
	}

	public void setExpired(List<Svpa> expired) {
		this.expired = expired;
	}

	public Integer getTotalExpired() {
		return totalExpired;
	}

	public void setTotalExpired(Integer totalExpired) {
		this.totalExpired = totalExpired;
	}

	public void setSvpaService(DataService<Svpa> svpaService) {
		this.svpaService = svpaService;
	}


}
