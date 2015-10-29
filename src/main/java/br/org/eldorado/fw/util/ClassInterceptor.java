package br.org.eldorado.fw.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe com métodos interceptadores para serem chamados através de 
 * reflection no framework.
 * @author tulio.castro
 *
 */
public class ClassInterceptor {

	/**
	 * Invoca um método encontrado no objeto da classe, 
	 * que tiver a anotação de método definida como argumento.
	 * É possível passar parametros para invocar o método.
	 * Se for passado parametros e o método não for invocado,
	 * o interceptador tentará achar o mesmo método sem parametros.
	 * @param clazz
	 * @param annotationClass
	 * @param value
	 * @throws Throwable 
	 */
	public static void callMethodByAnnotation(Object clazz, Class<? extends Annotation> annotationClass, Object ... params) throws Throwable{
		for (Method metodo : getMethodsHierarquical(clazz.getClass())){
			if (metodo.isAnnotationPresent(annotationClass)){
				try {
					metodo.invoke(clazz, params);
				} catch (IllegalArgumentException e) {
					if (params.length > 0){
						callMethodByAnnotation(clazz, annotationClass);
						return;
					}
					System.err.println("ClassInterceptorError - ["+metodo.getName()+"()] - O método encontrado está com os argumentos errado.");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.err.println("ClassInterceptorError - ["+metodo.getName()+"()] - O método encontrado não pode ser acessado. Verifique se está publicamente visível");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw e.getTargetException();
				}
			}
		}
	}
	
	/**
	 * Retorna todos os m�todos incluindo os da classe de hierarquia.
	 * Ordenando primeiro pelas classes pai e depois das classes filhas.
	 */
	public static Collection<Method> getMethodsHierarquical(Class<?> clazz){
		Collection<Method> metodos = new LinkedList<Method>();
		metodos.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		if (!clazz.getSuperclass().equals(Object.class)){
			metodos.addAll(getMethodsHierarquical(clazz.getSuperclass()));
			return metodos;
		}
		return metodos;
	}
}
