package br.com.csl.lynx.model;

import org.primefaces.model.chart.PieChartModel;

public class Dados{
	private String titulo;
	private PieChartModel grafico;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public PieChartModel getGrafico() {
		return grafico;
	}
	public void setGrafico(PieChartModel grafico) {
		this.grafico = grafico;
	}
	
	
}
