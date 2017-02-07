package br.com.csl.lynx.auth;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.exception.ServiceException;

@Component
public class AuthProvider implements AuthenticationProvider, Serializable {

	private static final long serialVersionUID = 7451829705217400761L;

	@Autowired
	private DataService<Usuario> usuarioService;

	public void logout() {
		SecurityContextHolder.clearContext();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		List<Role> roles = new ArrayList<Role>();

		Usuario user = usuarioService.find("username", username);

		if (user == null) {
			throw new UsernameNotFoundException("Usuário não existe!");
		}

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		if (passwordEncryptor.checkPassword(password, user.getPassword())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
			sdf.setCalendar(now);
			
			user.setLastLogin(now);
			
			try {
				usuarioService.save(user);
			} catch (ServiceException e) {
			}
			
			roles = user.getRoles();
		} else {
			throw new BadCredentialsException("Usuário/Senha incorretos!");
		}
		return new UsernamePasswordAuthenticationToken(user, null, roles);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
