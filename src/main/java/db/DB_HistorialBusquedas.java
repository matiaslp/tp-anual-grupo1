package db;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DB_HistorialBusquedas {

	private Map<Long, RegistroHistorico> listadoRegistros = new HashMap<Long, RegistroHistorico>();

	private static DB_HistorialBusquedas instance = null;

	public DB_HistorialBusquedas() {
		listadoRegistros = new HashMap<Long, RegistroHistorico>();
	}

	public Map<Long, RegistroHistorico> getListado() {
		return instance.listadoRegistros;
	}

	public static DB_HistorialBusquedas getInstance() {
		if (instance == null)
			instance = new DB_HistorialBusquedas();
		return instance;
	}

	public void agregarHistorialBusqueda(RegistroHistorico registro) {
		String registroStr = Integer.toString(listadoRegistros.size());
		listadoRegistros.put(Long.parseLong(registroStr), registro);
	}

	// Reporte de busquedas por fecha y cantidad de todo el sistema
	public Map<String, Long> reporteBusquedasPorFecha() {
		Map<String, Long> resumen = new HashMap<String, Long>();
		String fechaPrevia = "";
		Long cantParcial = 0L;
		DateTimeFormatter fmt = DateTimeFormat.shortDate();
		String fechaActual = "";

		for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
			fechaActual = fmt.print(registro.getValue().getTime());

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
	public Map<Long, Long> reporteCantidadResultadosPorTerminal(long terminal) {

		Map<Long, Long> resumen = new HashMap<Long, Long>();

		for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
			if (Long.compare(terminal, registro.getValue().getUserID()) == 0)
				resumen.put(registro.getValue().getId(), registro.getValue().getCantResultados());
		}
		return resumen;
	}

	public Map<Long, Long> reporteBusquedaPorUsuario() {

		Map<Long, Long> resumen = new HashMap<Long, Long>();
		List<Long> usuarios = new ArrayList<Long>();

		Long sumaParcial = 0L;
		Long userId = 0L;

		// Obetengo la lista de usuarios que hicieron las busquedas
		for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
			if (!usuarios.contains(registro.getValue().getUserID()))
				usuarios.add(registro.getValue().getUserID());
		}

		while (usuarios.size() > 0) {
			// Obtengo el ultimo usuario
			userId = usuarios.get(usuarios.size() - 1);
			// Saco la cantidad de busquedas del usuario
			for (Map.Entry<Long, RegistroHistorico> registro : listadoRegistros.entrySet()) {
				
				if (Long.compare(userId, registro.getValue().getUserID()) == 0)
					sumaParcial += registro.getValue().getCantResultados();
			}
			resumen.put(userId, sumaParcial);
			usuarios.remove(usuarios.size() - 1);
			sumaParcial = 0L;
		}
		return resumen;

	}

}
