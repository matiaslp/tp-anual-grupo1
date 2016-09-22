package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.ActualizacionLocalesComerciales;

public class FuncActualizacionLocalesComerciales extends Accion {

	public FuncActualizacionLocalesComerciales() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "actualizacionLocalesComerciales";
	}

	public void agregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			boolean disableAccion, String filePath) {
		if (validarsesion(user, Token)) {
			ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales(cantidadReintentos,
					enviarEmail, disableAccion, filePath);
			proceso.execute();
		}
	}

}
