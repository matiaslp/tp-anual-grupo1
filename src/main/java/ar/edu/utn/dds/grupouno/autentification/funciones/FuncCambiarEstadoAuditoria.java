package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
@Entity
public class FuncCambiarEstadoAuditoria extends Accion {
	
	public FuncCambiarEstadoAuditoria() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("TERMINAL"));
		nombre = "auditoria";
	}
	
	public boolean CambiarEstadoNotificarBusquedaLarga(Usuario user, String Token, boolean Estado) {
		if (validarsesion(user, Token)){
			user.setAuditoriaActivada(Estado);
			return true;
		}else{
			return false;
		}
	}

}
