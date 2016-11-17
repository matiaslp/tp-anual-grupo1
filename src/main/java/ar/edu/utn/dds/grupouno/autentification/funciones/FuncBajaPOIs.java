package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.BajaPOIs;
import ar.edu.utn.dds.grupouno.quartz.Proceso;
@Entity
public class FuncBajaPOIs extends Accion {

	public FuncBajaPOIs(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "bajaPOIs";
		isProcess = true;
	}

	public FuncBajaPOIs(){
		
	}
	
	public void darDeBajaPOI(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail, 
			String filePath) {
		if (validarsesion(user, Token)) {
			BajaPOIs proceso = new BajaPOIs(cantidadReintentos, enviarEmail, user, filePath);
			proceso.execute();
		}
	}

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public Proceso prepDarDeBajaPOI(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail, 
			String filePath) {
		if (validarsesion(user, Token)) {
			return new BajaPOIs(cantidadReintentos, enviarEmail, user, filePath);
		} else
			return null;
	}

}
