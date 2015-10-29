package br.org.eldorado.fw.util;

public class ObjectUtilException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2490445430293900870L;

	public ObjectUtilException(Exception e) {
		super(e.getCause());
	}

}
