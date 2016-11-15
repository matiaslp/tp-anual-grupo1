package ar.edu.utn.dds.grupouno.autentification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.Persistible;

@Entity
@Table(name = "Sesion")
public class Sesion extends Persistible {
	String token;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	Usuario usuario;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
