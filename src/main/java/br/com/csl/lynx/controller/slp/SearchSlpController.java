package br.com.csl.lynx.controller.slp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.filter.AdvancedFilter;
import br.com.csl.lynx.generic.AbstractSlpPrint;
import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.model.UsersRole;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Movement;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.PrintMethod;
import br.com.csl.utils.data.DataService;

/**
 * @author Caio Cesar Dantas
 * 
 */

@ManagedBean
@ViewScoped
public class SearchSlpController extends AbstractSlpPrint {

	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSlp selection;
	
	private List<Usuario> usuarios;
	
	@ManagedProperty("#{usersRolesService}")
	private DataService<UsersRole> usersRolesService;
	
	public SearchSlpController() {
		filter = new AdvancedFilter(Restrictions.eq("movimento", Movement.OPEN));
		printMethod = PrintMethod.SIMPLE;
		usuarios = new ArrayList<>();
	}
	
	@PostConstruct
	public void init() {
	    List<UsersRole> ur = usersRolesService.listAliased(Restrictions.or(
				Restrictions.eq("r.name", Permission.ATENDENTE.name()),
				Restrictions.eq("r.name", Permission.CALLCENTER.name())), Order.asc("u.nome"));
	    
	    for (UsersRole aux : ur) {
	    	if (!usuarios.contains(aux.getUsuario())) {
	    		usuarios.add(aux.getUsuario());
	    	}
	    }
	    
	    clear();
	}
	
	public void clear() {
		super.clear();
		
		selection = null;
	}
	
	public void back() {
		slp = new Slp();
		selection = null;
		filter();
	}
	
	public void select() {
		if (selection != null) {
			slp = selection.getSlp();
			load();
		}
	}

	public MovimentacaoSlp getSelection() {
		return selection;
	}

	public void setSelection(MovimentacaoSlp selection) {
		this.selection = selection;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsersRolesService(DataService<UsersRole> usersRolesService) {
		this.usersRolesService = usersRolesService;
	}
	
	
}
