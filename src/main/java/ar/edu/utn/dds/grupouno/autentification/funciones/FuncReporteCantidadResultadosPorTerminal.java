package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@Entity
public class FuncReporteCantidadResultadosPorTerminal extends Accion {

	public FuncReporteCantidadResultadosPorTerminal(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "reportecantidadResultadosPorTerminal";
	}

	public FuncReporteCantidadResultadosPorTerminal() {

	}

	public ArrayList<Object[]> obtenerCantidadResultadosPorTerminal(Usuario user, String Token, long terminal) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().resultadosRegistrosHistoricos()
					.reporteCantidadResultadosPorTerminal(terminal);
		else
			return null;
	}

}
