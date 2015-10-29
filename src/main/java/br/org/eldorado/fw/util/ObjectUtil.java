package br.org.eldorado.fw.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectUtil {

	public static Object getFieldValue(Object object, String fieldName){
		try {
			Method metodoGet = getMethodGetByField(object.getClass(), fieldName);
			Object value = metodoGet.invoke(object);
			return value;
		} catch (SecurityException e) {
			throw new ObjectUtilException(e);
		} catch (NoSuchMethodException e) {
			throw new ObjectUtilException(e);
		} catch (IllegalArgumentException e) {
			throw new ObjectUtilException(e);
		} catch (IllegalAccessException e) {
			throw new ObjectUtilException(e);
		} catch (InvocationTargetException e) {
			throw new ObjectUtilException(e);
		}
	}

	public static Object getFieldValue(Object object, Field field){
		try{
			field.setAccessible(true);
			return field.get(object);
		}catch (Exception e) {
			throw new ObjectUtilException(e);
		}
	}

	/**
	 * Retorna o primeiro field encontrado no objeto, com a annotation passada como argumento.
	 * @param object
	 * @param annotation
	 * @return
	 */
	public static <A> Field getFieldByAnnotation(Object object, Class<? extends Annotation> annotation){

		for (Field field : object.getClass().getDeclaredFields()){
			if (field.isAnnotationPresent(annotation)){
				return field;
			}
		}
		return null;
	}

	/**
	 * Retorna o m�todo get do Field passado como parametro.
	 * @param field
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getMethodGetByField(Class<?> classe, Field field) throws SecurityException, NoSuchMethodException{
		return getMethodGetByField(classe, field.getName());
	}
	
	/**
	 * Retorna o m�todo get do Field passado como parametro.
	 * @param field
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getMethodGetByField(Class<?> classe, String fieldname) throws SecurityException, NoSuchMethodException{
		String nome = "get"+StringUtil.captalize(fieldname);
		Method metodo = classe.getDeclaredMethod(nome);
		return metodo;
	}
}
