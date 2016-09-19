package autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import db.DB_HistorialBusquedas;

public class FuncCantidadResultadosPorTerminal extends Accion {

	public FuncCantidadResultadosPorTerminal(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}
	
	@Override
	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(long terminal) {
		return DB_HistorialBusquedas.getInstance().reporteCantidadResultadosPorTerminal(terminal);
	}

}
