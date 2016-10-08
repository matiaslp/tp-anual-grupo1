package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

public class FuncCambiarEstadoMail extends Accion {

	public FuncCambiarEstadoMail() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "cambiarEstadoMail";
	}

	public boolean cambiarEstadoMail(Usuario user, String Token, boolean estado) {
		if (validarsesion(user, Token)){
			return user.setMailHabilitado(estado);
		}else{
			return false;
		}
	}
}
