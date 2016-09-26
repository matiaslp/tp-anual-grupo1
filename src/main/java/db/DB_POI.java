package db;

import java.util.ArrayList;
import org.joda.time.DateTime;
import poi.POI;

public class DB_POI {

	private static ArrayList<POI> listadoPOI;
	private static DB_POI instance = null;

	public DB_POI() {
		listadoPOI = new ArrayList<POI>();
	}

	public static ArrayList<POI> getListado() {
		return DB_POI.listadoPOI;
	}

	public static DB_POI getInstance() {
		if (instance == null)
			instance = new DB_POI();
		return instance;
	}

	public static boolean eliminarPOI(int d) {

		for (POI poi : listadoPOI) {
			if (Long.compare(poi.getId(), d) == 0) {
				listadoPOI.remove(poi);
				return true;
			}
		}
		return false;
	}

	public static boolean agregarPOI(POI nuevoPOI) {
		try {
			// testear
			nuevoPOI.setId(listadoPOI.size() + 1);
			listadoPOI.add(nuevoPOI);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public POI getPOIbyId(double d) {
		for (POI poi : listadoPOI) {
			if (Long.compare(poi.getId(), (int) d) == 0)
				return poi;
		}
		return null;
	}
	
	//Me falta probarlo
	public static boolean bajaPoi(String[] valoresBusqueda, DateTime fechaBaja) {
		for (POI poi : listadoPOI) {
			//Si el POI coincide con la busqueda
			if (poi.busquedaEstandar(valoresBusqueda)) {
				if(poi.darDeBaja(fechaBaja))
					return true; //se dio de baja
				else
					return false; //ya estaba dado de baja
			}
		}
		return false;
	}

}
