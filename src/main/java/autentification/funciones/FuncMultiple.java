package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.Proceso;
import procesos.ProcesoMultiple;

public class FuncMultiple extends Accion {

	public FuncMultiple() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "procesoMultiple";
	}

	public void procesoMultiple(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			ArrayList<Proceso> listProc) {
		if (validarsesion(user, Token)) {
			ProcesoMultiple proceso = new ProcesoMultiple(cantidadReintentos, enviarEmail, listProc, user);
			proceso.execute();
		}
	}

}
