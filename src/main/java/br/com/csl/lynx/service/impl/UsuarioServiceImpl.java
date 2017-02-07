package br.com.csl.lynx.service.impl;

import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.service.UsuarioService;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.data.impl.AbstractDataService;

@Service("usuarioService")
public class UsuarioServiceImpl extends AbstractDataService<Usuario> implements UsuarioService, UserDetailsService {

	private static final long serialVersionUID = 1959818602191651235L;

	@Autowired
	private DataAccess<Usuario> usuarioDAO;
	
	@Override
	public DataAccess<Usuario> getDAO() {
		return usuarioDAO;
	}
	
	public UsuarioServiceImpl() {
		setProjections(Projections.projectionList());
		getProjections().add(Projections.groupProperty("id"), "id")
			.add(Projections.property("nome"), "nome")
			.add(Projections.property("username"), "username")
			.add(Projections.property("lastLogin"), "lastLogin")
			.add(Projections.property("dataCadastro"), "dataCadastro")
			.add(Projections.property("email"), "email")
			.add(Projections.property("telefone"), "telefone");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return find("username", username);
	}
	
}
