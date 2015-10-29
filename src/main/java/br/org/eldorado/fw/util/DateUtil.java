package br.org.eldorado.fw.util;

import java.util.Calendar;
import java.util.TimeZone;

public class DateUtil {

	/**
	 * Retorna o calendar com a data atual no formato Brasileiro.
	 * @return
	 */
	public static Calendar getCalendarInstance(){
//		Locale brasil = new Locale("pt", "BR");
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");  
		Calendar c = Calendar.getInstance(tz);
		return c;
	}
}
