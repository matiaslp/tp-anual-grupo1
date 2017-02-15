package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Entity;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.NodoProceso;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.procesos.ProcesoHandler;
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

	public void procesoMultiple(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail, Proceso proceso,
			ArrayList<NodoProceso> listProc, String filePath) {
		if (validarsesion(user, Token)) {
			try {
				Scheduler scheduler = ProcesoHandler.ejecutarProceso(user, proceso, filePath, enviarEmail, cantidadReintentos, (List)listProc);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SchedulerException
					| InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
