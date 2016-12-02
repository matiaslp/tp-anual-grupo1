package ar.edu.utn.dds.grupouno.abmc;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;

public class RegistroHistoricoMorphia {

	private Date time;
	private long userID;
	private String busqueda;
	private long cantResultados;
	private double tiempoDeConsulta;
	private List<POI> pois = new ArrayList<POI>();
	private long id;
	
	public Date getTime() {
		return time;
	}
	public void setTime(ZonedDateTime dateTime) {
		
		this.time = Date.from(dateTime.toInstant());
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
	public List<POI> getPois() {
		return pois;
	}
	public void setPois(List<POI> pois) {
		this.pois = pois;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
