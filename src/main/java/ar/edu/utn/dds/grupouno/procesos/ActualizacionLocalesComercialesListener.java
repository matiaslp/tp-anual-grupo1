package ar.edu.utn.dds.grupouno.procesos;

import org.quartz.JobListener;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class ActualizacionLocalesComercialesListener extends ProcesoListener implements JobListener{

	@Override
	protected void rollback(Usuario usuario) {
		//TODO: No hacer el commit de la sesion para que no se persistan los cambios
	}

}
