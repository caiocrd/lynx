package br.com.csl.lynx.auth;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;

import br.com.csl.lynx.session.UserSession;
import br.com.csl.utils.controller.CommonController;

@ManagedBean
@ViewScoped
public class AuthController extends CommonController implements PhaseListener {

	private static final long serialVersionUID = -6088628722023657297L;
	
	@ManagedProperty("#{userSession}")
	private UserSession userSession;

	public void logout() {
		userSession.setUsuario(null);
		redirect("/lynx/j_spring_security_logout");
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public String doLogin() throws ServletException, IOException {

		RequestDispatcher dispatcher = ((ServletRequest) getCurrentRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) getCurrentRequest(), (ServletResponse) getCurrentResponse());

		FacesContext.getCurrentInstance().responseComplete();

		return null;
	}

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent arg0) {
		
		Exception dadosIncorretosException = (Exception) getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		if (dadosIncorretosException instanceof AuthenticationException) {
			getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			
			addFacesErrorMessage(dadosIncorretosException.getMessage());
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
