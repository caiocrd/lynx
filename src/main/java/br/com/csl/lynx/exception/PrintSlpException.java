package br.com.csl.lynx.exception;

public class PrintSlpException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PrintSlpException(Exception e) {
		super(e);
	}

	public PrintSlpException(String msg) {
		super(msg);		
	}
	
	public PrintSlpException(Exception e, String msg) {
		super(msg, e);
	}

	public PrintSlpException() {
		super("Erro de SLP");		
	}

}
