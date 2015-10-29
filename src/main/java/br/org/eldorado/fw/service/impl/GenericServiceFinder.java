package br.org.eldorado.fw.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;

import br.org.eldorado.fw.persistence.FinderDAO;
import br.org.eldorado.fw.persistence.OrderBy;
import br.org.eldorado.fw.persistence.Param;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.persistence.producer.Transaction;
import br.org.eldorado.fw.service.FinderService;
import br.org.eldorado.fw.service.ServiceValidatorException;

public class GenericServiceFinder implements FinderService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FinderDAO finderDAO;
	
	public <E> E find(Class<E> entityClass, Object id){
		return finderDAO.find(entityClass, id);
	}

	public <E> E findByProperty(Class<E> entityClass,String propriedade,Object valor){
		return finderDAO.findByProperty(entityClass, propriedade, valor);
	}

	public <E> Collection<E> findAll(Class<E> entityClass) {
		return finderDAO.findAll(entityClass);
	}

	public <E> Collection<E> findAll(Class<E> entityClass, OrderBy orderBy) {
		return finderDAO.findAll(entityClass, orderBy);
	}

	public <E> Collection<E> findAllByProperty(Class<E> entityClass,
			String propriedade, Object valor) {
		return finderDAO.findAllByProperty(entityClass, propriedade, valor);
	}

	public <E> Collection<E> findAllByProperty(Class<E> entityClass,
			String propriedade, Object valor, OrderBy orderBy) {
		return finderDAO.findAllByProperty(entityClass, propriedade, valor, orderBy);
	}

	@Override
	public <E> E findByProperty(Class<E> entityClass, Param... params) {
		return finderDAO.findByProperty(entityClass, params);
	}

	@Override
	public <E> Collection<E> findAllByProperty(Class<E> entityClass,
			Param... params) {
		return finderDAO.findAllByProperty(entityClass, params);
	}

	@Override
	public <E> Collection<E> findAllByProperty(Class<E> entityClass,
			OrderBy orderBy, Param... params) {
		return finderDAO.findAllByProperty(entityClass, orderBy, params);
	}

	@Override
	public <E> Collection<E> findAllByExample(EntitySupport entity) {
		return finderDAO.findAllByExample(entity);
	}
	
	public <E> E findByProperty(String sql, Param... params) {
		return finderDAO.findByProperty(sql,params);
	}

	@Transaction
	public boolean update(Object entidade) {
		try{
			//beforeAlterar
			finderDAO.update(entidade);
			//afterAlterar
			return true;
		}catch (ServiceValidatorException e) {
			return false;
		}
	}
	
	@Transaction
	public boolean add(Object entidade) {
		try{
			//BeforeIncluir
			finderDAO.add(entidade);
			//afterincluir
			return true;
		}catch (ServiceValidatorException e) {
			return false;
		}
	}

	@Transaction
	public boolean remove(Object entidade){
		try{
			//Before excluir
			if (hasDependencia(entidade)){
				return false;
			}
			finderDAO.remove(entidade);
			//After excluir
			return true;
		}catch (PersistenceException e) {
			throw new ServiceValidatorException(e); 
		}
	}

	protected boolean hasDependencia(Object entidade) {
		for (Method m : entidade.getClass().getDeclaredMethods()){
			if (m.getAnnotation(OneToMany.class)!=null){
				try {
					List<?> lista = (List<?>) m.invoke(entidade);
					if (lista != null && lista.size()>0){
						return true;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
}
