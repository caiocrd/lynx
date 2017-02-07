package br.com.csl.lynx.model;

import javax.persistence.Transient;

import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.csl.lynx.domain.DomainObject;
import br.com.csl.lynx.model.Bairro;
import br.com.csl.lynx.model.Conjunto;
import br.com.csl.lynx.model.Logradouro;
import br.com.csl.lynx.model.LogradouroBairroFk;
import br.com.csl.lynx.model.Poste;
import br.com.csl.lynx.model.Rip;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 * CSL - Soluções em Informática
 *
 */

@Entity
@Table(name = "endereco")
@AssociationOverrides({
	@AssociationOverride(name = "fk.bairro", 
		joinColumns = @JoinColumn(name = "bairro_id")),
	@AssociationOverride(name = "fk.logradouro", 
		joinColumns = @JoinColumn(name = "logradouro_id")) })
public class Endereco implements DomainObject {

	private static final long serialVersionUID = 4043019137253875591L;

	private Integer id;
	private String cep;
	private LogradouroBairroFk fk = new LogradouroBairroFk();
	private Conjunto conjunto;
	private List<Poste> postes;
	private List<Rip> rips;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = true, unique = true)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Embedded
	public LogradouroBairroFk getFk() {
		return fk;
	}

	public void setFk(LogradouroBairroFk logradouroBairro) {
		this.fk = logradouroBairro;
	}
	
	@PrimaryKeyJoinColumn
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

	@OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	public List<Poste> getPostes() {
		return postes;
	}

	public void setPostes(List<Poste> postes) {
		this.postes = postes;
	}

	@OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	public List<Rip> getRips() {
		return rips;
	}

	public void setRips(List<Rip> rips) {
		this.rips = rips;
	}
	
	@Transient
	public Logradouro getLogradouro() {
		return fk.getLogradouro();
	}

	public void setLogradouro(Logradouro logradouro) {
		fk.setLogradouro(logradouro);
	}
	
	@Transient
	public Bairro getBairro() {
		return fk.getBairro();
	}
	
	public void setBairro(Bairro bairro) {
		fk.setBairro(bairro);
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}



