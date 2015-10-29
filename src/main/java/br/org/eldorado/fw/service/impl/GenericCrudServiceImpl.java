package br.org.eldorado.fw.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;

import br.org.eldorado.fw.persistence.CrudDAO;
import br.org.eldorado.fw.persistence.impl.GenericCrudDAOImpl;
import br.org.eldorado.fw.persistence.producer.Transaction;
import br.org.eldorado.fw.service.CrudService;
import br.org.eldorado.fw.service.ServiceValidatorException;



@Generic
public class GenericCrudServiceImpl implements CrudService, Serializable{

	
	
	@Produces
	public GenericCrudServiceImpl produces() {
		return new GenericCrudServiceImpl();
	}
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private CrudDAO crudDAO;
	
	public CrudDAO getCrudDAO(){
		
		return this.crudDAO;
	}

	@Transaction
	public boolean update(Object entidade) {
		try{
			//beforeAlterar
			getCrudDAO().update(entidade);
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
			getCrudDAO().add(entidade);
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
			getCrudDAO().remove(entidade);
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


	@Transaction
	public boolean removeAll(Class<?> class1, String param, Object paramValue) {
		crudDAO.removeAll(class1, param, paramValue);
		return true;
	}

	
	@Transaction
	@Override
	public <E> E saveOrUpdate(E entity) {
		E entityRet = crudDAO.saveOrUpdate(entity);
		return entityRet;
	}
	
}
