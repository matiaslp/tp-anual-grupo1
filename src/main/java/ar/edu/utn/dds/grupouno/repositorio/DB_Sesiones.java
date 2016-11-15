package ar.edu.utn.dds.grupouno.repositorio;

import java.util.HashMap;
import java.util.Map;

public class DB_Sesiones extends Persistible {

	private Map<String, String> listaSesiones;

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
		return listaSesiones;
	}

	public void setDiccionarioTokenUser(Map<String, String> diccionarioTokenUser) {
		this.listaSesiones = diccionarioTokenUser;
	}

	public void agregarTokenUser(String token, String user) {
		listaSesiones.put(token, user);
	}

	public void removerTokenUser(String token, String user) {
		listaSesiones.remove(token, user);
	}

	public void removerSesiones(String user) {
		while (listaSesiones.values().remove(user))
			;
	}

	public String validarToken(String token) {
		return listaSesiones.get(token);
	}

}
