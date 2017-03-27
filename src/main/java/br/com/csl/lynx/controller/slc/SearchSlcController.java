package br.com.csl.lynx.controller.slc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.filter.AdvancedFilter;
import br.com.csl.lynx.generic.AbstractSlcPrint;
import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.Slc;
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
public class SearchSlcController extends AbstractSlcPrint {

	private static final long serialVersionUID = 1L;
	
	private MovimentacaoSlc selection;
	
	private List<Usuario> usuarios;
	
	@ManagedProperty("#{usersRolesService}")
	private DataService<UsersRole> usersRolesService;
	
	public SearchSlcController() {
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
		slc = new Slc();
		selection = null;
		filter();
	}
	
	public void select() {
		if (selection != null) {
			slc = selection.getSlc();
			load();
		}
	}

	public MovimentacaoSlc getSelection() {
		return selection;
	}

	public void setSelection(MovimentacaoSlc selection) {
		this.selection = selection;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsersRolesService(DataService<UsersRole> usersRolesService) {
		this.usersRolesService = usersRolesService;
	}
	
	
}
