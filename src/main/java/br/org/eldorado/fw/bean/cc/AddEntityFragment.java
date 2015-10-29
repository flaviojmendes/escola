package br.org.eldorado.fw.bean.cc;

import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.CrudService;
import br.org.eldorado.fw.service.FinderService;

/**
 * Classe componentizadora, que auxilia nas telas de listagem.
 * 
 *
 */
public abstract class AddEntityFragment<T extends EntitySupport> {

	protected T entity;
	
	public abstract Class<T> getEntityClass();
	public abstract FinderService getFinderService();
	public abstract CrudService getCrudService();
	public abstract String getUrl();
	public abstract String getCallBackUrl();
	
	
	
	public AddEntityFragment(){

	}
	
	public String showFragment() {
		return getUrl();
	}
	
	public String add() {
		getCrudService().saveOrUpdate(entity);
		return getCallBackUrl();
	}
	
	
	public void onLoadPage(){
		try {
			this.entity = getEntityClass().newInstance();
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