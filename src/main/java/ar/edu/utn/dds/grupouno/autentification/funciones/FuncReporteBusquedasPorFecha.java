package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;

public class FuncReporteBusquedasPorFecha extends Accion {

	public FuncReporteBusquedasPorFecha() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "reporteBusquedasPorFecha";
	}

	public Map<String, Long> obtenerBusquedasPorFecha(Usuario user, String Token) {
		if (validarsesion(user, Token))
			return DB_HistorialBusquedas.getInstance().reporteBusquedasPorFecha();
		else
			return null;
	}

}
