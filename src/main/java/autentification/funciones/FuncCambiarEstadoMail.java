package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;

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
