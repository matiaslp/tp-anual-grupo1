package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;

public class FuncCambiarEstadoNotificarBusquedaLarga extends Accion {

	public FuncCambiarEstadoNotificarBusquedaLarga() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "notificarBusquedaLarga";
	}
	
	public boolean CambiarEstadoNotificarBusquedaLarga(Usuario user, String Token, boolean Estado) {
		if (validarsesion(user, Token)){
			user.setNotificacionesActivadas(Estado);
			return true;
		}else{
			return false;
		}
	}
	
}
