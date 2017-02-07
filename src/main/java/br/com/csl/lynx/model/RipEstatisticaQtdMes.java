package br.com.csl.lynx.model;

import java.io.Serializable;

public class RipEstatisticaQtdMes implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mes;
	private int qtd;
	
	public String getMes() {
		return mes;
	}
	
	public void setMes(String mes) {
		this.mes = mes;
	}
	
	public int getQtd() {
		return qtd;
	}
	
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

}
