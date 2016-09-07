package autentification;

import java.util.Map;

import db.DB_HistorialBusquedas;

public class funcBusquedasPorFecha extends Accion {
	
	@Override
	public Map<String,Long>obtenerBusquedasPorFecha(){
		return DB_HistorialBusquedas.getInstance().reporteBusquedasPorFecha();	
	}	

}
