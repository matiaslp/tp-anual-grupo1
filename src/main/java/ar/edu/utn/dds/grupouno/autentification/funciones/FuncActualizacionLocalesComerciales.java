package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.ActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.procesos.Proceso;

@Entity
public class FuncActualizacionLocalesComerciales extends Accion {

	public FuncActualizacionLocalesComerciales(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "actualizacionLocalesComerciales";
		isProcess = true;
	}

	public FuncActualizacionLocalesComerciales() {

	}

	public void actualizarLocales(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			String filePath) {
		if (validarsesion(user, Token)) {
			ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales(cantidadReintentos,
					enviarEmail, filePath, user);
			proceso.execute();
		}
	}

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public Proceso prepAgregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			String filePath) {
		if (validarsesion(user, Token)) {
			return new ActualizacionLocalesComerciales(cantidadReintentos, enviarEmail, filePath, user);
		} else
			return null;
	}

}
