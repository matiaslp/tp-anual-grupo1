package ar.edu.utn.dds.grupouno.db;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;
import ar.edu.utn.dds.grupouno.modelo.Persistible;

//@SuppressWarnings("serial")
@Entity
@Table(name = "HISTORICO")
@NamedQueries({
//@NamedQuery(name = "getHistoricobyUserId", query = "SELECT r FROM RegistroHistorico r WHERE r.userID = :ruserid"),
@NamedQuery(name = "getHistoricobyUserId", query = "SELECT r FROM RegistroHistorico r WHERE r.userID = :ruserid"),
@NamedQuery(name = "RegistroHistorico.findAll", query = "SELECT r FROM RegistroHistorico r"),
@NamedQuery(name = "RegistroHistorico.reporteBusquedasPorFecha", query ="SELECT date(r.time) as fecha,count(r.id) as cantidadBusquedas FROM RegistroHistorico r group by date(r.time)"),
@NamedQuery(name = "RegistroHistorico.reporteCantidadResultadosPorTerminal", query = "SELECT cantResultados,busqueda FROM RegistroHistorico r WHERE r.userID = :ruserid"),
@NamedQuery(name = "RegistroHistorico.reporteBusquedaPorUsuario", query = "SELECT r.userID,SUM(r.cantResultados) FROM RegistroHistorico r group by r.userID")})
public class RegistroHistorico extends Persistible {

	private ZonedDateTime time;
	@Column(name = "userid")
	private long userID;
	private String busqueda;
	private long cantResultados;
	private double tiempoDeConsulta;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "HISTORICO_POI", joinColumns = { @JoinColumn(name = "historico_id") }, inverseJoinColumns = {
			@JoinColumn(name = "poi_id") })
	private List<POI> pois = new ArrayList<POI>();

	public DateTime getTime() {

		return MetodosComunes.convertJavatoJoda(this.time);
	}

	public void setTime(DateTime time) {

		this.time = MetodosComunes.convertJodatoJava(time);
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

	public List<POI> getListaDePOIs() {
		return pois;
	}

	public void setListaDePOIs(ArrayList<POI> listaDePOIs) {
		this.pois = listaDePOIs;
	}

	public RegistroHistorico(DateTime time, long userID, String busqueda, long cantResultados, double tiempoDeConsulta,
			ArrayList<POI> listaDePOIs) {
		super();

		this.time = MetodosComunes.convertJodatoJava(time);
		this.userID = userID;
		this.busqueda = busqueda;
		this.cantResultados = cantResultados;
		this.tiempoDeConsulta = tiempoDeConsulta;

		for (POI unPOI : listaDePOIs) {
			agregarPOIaListaDePOIs(unPOI);
		}

		// agregar lista con los pois encontrados en una nueva lista de pois,
		// testiar todo
		// luego hacer la parte de hibernate de esto
	}

	public void agregarPOIaListaDePOIs(POI unPOI) {
		this.pois.add(unPOI);
	}

	public RegistroHistorico() {

	}
}
