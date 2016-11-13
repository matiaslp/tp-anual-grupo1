package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.BajaPOIs;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
@Entity
public class FuncBajaPOIs extends Accion {

	public FuncBajaPOIs() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("ADMIN"));
		nombre = "bajaPOIs";
		isProcess = true;
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
