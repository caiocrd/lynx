package br.com.csl.lynx.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.csl.lynx.domain.DomainObject;
import br.com.csl.lynx.support.Movement;

/**
 * @author Bruno H. de Castro
 * @version 2.0
 * 
 *          CSL - Soluções em Informática
 * 
 */

@Entity
@Table(name = "movimentacao")
public class MovimentacaoSiep implements DomainObject {

	private static final long serialVersionUID = -6921490124400335989L;

	private Long id;
	private Usuario usuario;
	private Siep siep;
	private Movement movimento;
	private String observacao;
	private Calendar data;
	private Integer pasta;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.EAGER)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	public Siep getSiep() {
		return siep;
	}

	public void setSiep(Siep siep) {
		this.siep = siep;
	}

	@Enumerated(EnumType.STRING)
	public Movement getMovimento() {
		return movimento;
	}

	public void setMovimento(Movement movimento) {
		this.movimento = movimento;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getPasta() {
		return pasta;
	}

	public void setPasta(Integer pasta) {
		this.pasta = pasta;
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
		MovimentacaoSiep other = (MovimentacaoSiep) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movimentacao [id=" + String.valueOf(id) + ", usuario="
				+ usuario + ", movimento=" + movimento + ", observacao="
				+ observacao + ", data=" + data.toString() + ", pasta=" + pasta
				+ "]";
	}

}
