package br.com.csl.lynx.exception;

public class PrintSvpaException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PrintSvpaException(Exception e) {
		super(e);
	}

	public PrintSvpaException(String msg) {
		super(msg);		
	}
	
	public PrintSvpaException(Exception e, String msg) {
		super(msg, e);
	}

	public PrintSvpaException() {
		super("Erro de SVPA");		
	}

}
