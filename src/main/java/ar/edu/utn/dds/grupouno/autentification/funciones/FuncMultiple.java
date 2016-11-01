package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.procesos.ProcesoMultiple;

public class FuncMultiple extends Accion {

	public FuncMultiple() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombre = "procesoMultiple";
	}

	public void procesoMultiple(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			ArrayList<Proceso> listProc) {
		if (validarsesion(user, Token)) {
			ProcesoMultiple proceso = new ProcesoMultiple(cantidadReintentos, enviarEmail, listProc, user);
			proceso.execute();
		}
	}

}
