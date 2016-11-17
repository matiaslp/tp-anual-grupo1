package ar.edu.utn.dds.grupouno.procesos;

import org.quartz.JobListener;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class BajaPOIsListener extends ProcesoListener implements JobListener{

	@Override
	protected void rollback(Usuario usuario) {
		// TODO: este metodo debe evitar el commit en caso de que haya alguna falla.
	}
}
