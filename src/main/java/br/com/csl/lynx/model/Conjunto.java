package br.com.csl.lynx.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.csl.lynx.domain.DomainObject;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 * CSL - Soluções em Informática
 *
 */

@Entity
@Table(name = "conjunto")
public class Conjunto implements DomainObject {

	private static final long serialVersionUID = 7756038896602199692L;

	private Integer id;
	private String nome;
	private Bairro bairro;
	private List<Rip> rips;
	private List<Poste> postes;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne
	public Bairro getBairro() {
		return bairro;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	@OneToMany
	@JoinColumn(name = "id")
	public List<Rip> getRips() {
		return rips;
	}
	
	public void setRips(List<Rip> rips) {
		this.rips = rips;
	}

	@OneToMany
	@JoinColumn(name = "id")
	public List<Poste> getPostes() {
		return postes;
	}

	public void setPostes(List<Poste> postes) {
		this.postes = postes;
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
		Conjunto other = (Conjunto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conjunto [id=" + String.valueOf(id) + ", nome=" + nome + ", bairro=" + bairro
				+ "]";
	}

}
