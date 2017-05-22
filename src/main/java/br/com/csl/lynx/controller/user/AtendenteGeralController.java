package br.com.csl.lynx.controller.user;

import java.util.Calendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jasypt.util.password.StrongPasswordEncryptor;

import br.com.csl.lynx.controller.generic.UsersAbstractController;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Permission;

/**
 * @author Caio Cesar
 * 
 */
@ViewScoped
@ManagedBean
public class AtendenteGeralController extends UsersAbstractController {

	private static final long serialVersionUID = 1L;

	public AtendenteGeralController() {
		clear();
	}

	@PostConstruct
	public void init() {
		
	}

	public void remove(Usuario user) {
		if (user.getId() == null) {
			addFacesErrorMessage("Erro ao remover usuário!");
			return;
		}
		
		Usuario u = usuarioService.find(Restrictions.eq("username", user.getUsername()));
		
		u.getRoles().remove(roleService.find(Restrictions.eq("name", Permission.ATENDENTE.name())));
		
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
				
				u.setRoles(roleService.list(Restrictions.like("name", Permission.ATENDENTE.name(), MatchMode.ANYWHERE)));
				addFacesWarnMessage("Usuário já cadastrado! Adicionando permissão.");
				user = u;
				save();
				return;
				
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
		
			user.setRoles(roleService.list(Restrictions.like("name", Permission.ATENDENTE.name(), MatchMode.ANYWHERE)));
		}
		
		save();
	}

}
