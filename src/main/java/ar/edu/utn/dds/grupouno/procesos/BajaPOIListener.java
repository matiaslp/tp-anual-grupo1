package ar.edu.utn.dds.grupouno.procesos;

import org.quartz.JobListener;

import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class BajaPOIListener extends ProcesoListener implements JobListener{

	@Override
	protected void rollback() {
		// TODO: este metodo debe evitar el commit en caso de que haya alguna falla.
	}
}
