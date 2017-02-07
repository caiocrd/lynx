package br.com.csl.lynx.exception;

public class MovementException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public MovementException(Exception e) {
		super(e);
	}

	public MovementException(String msg) {
		super(msg);		
	}
	
	public MovementException(Exception e, String msg) {
		super(msg, e);
	}

	public MovementException() {
		super("Erro de Movimentação");		
	}

}
