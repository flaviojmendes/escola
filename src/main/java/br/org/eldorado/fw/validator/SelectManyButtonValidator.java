package br.org.eldorado.fw.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import br.org.eldorado.fw.jsf.message.FacesMessage;

@Named("selectManyButtonValidator")
public class SelectManyButtonValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		Boolean isSelected = Boolean.valueOf((String)value);
		
		String msg = "javax.faces.component.UIInput.REQUIRED";
		if(component.getAttributes().get("msg") != null && !component.getAttributes().get("msg").equals("")) {
			msg = (String) component.getAttributes().get("msg");
		}
		String label = (String) component.getAttributes().get("label");
		
		if(!isSelected) {
			throw new ValidatorException(FacesMessage.getInstance().addError(msg, label));
		}

	}
}


