package autentification.funciones;

import java.util.Map;

import autentification.Accion;
import db.DB_HistorialBusquedas;

public class funcCantidadResultadosPorTerminal extends Accion {

	@Override
	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(long terminal) {
		return DB_HistorialBusquedas.getInstance().reporteCantidadResultadosPorTerminal(terminal);
	}

}
