package ar.edu.utn.dds.grupouno.db.poi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.Persistible;


@Entity
@Table(name = "SERVICIOS")
public class NodoServicio  extends Persistible {

	@ManyToMany(mappedBy="servicios")
	private Set<POI> pois = new HashSet<POI>();
//	String nombre;
	@ElementCollection
	@CollectionTable(name="SERVICIOS_DIAS")
    @Column(name="dia")
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
	
	public NodoServicio(){
		
	}
}
