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

import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Zona;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class RegionHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
  
	private List<Rip> toEvaluate;
	private Integer totalToEvaluate;
	
	private List<Rip> reversed;
	private Integer totalReversed;
	
	private List<Rip> withFeedback;
	private Integer totalWithFeedback;
	
	@ManagedProperty("#{ripService}") 
	private DataService<Rip> ripService;
	
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
			
			toEvaluate = ripService.listAliased(0, 6, criterion, Order.desc("id"));
			totalToEvaluate = ripService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.REVERSED),
					disjunction);
			
			reversed = ripService.listAliased(0, 6, criterion, Order.desc("id"));
			totalReversed = ripService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK),
					disjunction);
			
			withFeedback = ripService.listAliased(0, 6, criterion, Order.desc("id"));
			totalWithFeedback = ripService.count(criterion);
		} else {
			toEvaluate = ripService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING), Order.desc("id"));
			totalToEvaluate = ripService.count(Restrictions.eq("status", RipStatus.EVALUATING));
			
			reversed = ripService.listAliased(0, 6, Restrictions.eq("status", RipStatus.REVERSED), Order.desc("id"));
			totalReversed = ripService.count(Restrictions.eq("status", RipStatus.REVERSED));
			
			withFeedback = ripService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK), Order.desc("id"));
			totalWithFeedback = ripService.count(Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK));
		}
		
	}

	public List<Rip> getToEvaluate() {
		return toEvaluate;
	}

	public void setToEvaluate(List<Rip> toEvaluate) {
		this.toEvaluate = toEvaluate;
	}

	public Integer getTotalToEvaluate() {
		return totalToEvaluate;
	}

	public void setTotalToEvaluate(Integer totalToEvaluate) {
		this.totalToEvaluate = totalToEvaluate;
	}

	public List<Rip> getReversed() {
		return reversed;
	}

	public void setReversed(List<Rip> reversed) {
		this.reversed = reversed;
	}

	public Integer getTotalReversed() {
		return totalReversed;
	}

	public void setTotalReversed(Integer totalReversed) {
		this.totalReversed = totalReversed;
	}

	public List<Rip> getWithFeedback() {
		return withFeedback;
	}

	public void setWithFeedback(List<Rip> withFeedback) {
		this.withFeedback = withFeedback;
	}

	public Integer getTotalWithFeedback() {
		return totalWithFeedback;
	}

	public void setTotalWithFeedback(Integer totalWithFeedback) {
		this.totalWithFeedback = totalWithFeedback;
	}

	public void setRipService(DataService<Rip> ripService) {
		this.ripService = ripService;
	}

}
