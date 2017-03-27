package br.com.csl.lynx.controller.slc;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.CallCenterSlcAbstractController;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.support.RipStatus;

@ManagedBean
@ViewScoped
public class CloseSlcController extends CallCenterSlcAbstractController {

	private static final long serialVersionUID = 1L;
	
	public CloseSlcController() {
		filter = new SimpleFilter(Restrictions.eq("status", RipStatus.DONE));
	}
	
}
