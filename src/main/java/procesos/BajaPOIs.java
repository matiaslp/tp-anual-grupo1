package procesos;

import org.joda.time.DateTime;

import db.DB_POI;
import poi.POI;

public class BajaPOIs extends Proceso {

	DB_POI DB;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, boolean disableAccion) {
		super(cantidadReintentos, enviarEmail, disableAccion);
		this.DB = DB_POI.getInstance();
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
