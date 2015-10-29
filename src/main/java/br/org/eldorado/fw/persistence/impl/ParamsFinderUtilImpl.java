package br.org.eldorado.fw.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.PersistenceException;

import br.org.eldorado.fw.persistence.Param;
import br.org.eldorado.fw.persistence.ParamsFinderUtil;
import br.org.eldorado.fw.persistence.entity.EntitySupport;

public class ParamsFinderUtilImpl implements ParamsFinderUtil, Serializable {

	private static final long serialVersionUID = -7410648203596342043L;

	/**
	 * Retorna a lista de parametros seguindo o 
	 * exemplo de uma entidade passada como parametro.
	 * Todos os valores populados nessa entidade serão
	 * considerados e populados como um parametro.
	 * @return
	 */
	public Param[] getParamsByExample(EntitySupport entityExample){
		return getParamsByExample(null, entityExample).toArray(new Param[0]);
		
	}

	private Collection<Param> getParamsByExample(String prefixFieldName, EntitySupport entityExample){
		if (prefixFieldName == null){
			prefixFieldName = "";
		}
		Collection<Param> params = new ArrayList<Param>();
		for (Field field : entityExample.getClass().getDeclaredFields()){
			field.setAccessible(true);
			try {
				Object value = field.get(entityExample);
				if (isValidField(field,value)){
					if (value != null && !"".equals(value)){
						
						String propertyName = prefixFieldName;
						if (!propertyName.equals("")){
							propertyName += ".";
						}
						
						QueryOperator operator = QueryOperator.EQUALS;
						if (field.getType().equals(String.class)){
							operator = QueryOperator.LIKE;
						}
						params.add(new Param(propertyName+field.getName(), value, operator));
					}
					
				//Verifica se é uma pk para os casos que a pk dela nao estiver preenchida, e sim outros atributos
				}else if (value != null && value instanceof EntitySupport){
					String propertyName = prefixFieldName;
					if (!propertyName.equals("")){
						propertyName += ".";
					}
					propertyName+=field.getName();
					params.addAll(getParamsByExample(propertyName, (EntitySupport)value));
				}
			} catch (IllegalArgumentException e) {
				throw new PersistenceException(e);
			} catch (IllegalAccessException e) {
				throw new PersistenceException(e);
			}
		}
		return params;
	}
	
	private boolean isValidField(Field field, Object value) {
		if (value == null){
			return false;
		}
		
		//Se for collection nao é inserido na consulta
		if (isCollection(field)){
			return false;
		}
		
		//Se for uma entidade verifica se a pk esta preenchida
		if (value instanceof EntitySupport){
			
			Object pk = ((EntitySupport)value).getId();
			if (pk == null){
				return false;
			}
		}
		
		if (isEntity(value.getClass()) && !(value instanceof EntitySupport)){
			//TODO log warning
			System.err.println(" Entidade "+value.getClass() + " deve estender a classe EntitySupport para fazer parte da query automatica");
			return false;
		}
		
		return true;
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
		if (field.getType().isAssignableFrom(List.class)){
			return true;
		}
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
