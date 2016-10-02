package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public boolean actualizarPOI(POI poi) {
		if(eliminarPOI(poi.getId()))
			return agregarPOI(poi);
		else
			return false;
	}

	public Map<Long, Boolean> bajaPoi(String[] valoresBusqueda, List<DateTime> fechasBaja) {
		Map<Long, Boolean> resumen = new HashMap<Long, Boolean>();
		for (POI poi : listadoPOI) {
			//Si el POI coincide con la busqueda.
			if (poi.busquedaEstandar(valoresBusqueda)) {
				for(DateTime fecha : fechasBaja){
					if(poi.dadoDeBaja(fecha)){
						resumen.put(poi.getId(), eliminarPOI(poi.getId()));
					}
				}
			}
		}
		
		return resumen;
	}
}
