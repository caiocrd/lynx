package br.com.csl.utils.exception;

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String msg) {
		super(msg);		
	}
	
	public ServiceException(Exception e, String msg) {
		super(msg, e);
	}

	public ServiceException() {
		super("Erro de Serviço");		
	}

}
