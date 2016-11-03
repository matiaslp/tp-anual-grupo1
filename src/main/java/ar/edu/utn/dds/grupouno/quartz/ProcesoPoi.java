package ar.edu.utn.dds.grupouno.quartz;

import org.quartz.Job;

public abstract class ProcesoPoi implements Job {

	private static Class SIGUIENTE_PROCESO;

	public static Class getSiguienteProceso() {
		return SIGUIENTE_PROCESO;
	}
	
	public static void setSiguienteProceso(Class siguienteProceso) {
		SIGUIENTE_PROCESO = siguienteProceso;
	}


	public ProcesoListener getProcesoListener() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (ProcesoListener) getClass().getClassLoader().loadClass(getClass().getName()+"Listener").newInstance();
	}

}