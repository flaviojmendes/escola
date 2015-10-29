package br.org.eldorado.fw.util;

import java.text.Normalizer;

public class StringUtil {

	/**
	 * Retorna a String colocando a primeira letar maiuscula
	 * @param s
	 * @return
	 */
	public static String captalize(String s){
		if (s.length() == 0){
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static String removerAcentos(String str) {
		if (str == null){
			return str;
		}
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
	
	public static String camelCase(String str) {
		str = removerAcentos(str);
		str = str.replace(" ", "");
		return str;
	}
	
	public static String charToLowerCase(String texto, Integer ... posicoes){
		StringBuilder result = new StringBuilder();
		for (int i=0;i<texto.length();i++){
			if (ArrayUtil.hasElement(posicoes, i)){
				result.append(Character.toLowerCase(texto.charAt(i)));
			}else{
				result.append(texto.charAt(i));
			}
		}
		return result.toString();
	}
	
}
