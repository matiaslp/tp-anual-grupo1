package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.ActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.procesos.Proceso;

public class FuncActualizacionLocalesComerciales extends Accion {

	public FuncActualizacionLocalesComerciales() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "actualizacionLocalesComerciales";
		isProcess = true;
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
