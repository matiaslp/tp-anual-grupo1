package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
@Entity
public class FuncCambiarEstadoGenerarLog extends Accion {

	public FuncCambiarEstadoGenerarLog(Rol rol, Rol rol2) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		Roles.add(rol2);
		nombre = "generarLog";
	}
	
	public FuncCambiarEstadoGenerarLog(){
		
	}

	public boolean cambiarEstadoMail(Usuario user, String Token, boolean estado) {
		if (validarsesion(user, Token)){
			return user.setLog(estado);
		}else{
			return false;
		}
	}
}
