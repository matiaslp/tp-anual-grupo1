package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;

public class FuncMultiple extends Accion {

	public FuncMultiple() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}

}
