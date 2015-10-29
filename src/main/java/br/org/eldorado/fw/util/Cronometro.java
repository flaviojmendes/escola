package br.org.eldorado.fw.util;

import java.util.Date;

public class Cronometro {

	private static long inicio;
	
	public static void start(){
		inicio = new Date().getTime();
	}
	
	public static void stop(){
		System.out.println("Cronometro: "+ (new Date().getTime()-inicio));
	}
}
