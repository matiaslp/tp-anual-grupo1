package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;

public class BajaPOIs extends Accion {

	public BajaPOIs(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}
	
	
}
