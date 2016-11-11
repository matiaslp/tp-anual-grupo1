package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class FuncReporteBusquedaPorUsuario extends Accion {

	public FuncReporteBusquedaPorUsuario() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(new Rol("ADMIN"));
		nombre = "reporteBusquedaPorUsuario";
	}

	public ArrayList<Object[]> obtenerBusquedaPorUsuario(Usuario user, String Token) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().resultadosRegistrosHistoricos().reporteBusquedaPorUsuario();
		else
			return null;
	}

}
