package ar.edu.utn.dds.grupouno.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "Historial")
public class RegistroHistorico {

	@Id
	@GeneratedValue
	private long id;
	private DateTime time;
	private long userID;
	private String busqueda;
	private long cantResultados;
	private double tiempoDeConsulta;

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

	public RegistroHistorico(long id, DateTime time, long userID, String busqueda, long cantResultados,
			double tiempoDeConsulta) {
		super();
		this.id = id;
		this.time = time;
		this.userID = userID;
		this.busqueda = busqueda;
		this.cantResultados = cantResultados;
		this.tiempoDeConsulta = tiempoDeConsulta;
	}

	public RegistroHistorico() {
		
	}
}
