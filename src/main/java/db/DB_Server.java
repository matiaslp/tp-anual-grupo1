package db;

import java.util.ArrayList;

import poi.POI;

public class DB_Server {

	private static ArrayList<POI> listadoPOI;
	private static DB_Server instance = null;

	public DB_Server() {
		listadoPOI = new ArrayList<POI>();
	}

	public static ArrayList<POI> getListado() {
		return DB_Server.listadoPOI;
	}

	public DB_Server getInstance() {
		if (instance == null)
			instance = new DB_Server();
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

	public static POI getPOIbyId(double d) {
		for (POI poi : listadoPOI) {
			if (Long.compare(poi.getId(), (int) d) == 0)
				return poi;
		}
		return null;
	}

}
