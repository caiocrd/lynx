package br.com.csl.lynx.exception;

public class PrintSiepException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PrintSiepException(Exception e) {
		super(e);
	}

	public PrintSiepException(String msg) {
		super(msg);		
	}
	
	public PrintSiepException(Exception e, String msg) {
		super(msg, e);
	}

	public PrintSiepException() {
		super("Erro de SIEP");		
	}

}
