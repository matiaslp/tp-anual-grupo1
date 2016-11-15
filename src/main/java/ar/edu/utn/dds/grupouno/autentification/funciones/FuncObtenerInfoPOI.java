package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@Entity
public class FuncObtenerInfoPOI extends Accion {
	public FuncObtenerInfoPOI(Rol rol, Rol rol2) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		Roles.add(rol2);
		nombre = "obtenerInfoPOI";
	}

	public FuncObtenerInfoPOI() {

	}

	public POI obtenerInfoPOI(Usuario user, String Token, long id) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().pois().getPOIbyId(id);
		else
			return null;
	}

}
