package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.modelo.Persistible;


@Entity
@Table(name = "Historial")
public class RegistroHistorico extends Persistible {

	private DateTime time;
	private long userID;
	private String busqueda;
	private long cantResultados;
	private double tiempoDeConsulta;
	private ArrayList<POI> listaDePOIs= new ArrayList<POI>();
	


	

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public long getCantResultados() {
		return cantResultados;
	}

	public void setCantResultados(long cantResultados) {
		this.cantResultados = cantResultados;
	}

	public double getTiempoDeConsulta() {
		return tiempoDeConsulta;
	}

	public void setTiempoDeConsulta(double tiempoDeConsulta) {
		this.tiempoDeConsulta = tiempoDeConsulta;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public ArrayList<POI> getListaDePOIs() {
		return listaDePOIs;
	}

	public void setListaDePOIs(ArrayList<POI> listaDePOIs) {
		this.listaDePOIs = listaDePOIs;
	}

	public RegistroHistorico(long id, DateTime time, long userID, String busqueda, long cantResultados,
			double tiempoDeConsulta,ArrayList<POI> listaDePOIs) {
		super();
		this.id = id;
		this.time = time;
		this.userID = userID;
		this.busqueda = busqueda;
		this.cantResultados = cantResultados;
		this.tiempoDeConsulta = tiempoDeConsulta;
		
		
		
		for (POI unPOI:listaDePOIs){
			agregarPOIaListaDePOIs(unPOI);
		}
		
		//agregar lista con los pois encontrados en una nueva lista de pois, testiar todo
		// luego hacer la parte de hibernate de esto
	}
	
	public void agregarPOIaListaDePOIs(POI unPOI){
		this.listaDePOIs.add(unPOI);
	}
	


	public RegistroHistorico() {
		
	}
}
