package autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import db.DB_HistorialBusquedas;

public class FuncReporteCantidadResultadosPorTerminal extends Accion {

	public FuncReporteCantidadResultadosPorTerminal() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "reportecantidadResultadosPorTerminal";
	}

	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(Usuario user, String Token, long terminal) {
		if (validarsesion(user, Token))
			return DB_HistorialBusquedas.getInstance().reporteCantidadResultadosPorTerminal(terminal);
		else
			return null;
	}

}
