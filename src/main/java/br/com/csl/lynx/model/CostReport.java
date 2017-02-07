package br.com.csl.lynx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class CostReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ReportTarget> target;
	private List<RipCostReport> ripCostReports;
	private List<ServicoReport> servicoReports;
	
	private BigDecimal totalCost;
	
	public CostReport() {
		totalCost = BigDecimal.ZERO;
	}

	public List<ReportTarget> getTarget() {
		return target;
	}

	public void setTarget(List<ReportTarget> target) {
		this.target = target;
	}

	public List<RipCostReport> getRipCostReports() {
		return ripCostReports;
	}

	public void setRipCostReports(List<RipCostReport> ripCostReports) {
		this.ripCostReports = ripCostReports;
	}

	public List<ServicoReport> getServicoReports() {
		return servicoReports;
	}

	public void setServicoReports(List<ServicoReport> servicoReports) {
		this.servicoReports = servicoReports;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	public void addTotal(BigDecimal value) {
		this.totalCost = this.totalCost.add(value);
	}
	
	public String getTotalString() {
		return NumberFormat.getCurrencyInstance().format(totalCost);
	}

}
