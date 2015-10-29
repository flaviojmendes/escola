package br.org.eldorado.fw.jsf.navigation;

import java.util.HashMap;
import java.util.Map;

public class NavigationIntent {

	/**
	 * URL do xhtml da página de navegação.
	 */
	private String xhtmlPage;
	
	/**
	 * Caso true, retornamos a string com a mesma url, isso faz com que o jsf reinstancie o escopo, chamando novamente os metodos de ViewAction. Caso false, retornamos null, e o escopo não é refeito
	 */
	private boolean reloadScope = true;
	
	/**
	 * Define o método de redirecionamento do jsf: redirect ou forward. Caso true, redireciona por redirect (default).
	 */
	private boolean redirect = true;
	
	/**
	 * Parametros passados na url por GET.
	 */
	Map<String,String> params = new HashMap<String, String>();
	
	
	
	public NavigationIntent(String xhtmlPage) {
		super();
		this.xhtmlPage = xhtmlPage;
	}
	

	public NavigationIntent setXhtmlPage(String xhtmlPage) {
		this.xhtmlPage = xhtmlPage;
		return this;
	}
	
	public NavigationIntent(boolean reloadScope, String xhtmlPage, boolean redirect) {
		super();
		this.reloadScope = reloadScope;
		this.xhtmlPage = xhtmlPage;
		this.redirect = redirect;
	}
	
	public NavigationIntent addParam(String name, Object value){
		this.params.put(name, value.toString());
		return this;
	}
	
	public NavigationIntent setReloadScope(boolean reloadScope) {
		this.reloadScope = reloadScope;
		return this;
	}

	@Override
	public String toString() {
		if (reloadScope == false || 
				xhtmlPage == null || 
				xhtmlPage.equals("")){
			return null;
		}
		
		StringBuilder returnUrl= new StringBuilder(xhtmlPage);
		setDefaultUrlParams();
		if (!params.isEmpty()){
			returnUrl.append("?");
			returnUrl.append(buildStringParams());
		}
		
		return returnUrl.toString();
	}

	public NavigationIntent putParam(String name, Object value){
		this.params.put(name, value.toString());
		return this;
	}
	
	private void setDefaultUrlParams(){
		
		if (redirect){
			params.put("faces-redirect", "true");
			params.put("includeViewParams", "true");
		}
	}
	
	private String buildStringParams() {
		StringBuilder paramsBuilder = new StringBuilder();
		
		for (String key : params.keySet()){
		
			paramsBuilder
			.append(key)
			.append("=")
			.append(params.get(key))
			.append("&");
		}
		return paramsBuilder.toString();
	}

	public boolean isReloadScope() {
		return reloadScope;
	}
	public String getXhtmlPage() {
		return xhtmlPage;
	}
}
