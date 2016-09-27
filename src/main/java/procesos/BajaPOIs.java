package procesos;

import org.joda.time.DateTime;

import db.DB_POI;
import poi.POI;
import autentification.Usuario;

public class BajaPOIs extends Proceso {

	DB_POI DB;

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
	}

	public boolean bajaPoi(POI poi, DateTime fecha){
		POI poiPersistido = DB_POI.getInstance().getPOIbyId(poi.getId());
		if(poiPersistido != null && poiPersistido.getFechaBaja().equals(fecha)){
			DB.eliminarPOI(poi.getId());
			return true;
		} else {
			//SE rompio la internet y vuela todo a la chota wiiiiiiiii digo... CHAOS EVERYWHERE >:D
			return false;
		}
	}

}
