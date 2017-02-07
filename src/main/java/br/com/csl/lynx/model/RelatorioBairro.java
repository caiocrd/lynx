package br.com.csl.lynx.model;

public class RelatorioBairro {

	private String bairro;
	private int lampadasApagadas;
	private int lampadasQuebrada;
	private int lampadasAcesa;
	private int lampadasPiscando;
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public int getLampadasApagadas() {
		return lampadasApagadas;
	}
	
	public void setLampadasApagadas(int lampadasApagadas) {
		this.lampadasApagadas = lampadasApagadas;
	}
	
	public int getLampadasQuebrada() {
		return lampadasQuebrada;
	}
	
	public void setLampadasQuebrada(int lampadasQuebrada) {
		this.lampadasQuebrada = lampadasQuebrada;
	}
	
	public int getLampadasAcesa() {
		return lampadasAcesa;
	}
	
	public void setLampadasAcesa(int lampadasAcesa) {
		this.lampadasAcesa = lampadasAcesa;
	}
	
	public int getLampadasPiscando() {
		return lampadasPiscando;
	}
	
	public void setLampadasPiscando(int lampadasPiscando) {
		this.lampadasPiscando = lampadasPiscando;
	}

}
