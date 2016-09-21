package db;

import java.util.HashMap;
import java.util.Map;

public class DB_Sesiones {

	private Map<String, String> diccionarioTokenUser;

	private static DB_Sesiones instance = null;

	public DB_Sesiones() {
		setDiccionarioTokenUser(new HashMap<String, String>());
	}

	public static DB_Sesiones getInstance() {
		if (instance == null) {
			instance = new DB_Sesiones();
		}
		return instance;
	}

	public Map<String, String> getDiccionarioTokenUser() {
		return diccionarioTokenUser;
	}

	public void setDiccionarioTokenUser(Map<String, String> diccionarioTokenUser) {
		this.diccionarioTokenUser = diccionarioTokenUser;
	}

}
