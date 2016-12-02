package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

@Entity
public class FuncCambiarEstadoNotificarBusquedaLarga extends Accion {

	public FuncCambiarEstadoNotificarBusquedaLarga(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "notificarBusquedaLarga";
	}

	public FuncCambiarEstadoNotificarBusquedaLarga() {

	}

	public boolean CambiarEstadoNotificarBusquedaLarga(Usuario user, String Token, boolean Estado) {
		if (validarsesion(user, Token)) {
			user.setNotificacionesActivadas(Estado);
			return true;
		} else {
			return false;
		}
	}

}
