package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.AgregarAcciones;
import procesos.Proceso;

public class FuncAgregarAcciones extends Accion {

	public FuncAgregarAcciones() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "agregarAcciones";
		isProcess = true;
	}

	public void agregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			String filePath) {
		if (validarsesion(user, Token)) {
			AgregarAcciones proceso = new AgregarAcciones(cantidadReintentos, enviarEmail, filePath, user);
			proceso.execute();
		}
	}
	
	public void agregarAccionesUndo(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail) {
		if (validarsesion(user, Token)) {
			AgregarAcciones proceso = new AgregarAcciones(cantidadReintentos, enviarEmail, "", user);
			proceso.undo();
		}
	}
	

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public Proceso prepAgregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			String filePath, Usuario unUser) {
		if (validarsesion(user, Token)) {
			return new AgregarAcciones(cantidadReintentos, enviarEmail, filePath, unUser);
		} else
			return null;
	}

}
