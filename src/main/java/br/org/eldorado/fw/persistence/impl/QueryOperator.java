package br.org.eldorado.fw.persistence.impl;

public enum QueryOperator {

	EQUALS(" = "), LIKE(" LIKE "), IS_NULL (" is null "), IS_NOT_NULL(" is not null ");
	
	private String value;
	
	private QueryOperator(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
