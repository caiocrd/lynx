package br.com.csl.utils.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.csl.lynx.model.Role;
import br.com.csl.lynx.model.Usuario;

import com.sun.faces.component.visit.FullVisitContext;

/**
 * @author Bruno Henrique de Castro
 * @version 1.0
 *
 */
public abstract class CommonController implements Serializable {

	private static final long serialVersionUID = -6288685849499167752L;

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public Application getApplication() {
		return getFacesContext().getApplication();
	}
	
	public ApplicationContext getApplicationContext(){
		WebApplicationContext appContext =  WebApplicationContextUtils.getRequiredWebApplicationContext(getCurrentRequest().getSession().getServletContext());
		
		return appContext;
	}
	
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public String getParameter(String param) {
		return getCurrentRequest().getParameter(param);
	}
	
	public Integer getParameterInt(String param) {
		String value = getParameter(param);
		Integer result = null;

		try {
			result = Integer.parseInt(value);
		} catch(Exception e) {	}
		
		return result;
	}

	public Boolean getParameterBoolean(String param) {
		return Boolean.valueOf(getParameter(param));
	}
	
	public HttpServletRequest getCurrentRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public HttpServletResponse getCurrentResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	public void redirect(String link) {
		try {
			getExternalContext().redirect(link);
		} catch (IOException e) {
			addFacesErrorMessage("Página não encontrada!");
		}
	}
	
	public SecurityContext getSecutrityContext() {
		return SecurityContextHolder.getContext();
	}
	
	public Authentication getAuthentication() {
		return getSecutrityContext().getAuthentication();
	}
	
	public Usuario getLoggedUser() {
		if (getAuthentication().getPrincipal() instanceof Usuario) {
			return (Usuario) getAuthentication().getPrincipal();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getAuthorities() {
		return (List<Role>) getLoggedUser().getAuthorities();
	}
	
	public UIComponent findComponent(final String id){
	    FacesContext context = FacesContext.getCurrentInstance(); 
	    UIViewRoot root = context.getViewRoot();
	    final UIComponent[] found = new UIComponent[1];
	    root.visitTree(new FullVisitContext(context), new VisitCallback() {     
	        @Override
	        public VisitResult visit(VisitContext context, UIComponent component) {
	            if(component.getId().equals(id)){
	                found[0] = component;
	                return VisitResult.COMPLETE;
	            }
	            return VisitResult.ACCEPT;              
	        }
	    });
	    return found[0];
	}

	/**
	 * Add JSF info message.
	 * 
	 * @param msg
	 *            info message string
	 */
	public  void addFacesInfoMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}
	
	/**
	 * Add global JSF info message.
	 * 
	 * @param msg
	 */
	public  void addGlobalInfoMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
		ctx.addMessage(null, fm);
	}

	/**
	 * Add JSF error message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	/**
	 * Add JSF error message for a specific attribute.
	 * 
	 * @param attrName
	 *            name of attribute
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String attrName, String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				attrName, msg);
		ctx.addMessage(getRootViewComponentId(), fm);
	}
	
	/**
	 * Add JSF error message for a specific attribute
	 * and a specific component
	 * 
	 * @param attrName
	 *            name of attribute
	 * @param msg
	 *            error message string
	 */
	public  void addFacesErrorMessage(String componentId, String attrName, String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				attrName, msg);
		ctx.addMessage(componentId, fm);
	}

	/**
	 * Add JSF warn message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesWarnMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	/**
	 * Add JSF Fatal message.
	 * 
	 * @param msg
	 *            error message string
	 */
	public  void addFacesFatalMessage(String msg) {
		FacesContext ctx = getFacesContext();
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, "");
		ctx.addMessage(getRootViewComponentId(), fm);
	}

	// Informational getters

	/**
	 * Get view id of the view root.
	 * 
	 * @return view id of the view root
	 */
	public  String getRootViewId() {
		return getFacesContext().getViewRoot().getViewId();
	}

	/**
	 * Get component id of the view root.
	 * 
	 * @return component id of the view root
	 */
	public  String getRootViewComponentId() {
		return getFacesContext().getViewRoot().getId();
	}
	
	
}
