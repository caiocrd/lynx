package br.com.csl.lynx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.csl.lynx.domain.DomainObject;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 *          CSL - Soluções em Informática
 * 
 */

@Entity
@Table(name = "logradouro")
public class Logradouro implements DomainObject {

	private static final long serialVersionUID = -6826733671913928058L;

	private Integer id;
	private String nome;
	private Set<Bairro> bairros;
	private Set<Endereco> enderecos;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "logradouro_bairro", joinColumns = { @JoinColumn(name = "logradouro_id") }, inverseJoinColumns = { @JoinColumn(name = "bairro_id") })
	public Set<Bairro> getBairros() {
		return bairros;
	}

	@Transient
	public List<Bairro> getBairrosList() {
		return new ArrayList<Bairro>(bairros);
	}

	public void setBairros(Set<Bairro> bairros) {
		this.bairros = bairros;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, orphanRemoval=true)
	@JoinColumn(name = "logradouro_id")
	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	@Transient
	public List<Endereco> getEnderecosList() {
		return new ArrayList<Endereco>(enderecos);
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
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
		Logradouro other = (Logradouro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Logradouro [id=" + String.valueOf(id) + ", nome=" + nome + "]";
	}

}
