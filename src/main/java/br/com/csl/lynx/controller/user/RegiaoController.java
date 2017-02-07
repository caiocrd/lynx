package br.com.csl.lynx.controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.StrongPasswordEncryptor;

import br.com.csl.lynx.controller.generic.UsersAbstractController;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Permission;
import br.com.csl.lynx.support.Zona;

/**
 * @author Bruno Henrique de Castro
 * 
 */
@ViewScoped
@ManagedBean
public class RegiaoController extends UsersAbstractController {

	private static final long serialVersionUID = 1L;
	
	private final LogicalExpression disjunction;

	private List<Role> regionRoles;
 
	private Zona zona;

	public RegiaoController() {
		clear();
		disjunction = Restrictions.or(
					Restrictions.eq("r.name", Permission.ZONA_NORTE.name()),
					Restrictions.or(
							Restrictions.eq("r.name", Permission.ZONA_SUL.name()),
							Restrictions.or(
									Restrictions.eq("r.name", Permission.ZONA_LESTE.name()),
									Restrictions.or(
									Restrictions.eq("r.name", Permission.ZONA_OESTE.name()),
									Restrictions.eq("r.name", Permission.REGIAO.name())))));
		
	}

	@PostConstruct
	public void init() {
		usersRolesDataModel.removeRestraints();
 		usersRolesDataModel.getRestraints().add(disjunction);
 		
 		regionRoles = new ArrayList<Role>();
 		
 		regionRoles = roleService.list(
 				Restrictions.or(
 						Restrictions.eq("name", Permission.ZONA_NORTE.name()),
 						Restrictions.or(
 								Restrictions.eq("name", Permission.ZONA_SUL.name()),
 								Restrictions.or(
 										Restrictions.eq("name", Permission.ZONA_LESTE.name()),
 										Restrictions.or(
 										Restrictions.eq("name", Permission.ZONA_OESTE.name()),
 										Restrictions.eq("name", Permission.REGIAO.name()))))));
	}

	public void remove(Usuario user) {
		if (user.getId() == null) {
			addFacesErrorMessage("Erro ao remover usuário.");
			return;
		}
		
		Usuario u = usuarioService.find("username", user.getUsername());
		
		u.getRoles().removeAll(regionRoles);
		
		try {
			usuarioService.save(u);

			addFacesInfoMessage("Permissão revogada com sucesso.");
			clear();
		} catch (Exception e) {
			addFacesErrorMessage("Erro ao remover usuário", "Por favor, tente novamente.");
			e.printStackTrace();
		}
	}

	public void saveUser() {
		if (user == null) {
			addFacesErrorMessage("Erro ao salvar usuário.");
			return;
		}
		
		user.setUsername(user.getUsername().toLowerCase());

		if (user.getId() == null) {
			Usuario u = usuarioService.find(Restrictions.and(Restrictions.eq("username", user.getUsername()), Restrictions.like("nome", user.getNome(), MatchMode.ANYWHERE)));
			
			if (u != null) {
				
				u.getRoles().removeAll(regionRoles);
				u.getRoles().add(roleService.find("name", zona.name()));
				
				addFacesWarnMessage("Usuário já cadastrado. Adicionando permissão.");
				
				user = u;
				save();
				return;
			}
			
			user.setDataCadastro(Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00")));
			user.setRoles(new ArrayList<Role>());
			user.getRoles().add(roleService.find("name", zona.name()));
		} else {
			user.getRoles().removeAll(regionRoles);
			user.getRoles().add(roleService.find("name", zona.name()));
		}

		if (!confPass.equals(pass)) {
			confPass = null;
			pass = null;
			addFacesErrorMessage("Senhas não conferem.");
			return;
		}
		
		confPass = null;
		
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		if (!pass.isEmpty())
			user.setPassword(passwordEncryptor.encryptPassword(pass));
		
		save();
	}

	public void select() {
		if (user != null && user.getRoles() != null) {
			for (Role aux : user.getRoles()) {
				if (regionRoles.contains(aux)) {
					zona = Zona.valueOf(aux.getName());
				}
			}
		}
	}
	
	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

}
