package br.org.eldorado.fw.persistence;

import java.io.Serializable;

import br.org.eldorado.fw.persistence.impl.QueryOperator;

/**
 * Parametros dinamicos para automatizar as querys e facilitar o manuseio
 * @author flaviojmendes
 *
 */
public class Param implements Serializable{

	private static final long serialVersionUID = 6475266799552086146L;
	private String property;
	private Object value;
	private QueryOperator operator = QueryOperator.EQUALS;

	public Param(String property, Object value){
		this.property = property;
		if (value instanceof QueryOperator){
			this.operator = (QueryOperator)value;
		}else{
			this.value = value;
		}
	}

	public Param(String property, Object value, QueryOperator operator){
		this.property = property;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * Nome do campo
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Nome do campo
	 * @return
	 */
	public String getPropertyWhere() {
		String key = property.replace(".", "");
		return key;
	}


	/**
	 * Valor do campo
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	public QueryOperator getOperator() {
		return operator;
	}


}
