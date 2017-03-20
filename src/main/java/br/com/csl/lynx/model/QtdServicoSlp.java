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
 * @author Caio Cesar
 * @version 2.0
 * 
 * CSL - Soluções em Informática
 *
 */

@Entity
@Table(name = "qtdservicoSlp")
public class QtdServicoSlp implements DomainObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer qtd;
	private Integer aprovado;
	private Servico servico;
	private Slp slp;
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
	@JoinTable(name = "slp_qtdservico", joinColumns = { @JoinColumn(name = "qtdservicos_id")}, inverseJoinColumns = { @JoinColumn(name = "slp_id")})
	public Slp getSlp() {
		return slp;
	}

	public void setSlp(Slp slp) {
		this.slp = slp;
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
		QtdServicoSlp other = (QtdServicoSlp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
