package br.org.eldorado.fw.service;

import java.util.Collection;

import br.org.eldorado.fw.persistence.OrderBy;
import br.org.eldorado.fw.persistence.Param;
import br.org.eldorado.fw.persistence.entity.EntitySupport;

public interface FinderService {

	/*public <E> E find(Class<E> entityClass, Object id);
	public <E> Collection<E> findAll(Class<E> entityClass);
	public <E> Collection<E> findAll(Class<E> entityClass,OrderBy orderBy);
	public <E> E findByProperty(Class<E> entityClass,String propriedade,Object valor);
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade,Object valor);
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade,Object valor,OrderBy orderBy);
	public <E> Collection<E> findByExample(E exemplo);*/
	
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
	
	public <E> E findByProperty(String sql, Param... params);
	
	public boolean update(Object entity);
	public boolean add(Object entity);
	public boolean remove(Object entity);
}
