package br.com.csl.utils.exception;

public class DataAccessException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DataAccessException(Exception e) {
		super(e);
	}

	public DataAccessException(String msg) {
		super(msg);		
	}
	
	public DataAccessException(Exception e, String msg) {
		super(msg, e.getCause());
	}

	public DataAccessException() {
		super("Erro de Acesso ao Banco de Dados.");		
	}

}
