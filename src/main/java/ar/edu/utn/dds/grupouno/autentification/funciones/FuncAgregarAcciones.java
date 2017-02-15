package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.HashSet;

import javax.persistence.Entity;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.procesos.ActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.procesos.AgregarAcciones;
import ar.edu.utn.dds.grupouno.procesos.ProcesoHandler;
import ar.edu.utn.dds.grupouno.procesos.ProcesoListener;
import ar.edu.utn.dds.grupouno.procesos.ResultadoProceso;
@SuppressWarnings("serial")
@Entity
public class FuncAgregarAcciones extends Accion {

	public FuncAgregarAcciones(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "agregarAcciones";
		isProcess = true;
	}

	public FuncAgregarAcciones() {

	}

	// creacion Proceso para agregar a la lista en Proceso Multiple
	public void agregarAcciones(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail,
			String filePath) {
		if (validarsesion(user, Token)) {
			AgregarAcciones proceso = new AgregarAcciones();
			try {
				Scheduler scheduler = ProcesoHandler.ejecutarProceso(user, proceso, filePath, enviarEmail, cantidadReintentos,null);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SchedulerException
					| InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
