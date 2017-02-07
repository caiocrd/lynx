package br.com.csl.lynx.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.csl.lynx.domain.DomainObject;

@Entity
@Table(name = "log")
public class Log implements DomainObject {
	private static final long serialVersionUID = -150560269370575937L;


	private Long id;
	private String nomeClasse;
	private Long idObjeto;
	private Integer idUsuario;
	private String nomeUsuario;
	private Calendar data;
	private String metodo;

	public Log(){

	}

	public Log(String nomeClasse, Long idObjeto,
			Integer idUsuario, String nomeUsuario, Calendar data, String metodo) {
		super();
		this.nomeClasse = nomeClasse;
		this.idObjeto = idObjeto;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.data = data;
		this.metodo = metodo;
	}



	public String getNomeClasse() {
		return this.nomeClasse;
	}

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdObjeto() {
		return this.idObjeto;
	}

	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Calendar getData() {
		return this.data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getMetodo() {
		return this.metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

}
