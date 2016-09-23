package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.ActualizacionLocalesComerciales;
import procesos.Proceso;

public class FuncActualizacionLocalesComerciales extends Accion {

	public FuncActualizacionLocalesComerciales() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "actualizacionLocalesComerciales";
		isProcess = true;
	}

	public void agregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			boolean disableAccion, String filePath) {
		if (validarsesion(user, Token)) {
			ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales(cantidadReintentos,
					enviarEmail, disableAccion, filePath);
			proceso.execute();
		}
	}

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public Proceso prepAgregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			boolean disableAccion, String filePath) {
		if (validarsesion(user, Token)) {
			return new ActualizacionLocalesComerciales(cantidadReintentos, enviarEmail, disableAccion, filePath);
		} else
			return null;
	}

}
