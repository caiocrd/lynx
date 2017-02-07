package br.com.csl.lynx.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicoReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Servico servico;
	private Integer qtd;
	
	private BigDecimal total;
	
	public ServicoReport(Servico servico, Integer qtd, BigDecimal total) {
		this.servico = servico;
		this.qtd = qtd;
		this.total = total;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void addQtd(Integer qtd) {
		this.qtd = this.qtd + qtd;
	}
	
	public void addTotal(BigDecimal value) {
		this.total = this.total.add(value);
	}

}
