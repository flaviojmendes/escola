package br.org.eldorado.fw.cdi.extension.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

class FacesContextProducer {

	@Produces @RequestScoped FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();

	}

}