package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;

public class FuncCambiarEstadoAuditoria extends Accion {
	
	public FuncCambiarEstadoAuditoria() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "auditoria";
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
