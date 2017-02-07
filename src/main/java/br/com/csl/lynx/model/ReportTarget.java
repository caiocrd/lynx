package br.com.csl.lynx.model;

import java.io.Serializable;

public class ReportTarget implements Serializable {

	private static final long serialVersionUID = 1L;

	private String target;
	private String value;
	
	public ReportTarget(String target, String value) {
		this.target = target;
		this.value = value;
	}

	public String getTarget() {
		return target;
	}

	public String getValue() {
		return value;
	}

}
