package br.com.csl.lynx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SiepCostReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private Siep siep;
	private List<QtdServicoSiep> servicos;
	private BigDecimal total;

	public SiepCostReport(Siep siep) {
		this.siep = siep;
	}
	
	public Siep getSiep() {
		return siep;
	}

	public void setSiep(Siep siep) {
		this.siep = siep;
	}

	public List<QtdServicoSiep> getServicos() {
		return servicos;
	}

	public void setServicos(List<QtdServicoSiep> servicos) {
		this.servicos = servicos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
