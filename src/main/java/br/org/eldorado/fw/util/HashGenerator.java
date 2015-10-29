package br.org.eldorado.fw.util;

import java.util.Random;

public class HashGenerator {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	
	public static String generate(String prefix){
		StringBuilder sb = new StringBuilder(prefix);
		for( int i = 0; i < 40; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
}
