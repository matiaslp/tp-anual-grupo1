package ar.edu.utn.dds.grupouno.db.poi;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.PersistibleConNombre;

@Entity
@Table(name = "ETIQUETA")
public class Etiqueta  extends PersistibleConNombre implements IFlyweightEtiqueta {
	@ManyToMany(mappedBy="etiquetas")
	private Set<POI> pois = new HashSet<POI>();
	
	Etiqueta(String nom){
		this.setNombre(nom);
	}
	
	Etiqueta(){
		
	}
}
