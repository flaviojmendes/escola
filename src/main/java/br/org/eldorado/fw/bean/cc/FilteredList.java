package br.org.eldorado.fw.bean.cc;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.org.eldorado.fw.bean.Bean;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.FinderService;

/**
 * Classe componentizadora, que auxilia nas telas de listagem.
 * @author flaviojmendes
 *
 */
public abstract class FilteredList<T extends EntitySupport> extends Bean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected T entity;
	protected T entityFilter;
	
	protected Collection<T> entityList;
	
	public void beforeFilter() {}
	public void afterFilter() {}
	
	@Inject
	private FinderService finderService;
	
	
	public abstract Class<T> getEntityClass();
	
	public FilteredList(){
		try {
			this.entityFilter = getEntityClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void onCreate(){
		filter();
	}
	
	public String filter(){
		if (entityFilter != null){
			beforeFilter();
			this.entityList = getFinderService().findAllByExample(entityFilter);
			afterFilter();
		}
		return null;
	}
	
	public FinderService getFinderService(){
		return finderService;
	}
	
	public Collection<T> getEntityList() {
		return entityList;
	}
	public T getEntityFilter() {
		return entityFilter;
	}
	public void setEntityFilter(T entityFilter) {
		this.entityFilter = entityFilter;
	}
	
}
