package br.com.csl.lynx.controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
public class UserController extends UsersAbstractController {

	private static final long serialVersionUID = 1L;

	private String callcenter;
	private String direcao;
	private String regiao;
	private String prestadora;

	public UserController() {
		clear();
	}

	@PostConstruct
	public void init() {
		usuarioDataModel.getRestraints().add(Restrictions.neOrIsNotNull("password", ""));
	}
	
	public void clear() {
		super.clear();
		
		clearPermissions();
	}

	public void clearPermissions() {
		callcenter = "";
		direcao = "";
		regiao = "";
		prestadora = "";
	}
	
	public void remove(Usuario user) {
		if (user.getId() == null) {
			addFacesErrorMessage("Erro ao remover usuário!");
			return;
		}

		try {
			user.setRoles(new ArrayList<Role>());
			user.setPassword("");
			usuarioService.save(user);

			addFacesInfoMessage("Usuário removido com sucesso.");
		} catch (Exception e) {
			addFacesErrorMessage("Erro ao remover usuário. Por favor, tente novamente.");
			e.printStackTrace();
		}
	}

	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		if (!callcenter.isEmpty())
			permissions.add(callcenter);
		if (!direcao.isEmpty())
			permissions.add(direcao);
		if (!prestadora.isEmpty())
			permissions.add(prestadora);
		if (!regiao.isEmpty())
			permissions.add(regiao);
		
		return permissions;
	}
	
	public void saveUser() {
		if (user == null) {
			addFacesErrorMessage("Erro ao salvar usuário!");
			return;
		}
		
		if (getPermissions().size() == 0) {
			addFacesErrorMessage("Adicione as permissões do usuário!");
			return;
		}

		if (!confPass.equals(pass)) {
			confPass = null;
			pass = null;
			addFacesErrorMessage("Senhas não conferem!");
			return;
		}

		confPass = null;
		user.setUsername(user.getUsername().toLowerCase());

		if (user.getId() == null) {
			Usuario u = usuarioService.find(Restrictions.or(Restrictions.eq("username", user.getUsername()), Restrictions.like("nome", user.getNome(), MatchMode.ANYWHERE)));
			if (u != null) {
				if (u.getPassword().isEmpty()) {
					u.setEmail(user.getEmail());
					u.setNome(user.getNome());
					u.setTelefone(user.getTelefone());
					user = u;
				} else {
					addFacesErrorMessage("Usuário já cadastrado!");
					return;
				}
			}
		}

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		if (!pass.isEmpty())
			user.setPassword(passwordEncryptor.encryptPassword(pass));
		
		if (user.getId() == null)
			user.setDataCadastro(Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00")));
		
		user.setRoles(new ArrayList<Role>());
		
		for (String aux : getPermissions()) {
			user.getRoles().add(roleService.find("name", aux));
		}
		
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
	
	public void select() {
		clearPermissions();
		
		if (user != null && user.getRoles() != null) {
			for (Role aux : user.getRoles()) {
				if (aux.getName().equals(Permission.ATENDENTE.name()) ||
					aux.getName().equals(Permission.CALLCENTER.name())) {
					
					callcenter = aux.getName();
				}
				if (aux.getName().equals(Permission.PRESTADORA.name()) ||
					aux.getName().equals(Permission.EXECUTOR.name())) {
					prestadora = aux.getName();
				}
				if (aux.getName().equals(Permission.DIRECAO.name())) {
					direcao = aux.getName();
				}
				if (aux.getName().equals(Permission.REGIAO.name()) ||
					aux.getName().equals(Permission.ZONA_NORTE.name()) || 
					aux.getName().equals(Permission.ZONA_SUL.name()) || 
					aux.getName().equals(Permission.ZONA_LESTE.name()) || 
					aux.getName().equals(Permission.ZONA_OESTE.name())) {
					
					regiao = aux.getName();
				}
			}
		}
	}

	public String getCallcenter() {
		return callcenter;
	}

	public void setCallcenter(String callcenter) {
		this.callcenter = callcenter;
	}

	public String getDirecao() {
		return direcao;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getPrestadora() {
		return prestadora;
	}

	public void setPrestadora(String prestadora) {
		this.prestadora = prestadora;
	}

}
