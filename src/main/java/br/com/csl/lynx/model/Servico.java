package br.com.csl.lynx.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "servico")
public class Servico implements DomainObject {

	private static final long serialVersionUID = -4035663314275624L;

	private Integer id;	
	private String nome;
	private String descricao;
	private BigDecimal valor;
	
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
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Servico [id=" + String.valueOf(id) + ", nome=" + nome + ", descricao="
				+ descricao + ", valor=" + valor + "]";
	}

}
