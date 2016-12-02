package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.procesos.ProcesoMultiple;
@Entity
public class FuncMultiple extends Accion {

	public FuncMultiple(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "procesoMultiple";
	}

	public FuncMultiple() {

	}

	public void procesoMultiple(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			ArrayList<Proceso> listProc) {
		if (validarsesion(user, Token)) {
			ProcesoMultiple proceso = new ProcesoMultiple(cantidadReintentos, enviarEmail, listProc, user);
			proceso.execute();
		}
	}

}
