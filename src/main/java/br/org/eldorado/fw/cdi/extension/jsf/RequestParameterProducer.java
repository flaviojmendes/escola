package br.org.eldorado.fw.cdi.extension.jsf;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class RequestParameterProducer implements Serializable {

    private static final long serialVersionUID = -4260202951977249652L;
    
    @Inject
    FacesContext facesContext;

    @Produces
    @RequestParameter
    String getRequestParameter(InjectionPoint ip) {
        String name = ip.getAnnotated().getAnnotation(RequestParameter.class)
                .value();

        if ("".equals(name))
            name = ip.getMember().getName();

        String value = facesContext.getExternalContext().getRequestParameterMap()
                .get(name);
        
        //Se no rendered o parametro tiver sido setado como atributo, ele nao estara disponivel no ParameterMap, mas sim no Atributtes
        if (value == null){
        	value = (String) facesContext.getExternalContext().getRequestMap().get(name);
        }
        return value;
    }

}
