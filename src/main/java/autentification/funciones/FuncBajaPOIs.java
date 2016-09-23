package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.BajaPOIs;
import procesos.Proceso;

public class FuncBajaPOIs extends Accion {

	public FuncBajaPOIs() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		Roles.add(Rol.TERMINAL);
		nombreFuncion = "bajaPOIs";
		isProcess = true;
	}

	public void darDeBajaPOI(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			boolean disableAccion) {
		if (validarsesion(user, Token)) {
			BajaPOIs proceso = new BajaPOIs(cantidadReintentos, enviarEmail, disableAccion);
			proceso.execute();
		}
	}

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public Proceso prepDarDeBajaPOI(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			boolean disableAccion) {
		if (validarsesion(user, Token)) {
			return new BajaPOIs(cantidadReintentos, enviarEmail, disableAccion);
		} else
			return null;
	}

}
