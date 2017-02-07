package br.com.csl.lynx.exception;

public class RipException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public RipException(Exception e) {
		super(e);
	}

	public RipException(String msg) {
		super(msg);		
	}
	
	public RipException(Exception e, String msg) {
		super(msg, e);
	}

	public RipException() {
		super("Erro de Rip");		
	}

}
