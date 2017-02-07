package br.com.csl.lynx.exception;

public class PosteException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PosteException(Exception e) {
		super(e);
	}

	public PosteException(String msg) {
		super(msg);		
	}
	
	public PosteException(Exception e, String msg) {
		super(msg, e);
	}

	public PosteException() {
		super("Erro de Poste");		
	}

}
