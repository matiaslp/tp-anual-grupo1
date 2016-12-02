package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

@Entity
public class FuncCambiarEstadoMail extends Accion {

	public FuncCambiarEstadoMail(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "cambiarEstadoMail";
	}

	public FuncCambiarEstadoMail() {

	}

	public boolean cambiarEstadoMail(Usuario user, String Token, boolean estado) {
		if (validarsesion(user, Token)) {
			return user.setMailHabilitado(estado);
		} else {
			return false;
		}
	}
}
