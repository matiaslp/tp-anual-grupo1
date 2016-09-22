package autentification.funciones;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import procesos.AgregarAcciones;

public class FuncAgregarAcciones extends Accion {

	public FuncAgregarAcciones() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "agregarAcciones";
	}
	
	public void agregarAcciones(Usuario user, String Token,
			int cantidadReintentos, boolean enviarEmail, 
			boolean disableAccion, String filePath) {
		if (validarsesion(user, Token)){
		AgregarAcciones proceso = new AgregarAcciones(
				cantidadReintentos, enviarEmail, disableAccion, filePath);
		proceso.execute();
		}
	}

}
