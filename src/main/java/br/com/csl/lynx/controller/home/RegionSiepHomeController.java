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
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.lynx.support.Zona;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;

@ManagedBean
@ViewScoped
public class RegionSiepHomeController extends CommonController {

	private static final long serialVersionUID = -2090925333331272948L;
  
	private List<Siep> toEvaluate;
	private Integer totalToEvaluate;
	
	private List<Siep> reversed;
	private Integer totalReversed;
	
	private List<Siep> withFeedback;
	private Integer totalWithFeedback;
	
	@ManagedProperty("#{siepService}") 
	private DataService<Siep> siepService;
	
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
			
			toEvaluate = siepService.listAliased(0, 6, criterion, Order.desc("id"));
			totalToEvaluate = siepService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.REVERSED),
					disjunction);
			
			reversed = siepService.listAliased(0, 6, criterion, Order.desc("id"));
			totalReversed = siepService.count(criterion);
			
			criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK),
					disjunction);
			
			withFeedback = siepService.listAliased(0, 6, criterion, Order.desc("id"));
			totalWithFeedback = siepService.count(criterion);
		} else {
			toEvaluate = siepService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING), Order.desc("id"));
			totalToEvaluate = siepService.count(Restrictions.eq("status", RipStatus.EVALUATING));
			
			reversed = siepService.listAliased(0, 6, Restrictions.eq("status", RipStatus.REVERSED), Order.desc("id"));
			totalReversed = siepService.count(Restrictions.eq("status", RipStatus.REVERSED));
			
			withFeedback = siepService.listAliased(0, 6, Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK), Order.desc("id"));
			totalWithFeedback = siepService.count(Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK));
		}
		
	}

	public List<Siep> getToEvaluate() {
		return toEvaluate;
	}

	public void setToEvaluate(List<Siep> toEvaluate) {
		this.toEvaluate = toEvaluate;
	}

	public Integer getTotalToEvaluate() {
		return totalToEvaluate;
	}

	public void setTotalToEvaluate(Integer totalToEvaluate) {
		this.totalToEvaluate = totalToEvaluate;
	}

	public List<Siep> getReversed() {
		return reversed;
	}

	public void setReversed(List<Siep> reversed) {
		this.reversed = reversed;
	}

	public Integer getTotalReversed() {
		return totalReversed;
	}

	public void setTotalReversed(Integer totalReversed) {
		this.totalReversed = totalReversed;
	}

	public List<Siep> getWithFeedback() {
		return withFeedback;
	}

	public void setWithFeedback(List<Siep> withFeedback) {
		this.withFeedback = withFeedback;
	}

	public Integer getTotalWithFeedback() {
		return totalWithFeedback;
	}

	public void setTotalWithFeedback(Integer totalWithFeedback) {
		this.totalWithFeedback = totalWithFeedback;
	}

	public void setSiepService(DataService<Siep> siepService) {
		this.siepService = siepService;
	}

}
