package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;

public class FuncObtenerInfoPOI extends Accion {
	public FuncObtenerInfoPOI() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "obtenerInfoPOI";
	}

}
