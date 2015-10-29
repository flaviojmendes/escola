package br.org.eldorado.fw.persistence.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Id;
import javax.persistence.PersistenceException;

/**
 * Classe suporte para entidades de Id Simples (Com apenas uma PK).
 * @author flaviojmendes
 *
 */
public abstract class EntitySupport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Toda entidade deverá implementar esse método que será a representação da coluna PK da entidade.
	 * Graças a essa convenção conseguimos padronizar, agilizar o sistema e controlar cache de entidades.
	 * @return
	 */
	public abstract Long getId();
	
	@Override
	/**
	 * HashCode Genérico
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			if (getClass() != obj.getClass().getSuperclass()){
				return false;
			}
		}
		EntitySupport other = (EntitySupport) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
	/**
	 * Retorna o valor pk da entidade procurando pelos Fields e pelos metodos que estiverem anotados com a classe @Id
	 * @return
	 */
	public Object getPkValueEntidade(){
		Object pkFieldValue = getPKValueFromFields();
		if (pkFieldValue != null) return pkFieldValue;
		Object pkMethodValue = getPKValueFromMethods();
		if (pkMethodValue != null) return pkMethodValue;
		throw new PersistenceException("A entidade "+getClass().getSimpleName()+" nao possui pk.");
	}
	
	private Object getPKValueFromFields(){
		for (Field field : getClass().getDeclaredFields()){
			if (field.isAnnotationPresent(Id.class)){
				field.setAccessible(true);
				try {
					return (Long) field.get(this);
				} catch (IllegalArgumentException e) {
					throw new PersistenceException("Nao foi possivel recuperar o valor da pk da entidade "+getClass().getSimpleName(),e);
				} catch (IllegalAccessException e) {
					throw new PersistenceException("Nao foi possivel recuperar o valor da pk da entidade "+getClass().getSimpleName(),e);				
				}
			}
		}
		return null;
	}
	
	private Object getPKValueFromMethods()  {
		for (Method m : getClass().getDeclaredMethods()){
			if (m.getAnnotation(Id.class)!=null){
				try {
				return m.invoke(this);
				} catch (IllegalArgumentException e) {
					throw new PersistenceException("Nao foi possivel recuperar o valor da pk da entidade "+getClass().getSimpleName(),e);
				} catch (IllegalAccessException e) {
					throw new PersistenceException("Nao foi possivel recuperar o valor da pk da entidade "+getClass().getSimpleName(),e);				
				} catch (InvocationTargetException e) {
					throw new PersistenceException("Nao foi possivel recuperar o valor da pk da entidade "+getClass().getSimpleName(),e);		
				}
			}
		}
		return null;
	}
}
