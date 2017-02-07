package br.com.csl.lynx.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.csl.lynx.domain.DomainObject;

@IdClass(LogradouroBairroFk.class)
@Entity
@Table(name = "logradouro_bairro")
public class LogradouroBairro implements DomainObject {

	private static final long serialVersionUID = -7182690062373215557L;

	private Logradouro logradouro;
	private Bairro bairro;
	
	@Id
	@ManyToOne
	public Logradouro getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	
	@Id
	@ManyToOne
	public Bairro getBairro() {
		return bairro;
	}
	
	@Override
	@Transient
	public Serializable getId() {
		LogradouroBairroFk id = new LogradouroBairroFk();
		id.setBairro(bairro);
		id.setLogradouro(logradouro);
		return id;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
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
		LogradouroBairro other = (LogradouroBairro) obj;
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