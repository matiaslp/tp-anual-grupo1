package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class FuncReporteCantidadResultadosPorTerminal extends Accion {

	public FuncReporteCantidadResultadosPorTerminal() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("ADMIN"));
		nombre = "reportecantidadResultadosPorTerminal";
	}

	public ArrayList<Object[]> obtenerCantidadResultadosPorTerminal(Usuario user, String Token, long terminal) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().resultadosRegistrosHistoricos().reporteCantidadResultadosPorTerminal(terminal);
		else
			return null;
	}

}
