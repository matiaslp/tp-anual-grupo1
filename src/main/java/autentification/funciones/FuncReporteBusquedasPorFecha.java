package autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import db.DB_HistorialBusquedas;

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