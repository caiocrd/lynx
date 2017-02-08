package br.com.csl.lynx.controller.siep;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.CallCenterSiepAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class CloseSiepController extends CallCenterSiepAbstractController {

	private static final long serialVersionUID = 1L;
	
	public CloseSiepController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.DONE));
	}
	
}
