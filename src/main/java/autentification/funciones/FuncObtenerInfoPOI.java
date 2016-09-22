package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import db.DB_POI;
import poi.POI;
import procesos.BajaPOIs;

public class FuncObtenerInfoPOI extends Accion {
	public FuncObtenerInfoPOI() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "obtenerInfoPOI";
	}

	public POI obtenerInfoPOI(Usuario user, String Token, 
			long id) {
		if (validarsesion(user, Token))
			return DB_POI.getInstance().getPOIbyId(id);
		else
			return null;
	}
	
}
