package br.com.csl.lynx.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import br.com.csl.lynx.domain.DomainObject;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 *          CSL - Soluções em Informática
 * 
 */

@Entity
@BatchSize(size=500)
@Table(name = "ocorrenciaSlp")
public class OcorrenciaSlp implements DomainObject {

	private static final long serialVersionUID = 8659460758689848727L;

	private Long id;
	private String nome;
	private String telefone;
	private String observacao;
	private Calendar data;
	private Slp slp;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@ManyToOne
	public Slp getSlp() {
		return slp;
	}

	public void setSlp(Slp slp) {
		this.slp = slp;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		OcorrenciaSlp other = (OcorrenciaSlp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ocorrencia [id=" + String.valueOf(id) + ", nome=" + nome
				+ ", observacao=" + observacao + ", data=" + data.toString()
				+ "]";
	}
}
