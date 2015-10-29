package br.org.eldorado.fw.jsf;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

@Named(value = "doubleConverter")
public class DoubleConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		try {
			String convertedValue = value.replaceAll("[^0-9,]", "");
			convertedValue = convertedValue.replace(",", ".");
			return new Double(convertedValue);
		}catch (Exception e) {
			return new Double(0);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		return value.toString();
	}

}
