package br.com.csl.lynx.controller.rip;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.CallCenterAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class ReverseRipController extends CallCenterAbstractController {

	private static final long serialVersionUID = 1L;
	
	public ReverseRipController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.REVERSING));
	}

}
