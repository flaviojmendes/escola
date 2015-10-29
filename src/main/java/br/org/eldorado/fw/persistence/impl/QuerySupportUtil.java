package br.org.eldorado.fw.persistence.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import br.org.eldorado.fw.util.ArrayUtil;

public class QuerySupportUtil {
	
	public String buildJpql(Object entity, Map<String,Object> mapaFields){
		StringBuilder jpql = new StringBuilder("from ").append(entity.getClass().getSimpleName());
		if (!mapaFields.isEmpty()){
			jpql.append(" where ");
			jpql.append(getJpqlWhereClause(entity, mapaFields));
		}
		return jpql.toString();
	}
	
	public String getJpqlWhereClause(Object entity){
		Map<String,Object> mapaFields = mapValuableFields(entity);
		return getJpqlWhereClause(entity, mapaFields);
	}
	
	/**
	 * Através do mapa de Fields passado como parametro, esse método monta a clausula de JPQL 
	 * usada no where.
	 * @param entity
	 * @param mapaFields
	 * @return
	 */
	public String getJpqlWhereClause(Object entity, Map<String,Object> mapaFields){
		StringBuilder jpql = new StringBuilder();
		for (String key : mapaFields.keySet()){
			if (mapaFields.get(key) instanceof String){
				jpql.append(" ").append(key).append(" LIKE :").append(key);
			}else{
				jpql.append(" ").append(key).append("= :").append(key);
			}
			if (!ArrayUtil.isLastElement(key, mapaFields.keySet())){
				jpql.append(" AND ");
			}
		}
		return jpql.toString();
	}
	
	/**
	 * Mapeia os campos que podem ser usados para montar o sql, 
	 * inclusive a clausula WHERE etc.
	 * @param entity
	 * @return
	 */
	public Map<String,Object> mapValuableFields(Object entity){
		Map<String,Object> mapaFields = new HashMap<String,Object>();

		for (Field field : entity.getClass().getDeclaredFields()){
			field.setAccessible(true);
			try {
				Object value = field.get(entity);
				if (isValidField(field,value)){
					if (value != null && !"".equals(value)){
						mapaFields.put(field.getName(), value);
					}
				}
			} catch (IllegalArgumentException e) {
				throw new PersistenceException(e);
			} catch (IllegalAccessException e) {
				throw new PersistenceException(e);
			}
		}
		return mapaFields;
	}
	
	private boolean isValidField(Field field, Object value) {
		//Se for collection nao é inserido na consulta
		if (isCollection(field)){
			return false;
		}
		//Se for uma entidade verifica se a pk esta preenchida
		if (isEntity(field.getType())){
			if (value == null){
				return false;
			}
			Integer pk = getId(value);
			if (pk == null){
				return false;
			}
		}
		
		return true;
	}
	
	private Integer getId(Object entity) {
		if (entity instanceof HibernateProxy) {
			return getIdFromHibernateProxy(entity);
	    }else{
	    	return getPkFromObject(entity);
	    }
	}


	private Integer getIdFromHibernateProxy(Object entity){
	  LazyInitializer lazyInitializer = ((HibernateProxy) entity).getHibernateLazyInitializer();
        if (!lazyInitializer.isUninitialized()) {
            return (Integer) lazyInitializer.getIdentifier();
        }
        return null;
	}
	private Integer getPkFromObject(Object value) {
		for (Method metodo : value.getClass().getDeclaredMethods()){
			if (metodo.getAnnotation(Id.class)!=null){
				try {
					Object pk = metodo.invoke(value);
					return (Integer)pk;
				} catch (IllegalArgumentException e) {
					throw new PersistenceException(e);
				} catch (IllegalAccessException e) {
					throw new PersistenceException(e);
				} catch (InvocationTargetException e) {
					throw new PersistenceException(e);
				}
				
			}
		}
		return null;
	}

	/**
	 * Retorna true se o field for um:
	 * 	- Array
	 *  - Collection
	 *  - Set
	 * @param field
	 * @return
	 */
	private boolean isCollection(Field field){
		if (field.getType().equals(Collection.class)){
			return true;
		}
		if (field.getType().equals(Set.class)){
			return true;
		}
		return false;
	}

	private boolean isEntity(Class<?> clazz){
		if (clazz.getAnnotation(Entity.class)!=null){
			return true;
		}
		return false;
	}
}
