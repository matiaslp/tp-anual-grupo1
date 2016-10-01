package db;

import java.util.HashMap;
import java.util.Map;

import autentification.Usuario;

public class AgregarAccionesTransaction {
	
	private long id;
	private long userID;
	private Map<Long, String> listadoCambios; 
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	
	public void agregarCambios(String registro) {
		listadoCambios.put(Long.parseLong(registro), registro);
	}
	
	public Map<Long, String> getListadoCambios() {
		return listadoCambios;
	}
	public AgregarAccionesTransaction(long userID) {
		super();
		this.userID = userID;
		listadoCambios = new HashMap<Long,String>();
	}
	
	

}
