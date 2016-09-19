package autentification.funciones;

import java.util.ArrayList;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import db.DB_HistorialBusquedas;

public class FuncBusquedaPorUsuario extends Accion {

	public FuncBusquedaPorUsuario(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}
	
	
	@Override
	public Map<Long, Long> obtenerBusquedaPorUsuario() {
		return DB_HistorialBusquedas.getInstance().reporteBusquedaPorUsuario();
	}

}
