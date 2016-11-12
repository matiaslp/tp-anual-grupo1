package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.AgregarAcciones;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
@Entity
public class FuncAgregarAcciones extends Accion {

	public FuncAgregarAcciones() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("ADMIN"));
		nombre = "agregarAcciones";
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
			String filePath) {
		if (validarsesion(user, Token)) {
			return new AgregarAcciones(cantidadReintentos, enviarEmail, filePath, user);
		} else
			return null;
	}

}
