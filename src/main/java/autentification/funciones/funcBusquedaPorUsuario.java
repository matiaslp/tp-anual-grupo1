package autentification.funciones;

import java.util.Map;

import autentification.Accion;
import db.DB_HistorialBusquedas;

public class funcBusquedaPorUsuario extends Accion {

	@Override
	public Map<Long, Long> obtenerBusquedaPorUsuario() {
		return DB_HistorialBusquedas.getInstance().reporteBusquedaPorUsuario();
	}

}
