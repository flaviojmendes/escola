package br.org.eldorado.fw.util;

import javax.servlet.http.HttpServletRequest;


public class RequestUtil {

	/**
	 * Retorna a url real da aplicação em 
	 * @return
	 */
	public static String getRealContextPath(HttpServletRequest req){
		StringBuilder sb = new StringBuilder();

		String scheme = req.getScheme();
		if (scheme != null && !scheme.equals("")){
			sb.append(scheme).append("://");
		}

		String servername = req.getServerName();
		sb.append(servername);

		int port = req.getServerPort();
		if (port != 0){
			sb.append(":").append(port);
		}

		String contextpath = req.getContextPath();
		if (contextpath != null && !contextpath.equals("")){
			sb.append(contextpath);
		}

		return sb.toString();
	}
}
