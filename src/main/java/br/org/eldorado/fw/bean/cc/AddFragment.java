package br.org.eldorado.fw.bean.cc;

import java.io.Serializable;

import javax.inject.Inject;

import br.org.eldorado.fw.jsf.message.FacesMessage;
import br.org.eldorado.fw.jsf.navigation.NavigationIntent;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.CrudService;
import br.org.eldorado.fw.service.FinderService;

/**
 * Classe componentizadora, que auxilia nas telas de listagem.
 * 
 *
 */
public abstract class AddFragment<T extends EntitySupport> implements Serializable {

	protected T entity;
	
	public abstract Class<T> getEntityClass();
	public abstract FinderService getFinderService();
	public abstract CrudService getCrudService();
	public abstract NavigationIntent getUrl();
	public abstract NavigationIntent getCallBackUrl();
	public void beforeAdd() {}
	public void afterAdd() {}
	
	@Inject
	protected FacesMessage facesMessage;
	
	
	
	public AddFragment(){

	}
	
	public NavigationIntent showFragment() {
		return getUrl();
	}
	
	public NavigationIntent getAdd() {
		return add();
	}
	
	public NavigationIntent add() {
		beforeAdd();
		getFinderService().add(entity);
		afterAdd();
		return getCallBackUrl();
	}
	

	public void doSecurity(){}
	
	
	public void onLoadPage(){
		try {
			this.entity = getEntityClass().newInstance();
			doSecurity();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
}