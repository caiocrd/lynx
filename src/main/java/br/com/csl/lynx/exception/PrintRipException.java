package br.com.csl.lynx.exception;

public class PrintRipException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PrintRipException(Exception e) {
		super(e);
	}

	public PrintRipException(String msg) {
		super(msg);		
	}
	
	public PrintRipException(Exception e, String msg) {
		super(msg, e);
	}

	public PrintRipException() {
		super("Erro de Rip");		
	}

}
