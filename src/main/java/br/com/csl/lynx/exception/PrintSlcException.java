package br.com.csl.lynx.exception;

public class PrintSlcException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PrintSlcException(Exception e) {
		super(e);
	}

	public PrintSlcException(String msg) {
		super(msg);		
	}
	
	public PrintSlcException(Exception e, String msg) {
		super(msg, e);
	}

	public PrintSlcException() {
		super("Erro de SLC");		
	}

}
