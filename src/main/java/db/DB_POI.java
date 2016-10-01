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

	public boolean eliminarPOI(long d) {

		for (POI poi : listadoPOI) {
			if (Long.compare(poi.getId(), d) == 0) {
				listadoPOI.remove(poi);
				return true;
			}
		}
		return false;
	}

	public boolean agregarPOI(POI nuevoPOI) {
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

	public POI getPOIbyNombre(String nombre) {
		for (POI poi : listadoPOI) {
			if (poi.getNombre().equals(nombre))
				return poi;
		}
		return null;
	}

	public void actualizarPOI(POI poi) {
		eliminarPOI(poi.getId());
		agregarPOI(poi);
	}

	//Me falta probarlo
	public boolean bajaPoi(String[] valoresBusqueda, DateTime fechaBaja) {
		for (POI poi : listadoPOI) {
			//Si el POI coincide con la busqueda
			if (poi.busquedaEstandar(valoresBusqueda)) {
				if(poi.dadoDeBaja())
					return true; //se dio de baja
				else
					return false; //ya estaba dado de baja
			}
		}
		return false;
	}

}
