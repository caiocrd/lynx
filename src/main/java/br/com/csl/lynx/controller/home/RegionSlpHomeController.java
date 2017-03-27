package br.com.csl.lynx.controller.home;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Zona;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class RegionSlpHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
  
	private List<Slp> toEvaluate;
	private Integer totalToEvaluate;
	
	private List<Slp> reversed;
	private Integer totalReversed;
	
	private List<Slp> withFeedback;
	private Integer totalWithFeedback;
	
	@ManagedProperty("#{slpService}") 
	private DataService<Slp> slpService;
	
	@PostConstruct
	public void init() {
		Disjunction disjunction = Restrictions.disjunction();
		for (Role aux : getAuthorities()) {
			if ((aux.getName().equals(Zona.ZONA_NORTE.name()))
					|| (aux.getName().equals(Zona.ZONA_SUL.name()))
					|| (aux.getName().equals(Zona.ZONA_LESTE.name()))
					|| (aux.getName().equals(Zona.ZONA_OESTE.name()))) {
				disjunction.add(Restrictions.eq("bairro.zona",
						Zona.valueOf(aux.getName())));
			} else if (aux.getName().equals(Permission.REGIAO.name())) {
				disjunction = null;
				break;
			}
		}

		if (disjunction != null) {
			Criterion criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.EVALUATING),
					disjunction);
			
			toEvaluate = slpService.listAliased(0, 6, criterion, Order.desc("id"));
			totalToEvaluate = slpService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.REVERSED),
					disjunction);
			
			reversed = slpService.listAliased(0, 6, criterion, Order.desc("id"));
			totalReversed = slpService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK),
					disjunction);
			
			withFeedback = slpService.listAliased(0, 6, criterion, Order.desc("id"));
			totalWithFeedback = slpService.count(criterion);
		} else {
			toEvaluate = slpService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING), Order.desc("id"));
			totalToEvaluate = slpService.count(Restrictions.eq("status", RipStatus.EVALUATING));
			
			reversed = slpService.listAliased(0, 6, Restrictions.eq("status", RipStatus.REVERSED), Order.desc("id"));
			totalReversed = slpService.count(Restrictions.eq("status", RipStatus.REVERSED));
			
			withFeedback = slpService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK), Order.desc("id"));
			totalWithFeedback = slpService.count(Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK));
		}
		
	}

	public List<Slp> getToEvaluate() {
		return toEvaluate;
	}

	public void setToEvaluate(List<Slp> toEvaluate) {
		this.toEvaluate = toEvaluate;
	}

	public Integer getTotalToEvaluate() {
		return totalToEvaluate;
	}

	public void setTotalToEvaluate(Integer totalToEvaluate) {
		this.totalToEvaluate = totalToEvaluate;
	}

	public List<Slp> getReversed() {
		return reversed;
	}

	public void setReversed(List<Slp> reversed) {
		this.reversed = reversed;
	}

	public Integer getTotalReversed() {
		return totalReversed;
	}

	public void setTotalReversed(Integer totalReversed) {
		this.totalReversed = totalReversed;
	}

	public List<Slp> getWithFeedback() {
		return withFeedback;
	}

	public void setWithFeedback(List<Slp> withFeedback) {
		this.withFeedback = withFeedback;
	}

	public Integer getTotalWithFeedback() {
		return totalWithFeedback;
	}

	public void setTotalWithFeedback(Integer totalWithFeedback) {
		this.totalWithFeedback = totalWithFeedback;
	}

	public void setSlpService(DataService<Slp> slpService) {
		this.slpService = slpService;
	}

}
