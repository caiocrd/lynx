package br.com.csl.lynx.controller.svpa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.CallCenterSvpaAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class CloseSvpaController extends CallCenterSvpaAbstractController {

	private static final long serialVersionUID = 1L;
	
	public CloseSvpaController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.DONE));
	}
	
}
