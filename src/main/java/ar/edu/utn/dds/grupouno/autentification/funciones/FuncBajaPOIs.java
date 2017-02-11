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
import ar.edu.utn.dds.grupouno.procesos.BajaPOIs;
import ar.edu.utn.dds.grupouno.procesos.ProcesoHandler;
import ar.edu.utn.dds.grupouno.procesos.ProcesoListener;
import ar.edu.utn.dds.grupouno.procesos.ResultadoProceso;
@SuppressWarnings("serial")
@Entity
public class FuncBajaPOIs extends Accion {

	public FuncBajaPOIs(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "bajaPOIs";
		isProcess = true;
	}

	public FuncBajaPOIs() {

	}
	
	public void darDeBajaPOI(Usuario user, String Token, int cantidadReintentos, boolean enviarEmail, 
			String filePath) {
		if (validarsesion(user, Token)) {
			BajaPOIs proceso = new BajaPOIs();
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
