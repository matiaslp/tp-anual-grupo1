package ar.edu.utn.dds.grupouno.quartz;

import org.quartz.Job;

import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@SuppressWarnings("rawtypes")
public abstract class Proceso implements Job {

	private static Class SIGUIENTE_PROCESO;

	private DB_POI dbPOI = Repositorio.getInstance().pois();

	public static Class getSiguienteProceso() {
		return SIGUIENTE_PROCESO;
	}

	public static void setSiguienteProceso(Class siguienteProceso) {
		SIGUIENTE_PROCESO = siguienteProceso;
	}


	public ProcesoListener getProcesoListener()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (ProcesoListener) getClass().getClassLoader().loadClass(getClass().getName() + "Listener").newInstance();
	}

	public DB_POI getDbPOI() {
		return dbPOI;
	}

	public void setDbPOI(DB_POI dbPOI) {
		this.dbPOI = dbPOI;
	}

}
