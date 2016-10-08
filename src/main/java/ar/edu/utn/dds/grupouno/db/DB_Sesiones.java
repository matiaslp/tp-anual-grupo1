package ar.edu.utn.dds.grupouno.db;

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
	
	public void agregarTokenUser(String token, String user){
		diccionarioTokenUser.put(token, user);
	}
	
	public void removerTokenUser(String token, String user) {
		diccionarioTokenUser.remove(token, user);
	}
	
	public void removerSesiones(String user) {
		while (diccionarioTokenUser.values().remove(user));
	}
	
	public String validarToken(String token){
		return diccionarioTokenUser.get(token);
	}

}
