package br.com.csl.lynx.controller.generic;

import javax.faces.bean.ManagedProperty;

import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.UsersRole;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;

public abstract class UsersAbstractController extends CommonController {

	private static final long serialVersionUID = 1L;

	protected Usuario user;

	protected String pass;
	protected String confPass;

	@ManagedProperty("#{usuarioDataModel}")
	protected DataModel<Usuario> usuarioDataModel;
	
	@ManagedProperty("#{usersRolesDataModel}")
	protected DataModel<UsersRole> usersRolesDataModel;

	@ManagedProperty("#{usuarioService}")
	protected DataService<Usuario> usuarioService;

	@ManagedProperty("#{usersRolesService}")
	protected DataService<UsersRole> usersRolesService;
	
	@ManagedProperty("#{roleService}")
	protected DataService<Role> roleService;
	
	public void clear() {
		user = new Usuario();
		pass = "";
		confPass = "";
	}
	
	public abstract void remove(Usuario user);
	
	public abstract void saveUser();
	
	public void save() {
		try {
			usuarioService.save(user);
			addFacesInfoMessage("Usuário salvo com sucesso.");
			clear();
		} catch (Exception e) {
			addFacesErrorMessage("Erro ao salvar usuário. Por favor, tente novamente.");
			e.printStackTrace();
			user.setPassword(null);
		}
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getConfPass() {
		return confPass;
	}

	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}

	public DataModel<Usuario> getUsuarioDataModel() {
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(DataModel<Usuario> usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public DataModel<UsersRole> getUsersRolesDataModel() {
		return usersRolesDataModel;
	}

	public void setUsersRolesDataModel(DataModel<UsersRole> usersRolesDataModel) {
		this.usersRolesDataModel = usersRolesDataModel;
	}

	public void setUsuarioService(DataService<Usuario> usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setUsersRolesService(DataService<UsersRole> usersRolesService) {
		this.usersRolesService = usersRolesService;
	}

	public void setRoleService(DataService<Role> roleService) {
		this.roleService = roleService;
	}
	
}
