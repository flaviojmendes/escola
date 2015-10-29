package br.org.eldorado.fw.bean.crud;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;


/**
 * Controlador de mensagens bundle para arquivos externos ao contexto do JSF.
 * Dessa maneira conseguimos externalizar o arquivo de mensagens ao servidor, 
 * acabando com a necessidade de executar o deploy novamente.
 * O escopo é de request, com isso temos as mensagens atualizadas em tempo real
 * no final de cada altera��o do arquivo.
 * @author tulio.castro
 *
 */
//@Named("msg")
//@RequestScoped
//@Produces
public class MessageExtBundleJsf extends AbstractMap<String, String> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3783041980427076254L;
	private ResourceBundle resourceBundle;
	
	public MessageExtBundleJsf(){
		start();
	}

	public void start(){
		File file = new File("D:\\Tulio\\Testers\\properties\\i18n");  

		ClassLoader loader=null;
		try {
			URL[] urls = {file.toURI().toURL()};  
			loader = new URLClassLoader(urls); 
			this.resourceBundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale(), loader);
		} catch (MalformedURLException ex) { }
	}
	
	public String get(Object key){
		return resourceBundle.getString((String) key);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return new HashSet<java.util.Map.Entry<String,String>>();
	}

}
