package br.org.eldorado.fw.validator;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.inject.Named;

import br.org.eldorado.fw.bean.BeanSupport;

@Named
@RequestScoped
public class SelectManyButtonMB extends BeanSupport{

	public void validate(HtmlInputHidden...entity) {
		System.out.println("hello");
	}
	
}
