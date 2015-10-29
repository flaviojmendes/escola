package br.org.eldorado.fw.util;

import java.util.Collection;

public class ArrayUtil {
	
	public static boolean isLastElement(Object element, Collection<?> array){
		return isLastElement(element, array.toArray());
	}
	
	public static boolean isLastElement(Object element, Object[] array){
		if (element.equals(array[array.length-1])){
			return true;
		}
		return false;
	}
	
	public static boolean hasElement(Object[] array, Object element){
		for (Object ob : array){
			if (ob.equals(element)){
				return true;
			}
		}
		return false;
	}
	
}
