package br.org.eldorado.fw.bean;

import java.lang.annotation.Annotation;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.org.eldorado.fw.jsf.message.FacesMessage;
import br.org.eldorado.fw.util.ClassInterceptor;

public abstract class BeanSupport {

	@Inject
	private FacesMessage facesMessage;
	
	protected void addParamRequest(String key, Object value){
		//		getFacesContext().getExternalContext().getRequestParameterMap().put(key,value.toString());
		getFacesContext().getExternalContext().getRequestMap().put(key,value);
	}

	protected Object getRequestParam(String param){
		return getFacesContext().getExternalContext().getRequestParameterMap().get(param);
	}
	
	protected void addMsgInfo(String message, Object ... params){
		facesMessage.addInfo(message, params);
	}
	
	protected void addMsgError(String message, Object ... params){
		facesMessage.addError(message,params);
	}
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public void interceptMethod(Class<? extends Annotation> annotation, Object ... params) throws Throwable{
		ClassInterceptor.callMethodByAnnotation(this, annotation, params);
	}
	
	protected HttpSession getHttpSession(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		return req.getSession();
	}

}
