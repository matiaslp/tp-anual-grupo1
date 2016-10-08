package ar.edu.utn.dds.grupouno.db;

import java.util.HashMap;
import java.util.Map;

public class DB_ResultadosProcesos {

	private Map<Long, ResultadoProceso> listadoResultados = new HashMap<Long, ResultadoProceso>();

	private static DB_ResultadosProcesos instance = null;

	public DB_ResultadosProcesos() {
		listadoResultados = new HashMap<Long, ResultadoProceso>();
	}

	public static DB_ResultadosProcesos getInstance() {
		if (instance == null)
			instance = new DB_ResultadosProcesos();
		return instance;
	}

	public int cantidadResultados() {
		return listadoResultados.size();
	}

	public ResultadoProceso resultadoProcesoPorId(long id) {
		return listadoResultados.get(id - 1);
	}

	public void agregarResultadoProceso(ResultadoProceso registro) {
		// se genera el id (se debe modificar con hibernate)
		if (registro.getId() == 0)
			registro.setId(listadoResultados.size() + 1);
		String registroStr = Integer.toString(listadoResultados.size());
		listadoResultados.put(Long.parseLong(registroStr), registro);
	}
}
