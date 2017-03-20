package br.com.csl.lynx.session;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Siep;
import br.com.csl.lynx.model.Slp;
import br.com.csl.lynx.model.Svpa;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.controller.CommonController;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@ManagedBean
@SessionScoped
public class UserSession extends CommonController implements Serializable {

	private static final long serialVersionUID = 6822433154937876999L;

	private String password;
	private String newPassword;
	private String repeatPassword;

	private Usuario user;
	private List<Role> permissions;
	private Rip lastRip;
	private Siep lastSiep;
	private Svpa lastSvpa;
	private Slp lastSlp;

	@ManagedProperty("#{usuarioService}")
	private DataService<Usuario> usuarioService;

	public Usuario getUser() {
		if (user == null) {
			user = getLoggedUser();
		}
		return user;
	}

	public List<Role> getPermissions() {
		if (permissions == null) {
			permissions = getAuthorities();
		}
		return permissions;
	}

	public void chngPasswd() {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		if (!newPassword.contentEquals(repeatPassword)) {
			addFacesErrorMessage("Senhas não conferem");
		} else if (!passwordEncryptor.checkPassword(password,
				user.getPassword())) {
			addFacesErrorMessage("Senhas atual não confere");
		} else {
			user.setPassword(passwordEncryptor.encryptPassword(newPassword));
			try {
				user = usuarioService.save(user);
			} catch (ServiceException e) {
				addFacesErrorMessage("Erro ao alterar senha.");
				e.printStackTrace();
			} finally {
				password = "";
				newPassword = "";
				repeatPassword = "";
			}
			addFacesInfoMessage("Senha alterada com sucesso");
		}
	}

	public Boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
	}

	public void setUsuario(Usuario user) {
		this.user = user;
	}

	public DataService<Usuario> getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(DataService<Usuario> userService) {
		this.usuarioService = userService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public Rip getLastRip() {
		return lastRip;
	}

	public void setLastRip(Rip lastRip) {
		this.lastRip = lastRip;
	}

	public Siep getLastSiep() {
		return lastSiep;
	}

	public void setLastSiep(Siep lastSiep) {
		this.lastSiep = lastSiep;
	}

	public Svpa getLastSvpa() {
		return lastSvpa;
	}

	public void setLastSvpa(Svpa lastSvpa) {
		this.lastSvpa = lastSvpa;
	}

	public Slp getLastSlp() {
		return lastSlp;
	}

	public void setLastSlp(Slp lastSlp) {
		this.lastSlp = lastSlp;
	}
	
	
}
