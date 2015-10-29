package br.org.eldorado.fw.persistence.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public abstract class DAOSuporte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager entityManager;
	
	public void persist(Object arg0){
		entityManager.persist(arg0);
	}
	
	public void clear(){
		entityManager.clear();
	}
	
	public void refresh(Object obj){
		entityManager.refresh(obj);
	}
	
	
	public void flush(){
		entityManager.flush();
	}
	
	public void commit(){
		entityManager.getTransaction().commit();
	}
	
	public void openTransaction() {
		entityManager.getTransaction().begin();
	}
	
	public <E> E merge(E arg0){
		return entityManager.merge(arg0);
	}
	
	public void remove(Object arg0){
		entityManager.remove(arg0);
	}
	
	public <E> E find(Class<E> classe, Object pk){
		return entityManager.find(classe, pk);
	}
	
	public Query createQuery(StringBuilder sql){
		return createQuery(sql.toString());
	}
	
	public Query createQuery(String sql){
		return entityManager.createQuery(sql);
	}
}
