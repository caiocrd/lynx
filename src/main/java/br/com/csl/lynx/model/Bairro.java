package br.com.csl.lynx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.csl.lynx.domain.DomainObject;
import br.com.csl.lynx.support.Zona;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 *          CSL - Soluções em Informática
 * 
 */

@Entity
@Table(name = "bairro")
public class Bairro implements DomainObject {

	private static final long serialVersionUID = 1445244477018808368L;

	private Integer id;
	private String nome;
	private Zona zona;
	private Set<Logradouro> logradouros;
	private Set<Conjunto> conjuntos;
	private Set<Endereco> enderecos;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true, length = 255)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	@ManyToMany(mappedBy = "bairros", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Logradouro> getLogradouros() {
		return logradouros;
	}

	@Transient
	public List<Logradouro> getLogradourosList() {
		return new ArrayList<Logradouro>(logradouros);
	}

	public void setLogradouros(Set<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}

	@JoinColumn(name = "bairro_id")
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Set<Conjunto> getConjuntos() {
		return conjuntos;
	}

	@Transient
	public List<Conjunto> getConjuntosList() {
		return new ArrayList<Conjunto>(conjuntos);
	}

	public void setConjuntos(Set<Conjunto> conjuntos) {
		this.conjuntos = conjuntos;
	}

	@JoinColumn(name = "bairro_id")
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
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
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bairro [id=" + String.valueOf(id) + ", nome=" + nome
				+ ", zona=" + zona + "]";
	}

}
