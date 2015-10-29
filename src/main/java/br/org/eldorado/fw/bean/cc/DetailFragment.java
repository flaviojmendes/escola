package br.org.eldorado.fw.bean.cc;

import java.io.Serializable;

import javax.inject.Inject;

import br.org.eldorado.fw.jsf.message.FacesMessage;
import br.org.eldorado.fw.jsf.navigation.NavigationIntent;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.FinderService;

/**
 * Classe componentizadora, que auxilia nas telas de listagem.
 * @author flaviojmendes
 *
 */
public abstract class DetailFragment<T extends EntitySupport>  implements Serializable{

	
	protected T entity;
	private Long id;
	
	public abstract Class<T> getEntityClass();
	public abstract FinderService getFinderService();
	public abstract NavigationIntent getUrl();
	
	@Inject
	protected FacesMessage facesMessage;
	
	public DetailFragment(){

	}
	
	public void doSecurity(){}
	

	public void onLoadPage(){
		setEntity(getFinderService().find(getEntityClass(), getId()));
		doSecurity();
	}
	
	public NavigationIntent showFragment() {
		return getUrl();
	}
	
	
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}