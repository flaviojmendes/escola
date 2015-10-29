package br.org.eldorado.escola.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
	private static SimpleDateFormat dateformatHoras = new SimpleDateFormat("dd/MM/yy HH:mm");
	private static SimpleDateFormat dateFormatDia = new SimpleDateFormat("dd/MM/yy");

	public static String recuperarCodigoFonte(String urlstr){

		URL url;
		String result = null;
		try {
			url = new URL(urlstr);
			InputStreamReader inputStream;
			try {
				inputStream = new InputStreamReader(url.openStream());

				BufferedReader reader = new BufferedReader(inputStream);

				result = "";
				String line;

				while ((line = reader.readLine()) != null) {
					result += line;
				}

				reader.close();
			} catch (MalformedURLException ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}


		return result;

	}



	static Date parseTime(String text) throws ParseException {

		char ch;
		int i, j;
		for (i=0, ch = text.charAt(i); 
				ch < 48 || ch > 57; 
				i++, ch = text.charAt(i)){} //percorre string até achar um numero
		for (j=text.length()-1, ch = text.charAt(i); 
				ch < 48 || ch > 57; 
				i--, ch = text.charAt(i)){} //percorre string até achar um numero


		String hour = text.substring(i,j+1);

		return dateformatHoras.parse(dateFormatDia.format(new Date())+" "+hour);
	}

}
