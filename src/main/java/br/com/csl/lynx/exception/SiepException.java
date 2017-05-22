package br.com.csl.lynx.exception;

public class SiepException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SiepException(Exception e) {
		super(e);
	}

	public SiepException(String msg) {
		super(msg);		
	}
	
	public SiepException(Exception e, String msg) {
		super(msg, e);
	}

	public SiepException() {
		super("Erro de Siep");		
	}

}
