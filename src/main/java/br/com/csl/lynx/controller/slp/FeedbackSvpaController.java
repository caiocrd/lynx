package br.com.csl.lynx.controller.slp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.RegionSvpaAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class FeedbackSvpaController extends RegionSvpaAbstractController {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		setRestraints();
		
		if (restraints != null) {
			Criterion criterion = Restrictions.and(
					Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK),
					restraints);
			filter = new SimpleFilter(criterion);
		} else {
			filter = new SimpleFilter(Restrictions.eq("status", RipStatus.EVALUATING_FEEDBACK));
		}
		
		clear();
	}
}
