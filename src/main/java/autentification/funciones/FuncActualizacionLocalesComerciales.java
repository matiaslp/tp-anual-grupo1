package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;

public class FuncActualizacionLocalesComerciales extends Accion {

	public FuncActualizacionLocalesComerciales(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}
	
}
