package br.com.csl.lynx.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.lynx.support.Permission;
import br.com.csl.utils.controller.CommonController;

@ManagedBean
@NoneScoped
public class NavigationController extends CommonController {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public void userRoleRedirect() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Role> roles = (List<Role>) auth.getAuthorities();
		
		List<String> authorities = new ArrayList<String>();
		
		for (Role aux : roles)
			authorities.add(aux.getName());
		
		int cont = 0;
		
		String redirecionar = "";
		
		if (authorities.contains(Permission.DIRECAO.toString())){
			cont++;
			redirecionar = "manager";
		}
		
		if (authorities.contains(Permission.CALLCENTER.toString()) || authorities.contains(Permission.ATENDENTE.toString())) {
			cont++;
			redirecionar = "callcenter";
		}
		
		if (authorities.contains(Permission.PRESTADORA.toString()) || authorities.contains(Permission.EXECUTOR.toString())){
			cont++;
			redirecionar = "executor";
		}
		
		if (authorities.contains(Permission.REGIAO.toString()) || authorities.contains(Permission.ZONA_NORTE.toString()) || authorities.contains(Permission.ZONA_SUL.toString()) ||
				authorities.contains(Permission.ZONA_LESTE.toString()) || authorities.contains(Permission.ZONA_OESTE.toString())){
			cont++;
			redirecionar = "region";
		}
		
		if (cont == 1) {
			redirect("/lynx/pages/" + redirecionar + "/");
		}
	}
	
	public void loggedUserRedirect() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() != null && auth.getPrincipal() instanceof Usuario)
			redirect("/lynx/pages/");
	}

}
