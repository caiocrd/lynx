package br.com.csl.lynx.controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.StrongPasswordEncryptor;

import br.com.csl.lynx.controller.generic.UsersAbstractController;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Permission;

/**
 * @author Bruno Henrique de Castro
 * 
 */
@ViewScoped
@ManagedBean
public class AtendenteSvpaController extends UsersAbstractController {

	private static final long serialVersionUID = 1L;

	public AtendenteSvpaController() {
		clear();
	}

	@PostConstruct
	public void init() {
		usersRolesDataModel.newRestraints("r.name", Permission.ATENDENTE_SVPA.name());
	}

	public void remove(Usuario user) {
		if (user.getId() == null) {
			addFacesErrorMessage("Erro ao remover usuário!");
			return;
		}
		
		Usuario u = usuarioService.find(Restrictions.eq("username", user.getUsername()));
		
		u.getRoles().remove(roleService.find(Restrictions.eq("name", Permission.ATENDENTE_SVPA.name())));
		
		try {
			usuarioService.save(u);

			addFacesInfoMessage("Permissão revogada com sucesso.");
			clear();
		} catch (Exception e) {
			addFacesErrorMessage("Erro ao remover usuário. Por favor, tente novamente.");
			e.printStackTrace();
		}
	}

	public void saveUser() {
		if (user == null) {
			addFacesErrorMessage("Erro ao salvar usuário!");
			return;
		}
		
		user.setUsername(user.getUsername().toLowerCase());

		if (user.getId() == null) {
			Usuario u = usuarioService.find(Restrictions.and(Restrictions.eq("username", user.getUsername()), Restrictions.like("nome", user.getNome(), MatchMode.ANYWHERE)));
			
			if (u != null) {
				if (usersRolesService.count(
							Restrictions.and(
									Restrictions.eq("u.username", user.getUsername()),
									Restrictions.or(
											Restrictions.eq("r.name", Permission.ATENDENTE_SVPA.name()), 
											Restrictions.eq("r.name", Permission.CALLCENTER_SVPA.name())))) == 0) {
					
					u.getRoles().add(roleService.find("name", Permission.ATENDENTE_SVPA.name()));
					addFacesWarnMessage("Usuário já cadastrado! Adicionando permissão.");
					user = u;
					save();
					return;
				} else {
					addFacesErrorMessage("Usuário já cadastrado!");
					return;
				}
			}
		}

		if (!confPass.equals(pass)) {
			confPass = null;
			pass = null;
			addFacesErrorMessage("Senhas não conferem!");
			return;
		}
		
		confPass = null;
		
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		if (!pass.isEmpty())
			user.setPassword(passwordEncryptor.encryptPassword(pass));
		
		if (user.getId() == null) {
			user.setDataCadastro(Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00")));
		
			user.setRoles(new ArrayList<Role>());
		
			user.getRoles().add(roleService.find("name", Permission.ATENDENTE_SVPA.name()));
		}
		
		save();
	}

}
