package autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import db.DB_HistorialBusquedas;

public class FuncBusquedasPorFecha extends Accion {

	public FuncBusquedasPorFecha(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}
	
	@Override
	public Map<String, Long> obtenerBusquedasPorFecha() {
		return DB_HistorialBusquedas.getInstance().reporteBusquedasPorFecha();
	}

}
