package br.org.eldorado.fw.persistence;

public class OrderBy {

	public static enum Type {ASC,DESC};
	
	private String atribute;
	private Type type = Type.ASC;
	
	public OrderBy(String atribute){
		this.atribute = atribute;
	}
	
	public OrderBy(String atribute, Type tipo){
		this.atribute = atribute;
		this.type = tipo;
	}
	
	public Type getType(){
		return type;
	}

	public String getAtribute() {
		return atribute;
	}
	
}
