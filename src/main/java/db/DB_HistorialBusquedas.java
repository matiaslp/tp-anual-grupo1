package db;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DB_HistorialBusquedas {

	private static Map<Long, RegistroHistorico> listadoRegistros = new HashMap<Long, RegistroHistorico>();

	private static DB_HistorialBusquedas instance = null;

	public DB_HistorialBusquedas() {
		listadoRegistros = new HashMap<Long, RegistroHistorico>();
	}

//	public Map<Long, RegistroHistorico> getListado() {
//		return instance.listadoRegistros;
//	}

	public static DB_HistorialBusquedas getInstance() {
		if (instance == null)
			instance = new DB_HistorialBusquedas();
		return instance;
	}

	public void agregarHistorialBusqueda(RegistroHistorico registro) {
		// se genera el id (se debe modificar con hibernate)
		if (registro.getId() == 0)
			registro.setId(listadoRegistros.size() + 1);
		String registroStr = Integer.toString(listadoRegistros.size());
		listadoRegistros.put(Long.parseLong(registroStr), registro);
	}

	// Reporte de busquedas por fecha y cantidad de todo el sistema
	public static Map<String, Long> reporteBusquedasPorFecha(String criterioConsulta) {
		Map<String, Long> resumen = new HashMap<String, Long>();
		String fechaPrevia = "";
		Long cantParcial = 0L;

		for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
			DateTimeFormatter fmt = DateTimeFormat.shortDate();
			String fechaActual = fmt.print(registro.getValue().getTime());

			if (fechaPrevia.equals(fechaActual)) {
				cantParcial += registro.getValue().getCantResultados();
			} else {
				resumen.put(fechaPrevia, cantParcial);
				fechaPrevia = fechaActual;
				cantParcial = registro.getValue().getCantResultados();
			}

		}

		return resumen;
	}
	
	// Reporte de busquedas parciales por terminal
	public static Map<Long, Long> reporteCantidadResultadosPorTerminal(long terminal){
		
		Map<Long,Long> resumen = new HashMap<Long,Long>();
		
		for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
			if (Long.compare(terminal,registro.getValue().getUserID()) == 0)
				resumen.put(registro.getValue().getId(), registro.getValue().getCantResultados());
		}
		return resumen;
	}

}
