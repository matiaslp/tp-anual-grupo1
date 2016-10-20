package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

public class FuncCambiarEstadoGenerarLog extends Accion {

	public FuncCambiarEstadoGenerarLog() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "generarLog";
	}

	public boolean cambiarEstadoMail(Usuario user, String Token, boolean estado) {
		if (validarsesion(user, Token)){
			return user.setLog(estado);
		}else{
			return false;
		}
	}
}
