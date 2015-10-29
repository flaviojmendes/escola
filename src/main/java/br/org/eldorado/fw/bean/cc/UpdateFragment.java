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
 * @author flaviojmendes
 *
 */
public abstract class UpdateFragment<T extends EntitySupport> implements Serializable {


	protected T entity;
	private Long id;
	
	public abstract Class<T> getEntityClass();
	public abstract FinderService getFinderService();
	public abstract CrudService getCrudService();
	public abstract NavigationIntent getUrl();
	public abstract NavigationIntent getCallBackUrl();
	public void beforeUpdate() {};
	public void afterUpdate() {};
	
	//@Inject 
	//private FacesMessage facesMessage;
	
	public UpdateFragment(){

	}
	
	public void doSecurity(){}
	
	public NavigationIntent showFragment() {
		return getUrl();
	}
	
	
	public NavigationIntent update() {
		beforeUpdate();
		getFinderService().update(entity);
		afterUpdate();
		//facesMessage.addInfo("alterado.sucesso");
		return getCallBackUrl();
	}
	
	
	public void onLoadPage(){
		entity = getFinderService().find(getEntityClass(), getId());
		doSecurity();
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