package ar.edu.utn.dds.grupouno.procesos;

@SuppressWarnings("rawtypes")
public class NodoProceso {

	private Class SIGUIENTE_PROCESO;
	private String SIGUIENTE_PROCESO_FILEPATH;
	private int SIGUIENTE_PROCESO_REINTENTOS;
	private boolean SIGUIENTE_PROCESO_ENVIAREMAIL;

	public Class getSiguienteProceso() {
		return SIGUIENTE_PROCESO;
	}

	public void setSiguienteProceso(Class siguienteProceso) {
		SIGUIENTE_PROCESO = siguienteProceso;
	}

	public String getSiguienteProcesoFilepath() {
		return SIGUIENTE_PROCESO_FILEPATH;
	}

	public void setSiguienteProcesoFilepath(String sIGUIENTE_PROCESO_FILEPATH) {
		SIGUIENTE_PROCESO_FILEPATH = sIGUIENTE_PROCESO_FILEPATH;
	}

	public int getSiguienteProcesoReintentos() {
		return SIGUIENTE_PROCESO_REINTENTOS;
	}

	public void setSiguienteProcesoReintentos(int sIGUIENTE_PROCESO_REINTENTOS) {
		SIGUIENTE_PROCESO_REINTENTOS = sIGUIENTE_PROCESO_REINTENTOS;
	}

	public boolean isSiguienteProcesoEnviarEmail() {
		return SIGUIENTE_PROCESO_ENVIAREMAIL;
	}

	public void setSiguienteProcesoEnviarEmail(boolean sIGUIENTE_PROCESO_ENVIAREMAIL) {
		SIGUIENTE_PROCESO_ENVIAREMAIL = sIGUIENTE_PROCESO_ENVIAREMAIL;
	}

	public NodoProceso(Proceso siguienteProceso, String SiguienteProcesoFilepath, int SiguienteProcesoReintentos,
			boolean SiguienteProcesoEnviarEmail) {

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
}
