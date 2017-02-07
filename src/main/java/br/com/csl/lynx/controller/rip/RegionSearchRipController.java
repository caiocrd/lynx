package br.com.csl.lynx.controller.rip;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.controller.generic.RegionAbstractController;
import br.com.csl.lynx.filter.AdvancedFilter;
import br.com.csl.lynx.filter.SimpleFilter;
import br.com.csl.lynx.generic.AbstractRipPrint;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.UsersRole;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.lynx.support.RipStatus;
import br.com.csl.utils.data.DataService;

/**
 * @author Bruno Henrique de Castro
 * 
 */

@ManagedBean
@ViewScoped
public class RegionSearchRipController extends RegionAbstractController {

	private static final long serialVersionUID = 1L;
	
	private Movimentacao selection;
	
	private List<Usuario> usuarios;
	
	@ManagedProperty("#{usersRolesService}")
	private DataService<UsersRole> usersRolesService;
	
	
	@PostConstruct
	public void init() {
		setRestraints();
		
		if(restraints != null)
			filter = new AdvancedFilter(restraints);
		else
			filter = new AdvancedFilter(Restrictions.eq("movimento", Movement.OPEN));
		clear();
	}
	
	public void clear() {
		super.clear();
		
		selection = null;
	}
	
	public void back() {
		rip = new Rip();
		selection = null;
		filter();
	}
	
	public void select() {
		if (selection != null) {
			rip = selection.getRip();
			load();
		}
	}

	public Movimentacao getSelection() {
		return selection;
	}

	public void setSelection(Movimentacao selection) {
		this.selection = selection;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsersRolesService(DataService<UsersRole> usersRolesService) {
		this.usersRolesService = usersRolesService;
	}
	
	
}
