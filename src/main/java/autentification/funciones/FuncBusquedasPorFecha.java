package autentification.funciones;

import java.util.Map;

import autentification.Accion;
import db.DB_HistorialBusquedas;

public class FuncBusquedasPorFecha extends Accion {

	@Override
	public Map<String, Long> obtenerBusquedasPorFecha() {
		return DB_HistorialBusquedas.getInstance().reporteBusquedasPorFecha();
	}

}
