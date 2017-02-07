package br.com.csl.lynx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RipCostReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Rip rip;
	private List<QtdServico> servicos;
	private BigDecimal total;

	public RipCostReport(Rip rip) {
		this.rip = rip;
	}
	
	public Rip getRip() {
		return rip;
	}

	public void setRip(Rip rip) {
		this.rip = rip;
	}

	public List<QtdServico> getServicos() {
		return servicos;
	}

	public void setServicos(List<QtdServico> servicos) {
		this.servicos = servicos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
