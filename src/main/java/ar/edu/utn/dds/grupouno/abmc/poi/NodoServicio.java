package ar.edu.utn.dds.grupouno.abmc.poi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.frontend.abmPOIs.Dias;
import ar.edu.utn.dds.grupouno.repositorio.PersistibleConNombre;

@Entity
@Table(name = "SERVICIOS")
public class NodoServicio extends PersistibleConNombre {

	@ManyToMany(mappedBy = "servicios")
	private Set<POI> pois = new HashSet<POI>();
	@ElementCollection
	@CollectionTable(name = "SERVICIOS_DIAS")
	@Column(name = "dia")
	List<Integer> listaDias = new ArrayList<Integer>();
	int horaInicio;
	int horaFin;

	public void setName(String nombre) {
		this.nombre = nombre;
	}

	public String getName() {
		return this.nombre;
	}

	public void agregarDia(Integer numero) {
		listaDias.add(numero);
	}

	public List<Integer> getListaDias() {
		return this.listaDias;
	}

	public void setHoras(int inicio, int fin) {
		this.horaInicio = inicio;
		this.horaFin = fin;
	}

	public int getHoraInicio() {
		return this.horaInicio;
	}

	public int getHoraFin() {
		return this.horaFin;
	}

	public NodoServicio() {

	}

	public Set<POI> getPois() {
		return pois;
	}

	public void setPois(Set<POI> pois) {
		this.pois = pois;
	}

	public void setListaDias(List<Integer> listaDias) {
		this.listaDias = listaDias;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}
	
	public boolean isDomingo(){
		return this.listaDias.contains(Dias.DOMINGO.getValue());
	}
	public boolean isLunes(){
		return this.listaDias.contains(Dias.LUNES.getValue());
	}
	public boolean isMartes(){
		return this.listaDias.contains(Dias.MARTES.getValue());
	}
	public boolean isMiercoles(){
		return this.listaDias.contains(Dias.MIERCOLES.getValue());
	}
	public boolean isJueves(){
		return this.listaDias.contains(Dias.JUEVES.getValue());
	}
	public boolean isViernes(){
		return this.listaDias.contains(Dias.VIERNES.getValue());
	}
	public boolean isSabado(){
		return this.listaDias.contains(Dias.SABADO.getValue());
	}
	
	
}
