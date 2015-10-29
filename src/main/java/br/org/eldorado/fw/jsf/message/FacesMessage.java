package br.org.eldorado.fw.jsf.message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesMessage extends javax.faces.application.FacesMessage{

	private ResourceBundle resourceBundle;

	
    private static FacesMessage instance;

    private FacesMessage() {
    	resourceBundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");
    }

    public static FacesMessage getInstance(){
          if (instance == null) {
              instance = new FacesMessage();
          }
          return instance;
    }
	
	public FacesMessage addInfo(String message, Object ... params){
		return addMsgFromBundle(javax.faces.application.FacesMessage.SEVERITY_INFO, message, params);
	}
	
	public FacesMessage addError(String message, Object ... params){
		return addMsgFromBundle(javax.faces.application.FacesMessage.SEVERITY_ERROR, message, params);
	}
	
	public FacesMessage addWarn(String message, Object ... params){
		return addMsgFromBundle(javax.faces.application.FacesMessage.SEVERITY_WARN, message, params);
	}
	
	public FacesMessage addFatal(String message, Object ... params){
		return addMsgFromBundle(javax.faces.application.FacesMessage.SEVERITY_FATAL, message, params);
	}
	
	public FacesMessage addMsgFromBundle(Severity severity, String message, Object ... params){
		message = getMsgFromBundle(message);
		if (params.length > 0){
			message = MessageFormat.format(message, params);
		}
		FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(severity, message, message));
		return this;
	}
	
	public String getMsgFromBundle(String key){
		try{
			String msgBundle = resourceBundle.getString(key);
			return msgBundle;
		}catch(java.util.MissingResourceException mre){
		}
		return key;
	}
	
}
