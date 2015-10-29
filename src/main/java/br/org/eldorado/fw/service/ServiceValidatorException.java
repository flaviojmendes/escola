package br.org.eldorado.fw.service;


/**
 * Classe de exce��o para erros de valida��o feitas na camada de servi�o.
 * @author tulio.castro
 *
 */
public class ServiceValidatorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7942887063440949022L;

	public ServiceValidatorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceValidatorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceValidatorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceValidatorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
