package br.org.eldorado.fw.persistence.impl;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.persistence.Query;

import br.org.eldorado.fw.persistence.CrudDAO;
import br.org.eldorado.fw.service.impl.GenericCrudServiceImpl;

public class GenericCrudDAOImpl extends DAOSuporte implements CrudDAO, Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void add(Object entity) {
		persist(entity); 
	}

	@Override
	public void remove(Object entity) {
		entity = merge(entity);
		super.remove(entity);
	}
	
	@Override
	public void update(Object entidade){
		merge(entidade); 
	}

	public <E> E saveOrUpdate(E entidade){
		return merge(entidade);
	}
	
	public void removeAll(Class<?> class1, String param,
			Object paramValue) {
		Query q = createQuery("delete from "+class1.getSimpleName()+" where "+param+" = :param");
		q.setParameter("param", paramValue);
		q.executeUpdate();
	}
	
	
}
