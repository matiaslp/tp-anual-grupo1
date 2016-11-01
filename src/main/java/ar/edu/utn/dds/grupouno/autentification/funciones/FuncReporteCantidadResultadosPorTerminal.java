package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;

public class FuncReporteCantidadResultadosPorTerminal extends Accion {

	public FuncReporteCantidadResultadosPorTerminal() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombre = "reportecantidadResultadosPorTerminal";
	}

	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(Usuario user, String Token, long terminal) {
		if (validarsesion(user, Token))
			return DB_HistorialBusquedas.getInstance().reporteCantidadResultadosPorTerminal(terminal);
		else
			return null;
	}

}
