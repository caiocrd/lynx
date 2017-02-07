package br.com.csl.lynx.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.csl.lynx.domain.DomainObject;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 * CSL - Soluções em Informática
 *
 */

@Entity
@Table(name = "qtdservico")
public class QtdServico implements DomainObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer qtd;
	private Integer aprovado;
	private Servico servico;
	private Rip rip;
	private BigDecimal cost;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQtd() {
		return qtd;
	}
	
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	
	@ManyToOne
	public Servico getServico() {
		return servico;
	}
	
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	@ManyToOne
	@JoinTable(name = "rip_qtdservico", joinColumns = { @JoinColumn(name = "qtdservicos_id")}, inverseJoinColumns = { @JoinColumn(name = "rip_id")})
	public Rip getRip() {
		return rip;
	}

	public void setRip(Rip rip) {
		this.rip = rip;
	}

	public Integer getAprovado() {
		return aprovado;
	}
	
	public void setAprovado(Integer aprovado) {
		this.aprovado = aprovado;
	}

	@Transient
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "QtdServico [id=" + id + ", qtd=" + qtd + ", aprovado="
				+ aprovado + "]";
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
		QtdServico other = (QtdServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
