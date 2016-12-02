package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

@Entity
public class FuncCambiarEstadoAuditoria extends Accion {

	public FuncCambiarEstadoAuditoria(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "auditoria";
	}

	public FuncCambiarEstadoAuditoria() {

	}

	public boolean CambiarEstadoNotificarBusquedaLarga(Usuario user, String Token, boolean Estado) {
		if (validarsesion(user, Token)) {
			user.setAuditoriaActivada(Estado);
			return true;
		} else {
			return false;
		}
	}

}
