package procesos;

import org.joda.time.DateTime;

import db.DB_POI;
import poi.POI;
import autentification.Usuario;

public class BajaPOIs extends Proceso {

	DB_POI dbPOI = DB_POI.getInstance();

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
	}

	public boolean bajaPoi(String valorBusqueda, DateTime fechaBaja){
		String[] valoresBusqueda = {valorBusqueda};
		
		return dbPOI.bajaPoi(valoresBusqueda, fechaBaja);
	}
}
