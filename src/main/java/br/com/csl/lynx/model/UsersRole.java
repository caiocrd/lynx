package br.com.csl.lynx.model;

import java.io.Serializable;

import javax.persistence.*;

import br.com.csl.lynx.domain.DomainObject;

@IdClass(UsersRolePK.class)
@Entity
@Table(name = "users_roles")
public class UsersRole implements DomainObject {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Role role;

	@Id
	@ManyToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Id
	@ManyToOne
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	@Transient
	public Serializable getId() {
		UsersRolePK id = new UsersRolePK();
		id.setRole(role);
		id.setUsuario(usuario);
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		UsersRole other = (UsersRole) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}