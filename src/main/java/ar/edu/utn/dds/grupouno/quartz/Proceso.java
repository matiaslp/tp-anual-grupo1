package ar.edu.utn.dds.grupouno.quartz;

import org.quartz.Job;

import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@SuppressWarnings("rawtypes")
public abstract class Proceso implements Job {

	private static Class SIGUIENTE_PROCESO;
	private static String SIGUIENTE_PROCESO_FILEPATH;
	private static int SIGUIENTE_PROCESO_REINTENTOS;
	private static boolean SIGUIENTE_PROCESO_ENVIAREMAIL;

	private DB_POI dbPOI = Repositorio.getInstance().pois();

	public static Class getSiguienteProceso() {
		return SIGUIENTE_PROCESO;
	}

	public static void setSiguienteProceso(Class siguienteProceso) {
		SIGUIENTE_PROCESO = siguienteProceso;
	}

	public static String getSiguienteProcesoFilepath() {
		return SIGUIENTE_PROCESO_FILEPATH;
	}

	public static void setSiguienteProcesoFilepath(String sIGUIENTE_PROCESO_FILEPATH) {
		SIGUIENTE_PROCESO_FILEPATH = sIGUIENTE_PROCESO_FILEPATH;
	}

	public static int getSiguienteProcesoReintentos() {
		return SIGUIENTE_PROCESO_REINTENTOS;
	}

	public static void setSiguienteProcesoReintentos(int sIGUIENTE_PROCESO_REINTENTOS) {
		SIGUIENTE_PROCESO_REINTENTOS = sIGUIENTE_PROCESO_REINTENTOS;
	}

	public static boolean isSiguienteProcesoEnviarEmail() {
		return SIGUIENTE_PROCESO_ENVIAREMAIL;
	}

	public static void setSiguienteProcesoEnviarEmail(boolean sIGUIENTE_PROCESO_ENVIAREMAIL) {
		SIGUIENTE_PROCESO_ENVIAREMAIL = sIGUIENTE_PROCESO_ENVIAREMAIL;
	}

	public static void addSiguienteProceso(Proceso siguienteProceso, String SiguienteProcesoFilepath,
			int SiguienteProcesoReintentos, boolean SiguienteProcesoEnviarEmail) {

		// Obtiene la clase del proceso
		String nombreProcesoActual = siguienteProceso.getClass().getName().replace("Listener", "");
		Class claseSiguienteProceso;
		try {
			claseSiguienteProceso = siguienteProceso.getClass().getClassLoader().loadClass(nombreProcesoActual);
			SIGUIENTE_PROCESO = claseSiguienteProceso;
			SIGUIENTE_PROCESO_FILEPATH = SiguienteProcesoFilepath;
			SIGUIENTE_PROCESO_REINTENTOS = SiguienteProcesoReintentos;
			SIGUIENTE_PROCESO_ENVIAREMAIL = SiguienteProcesoEnviarEmail;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
