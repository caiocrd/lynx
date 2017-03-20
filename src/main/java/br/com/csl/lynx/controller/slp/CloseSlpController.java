package br.com.csl.lynx.controller.slp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.CallCenterSlpAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class CloseSlpController extends CallCenterSlpAbstractController {

	private static final long serialVersionUID = 1L;
	
	public CloseSlpController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.DONE));
	}
	
}
