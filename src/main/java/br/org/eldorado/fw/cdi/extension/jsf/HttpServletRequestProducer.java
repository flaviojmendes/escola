package br.org.eldorado.fw.cdi.extension.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestProducer {

	@Inject
	FacesContext facesContext;
	
//	@Produces @RequestScoped
//	public HttpServletRequest getHttpServletRequest(){
//		return (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
//	}
}
