package br.org.eldorado.fw.jsf;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named(value = "phoneConverter")
public class PhoneConverter implements Converter, Serializable{

	private static final long serialVersionUID = 8720332352460614280L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		try {
			return new Long(value.replaceAll("[^0-9]", ""));
		}catch (Exception e) {
			return new Long(0);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if(value != null) {
			try{
				String convertedPhone = value.toString();
				convertedPhone = "(" + convertedPhone.substring(0,2) + ") " +  convertedPhone.substring(2,6) + "-" + convertedPhone.substring(6,convertedPhone.length());
				return convertedPhone;
			} catch (Exception e) {
				return value.toString();
			}
		}
		
		return "";
	}

}
