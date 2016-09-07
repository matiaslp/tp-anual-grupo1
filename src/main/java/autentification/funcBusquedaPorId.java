package autentification;

import db.DB_HistorialBusquedas;
import db.RegistroHistorico;

public class funcBusquedaPorId extends Accion {
	
	@Override
	public RegistroHistorico obtenerRegistroPorId(long id){
		return DB_HistorialBusquedas.getInstance().registroHistoricoPorId(id);
	}

}
