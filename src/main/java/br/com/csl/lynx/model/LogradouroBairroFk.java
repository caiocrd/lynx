package br.com.csl.lynx.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class LogradouroBairroFk implements Serializable {

	private static final long serialVersionUID = -7182690062373215557L;

	private Logradouro logradouro;
	private Bairro bairro;
	
	@ManyToOne
	public Logradouro getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	
	@ManyToOne
	public Bairro getBairro() {
		return bairro;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result	+ ((logradouro == null) ? 0 : logradouro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogradouroBairroFk other = (LogradouroBairroFk) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		return true;
	}
	
	
}
