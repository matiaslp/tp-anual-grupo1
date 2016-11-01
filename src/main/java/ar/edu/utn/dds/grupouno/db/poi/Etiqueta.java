package ar.edu.utn.dds.grupouno.db.poi;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.Persistible;

@Entity
@Table(name = "ETIQUETA")
public class Etiqueta  extends Persistible implements IFlyweightEtiqueta {
	@ManyToMany(mappedBy="etiquetas")
	private Set<POI> pois = new HashSet<POI>();
	
	
	//	public String nombre;
//
//	public Etiqueta(String nombre) {
//		this.nombre = nombre;
//
//	}
//
//	public String getNombre() {
//		return this.nombre;
//	}
	Etiqueta(String nom){
		this.setNombre(nom);
	}
	
	Etiqueta(){
		
	}
}
