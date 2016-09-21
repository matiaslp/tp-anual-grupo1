package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;

public class FuncBajaPOIs extends Accion {

	public FuncBajaPOIs() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		Roles.add(Rol.TERMINAL);
	}

}
