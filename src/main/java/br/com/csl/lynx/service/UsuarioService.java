package br.com.csl.lynx.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.data.DataService;

public interface UsuarioService extends DataService<Usuario>, UserDetailsService {

}
