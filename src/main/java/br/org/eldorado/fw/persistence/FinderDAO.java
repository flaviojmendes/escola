package br.org.eldorado.fw.persistence;

import java.util.Collection;

import br.org.eldorado.fw.persistence.entity.EntitySupport;

public interface FinderDAO {

	public <E> E find(Class<E> entityClass, Object id);
	public <E> Collection<E> findAll(Class<E> entityClass);
	public <E> Collection<E> findAll(Class<E> entityClass,OrderBy orderBy);
	
	public <E> E findByProperty(Class<E> entityClass,String propriedade,Object valor);
	public <E> E findByProperty(Class<E> entityClass,Param ... params);
	
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade,Object valor);
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade,Object valor,OrderBy orderBy);
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, Param ... params);
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, OrderBy orderBy, Param ... params);
	
	public <E> Collection<E> findAllByExample(EntitySupport entity);
	
	public <E> E findByProperty(String sql, Param... params) ;
	
	public void update(Object entity);
	public void add(Object entity);
	public void remove(Object entity);
	
}
