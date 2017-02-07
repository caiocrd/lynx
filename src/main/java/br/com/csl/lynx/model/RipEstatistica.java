package br.com.csl.lynx.model;

import java.io.Serializable;

public class RipEstatistica implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String status;
	private int qtd[];
	
	public RipEstatistica(){
		this.qtd = new int[12];
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int[] getQtd() {
		return qtd;
	}

	public void setQtd(int[] qtd) {
		this.qtd = qtd;
	}
	
	
}
