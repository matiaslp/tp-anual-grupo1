package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class FuncObtenerInfoPOI extends Accion {
	public FuncObtenerInfoPOI() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("ADMIN"));
		Roles.add(new Rol("TERMINAL"));
		nombre = "obtenerInfoPOI";
	}

	public POI obtenerInfoPOI(Usuario user, String Token, long id) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().pois().getPOIbyId(id);
		else
			return null;
	}

}
