package br.com.csl.lynx.model;

import java.math.BigDecimal;

public class RelatorioGeral {
	
	
	private String zona;
	private BigDecimal abertas;
	private BigDecimal aExecutar;
	private BigDecimal avaliar;
	private BigDecimal adequada;
	private BigDecimal aguardandoEstorno;
	private BigDecimal estornada;
	private BigDecimal feedbackNegativo;
	private BigDecimal aguardandoFinalizacao;
	private BigDecimal finalizada;
	private BigDecimal finalizadaDentroPrazo;
	private BigDecimal cancelada;
	
	public RelatorioGeral() {
		abertas = new BigDecimal(0);
		aExecutar = new BigDecimal(0);
		avaliar = new BigDecimal(0);
		adequada = new BigDecimal(0);
		aguardandoEstorno = new BigDecimal(0);
		estornada = new BigDecimal(0);
		feedbackNegativo = new BigDecimal(0);
		aguardandoFinalizacao = new BigDecimal(0);
		finalizada = new BigDecimal(0);
		finalizadaDentroPrazo = new BigDecimal(0);
		cancelada = new BigDecimal(0);
	}
	
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public BigDecimal getAbertas() {
		return abertas;
	}
	public void setAbertas(BigDecimal abertas) {
		this.abertas = abertas;
	}
	public BigDecimal getaExecutar() {
		return aExecutar;
	}
	public void setaExecutar(BigDecimal aExecutar) {
		this.aExecutar = aExecutar;
	}
	public BigDecimal getAvaliar() {
		return avaliar;
	}
	public void setAvaliar(BigDecimal avaliar) {
		this.avaliar = avaliar;
	}
	public BigDecimal getAdequada() {
		return adequada;
	}
	public void setAdequada(BigDecimal adequada) {
		this.adequada = adequada;
	}
	
	public BigDecimal getAguardandoEstorno() {
		return aguardandoEstorno;
	}
	public void setAguardandoEstorno(BigDecimal aguardandoEstorno) {
		this.aguardandoEstorno = aguardandoEstorno;
	}
	public BigDecimal getEstornada() {
		return estornada;
	}
	public void setEstornada(BigDecimal estornada) {
		this.estornada = estornada;
	}
	public BigDecimal getFeedbackNegativo() {
		return feedbackNegativo;
	}
	public void setFeedbackNegativo(BigDecimal feedbackNegativo) {
		this.feedbackNegativo = feedbackNegativo;
	}
	public BigDecimal getAguardandoFinalizacao() {
		return aguardandoFinalizacao;
	}
	public void setAguardandoFinalizacao(BigDecimal aguardandoFinalizacao) {
		this.aguardandoFinalizacao = aguardandoFinalizacao;
	}
	public BigDecimal getFinalizada() {
		return finalizada;
	}
	public void setFinalizada(BigDecimal finalizada) {
		this.finalizada = finalizada;
	}
	
	public BigDecimal getFinalizadaDentroPrazo() {
		return finalizadaDentroPrazo;
	}

	public void setFinalizadaDentroPrazo(BigDecimal finalizadaDentroPrazo) {
		this.finalizadaDentroPrazo = finalizadaDentroPrazo;
	}

	public BigDecimal getCancelada() {
		return cancelada;
	}
	public void setCancelada(BigDecimal cancelada) {
		this.cancelada = cancelada;
	}
	
	
	
}
