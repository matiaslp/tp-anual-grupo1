package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.Proceso;

public class FuncMultiple extends Accion {

	public FuncMultiple() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "procesoMultiple";
	}

	
	public void crearProcesoMultilpe(Usuario user, String Token,
			int cantidadReintentos, boolean enviarEmail, 
			boolean disableAccion, ArrayList<Proceso> listProc) {
		// TODO referenciar al proceso

	}
	
}
