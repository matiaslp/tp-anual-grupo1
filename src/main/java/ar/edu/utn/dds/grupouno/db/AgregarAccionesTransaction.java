package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;

public class AgregarAccionesTransaction {
	
	private long id;
	private long userID;
	private ArrayList<String> listadoCambios; 
	
	
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
		listadoCambios.add(registro);
	}
	
	public ArrayList<String> getListadoCambios() {
		return listadoCambios;
	}
	public AgregarAccionesTransaction(long userID) {
		super();
		this.userID = userID;
		listadoCambios = new ArrayList<String>();
	}
	
	

}
