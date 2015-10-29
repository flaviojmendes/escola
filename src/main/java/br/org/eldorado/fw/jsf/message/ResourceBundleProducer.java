package br.org.eldorado.fw.jsf.message;


import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class ResourceBundleProducer {
	
	@Produces
	public ResourceBundle getResourceBundle(FacesContext facesContext){
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
		return bundle;
	}

}
