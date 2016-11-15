package ar.edu.utn.dds.grupouno.abmc.poi;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.PersistibleConNombre;

@Entity
@Table(name = "ETIQUETA")
@NamedQueries({
		@NamedQuery(name = "getEtiquetabyNombre", query = "SELECT e FROM Etiqueta e WHERE e.nombre LIKE :enombre"),
		@NamedQuery(name = "Etiqueta.findAll", query = "SELECT e FROM Etiqueta e") })
public class Etiqueta extends PersistibleConNombre implements IFlyweightEtiqueta {
	@ManyToMany(mappedBy = "etiquetas")
	private Set<POI> pois = new HashSet<POI>();

	Etiqueta(String nom) {
		this.setNombre(nom);
	}

	Etiqueta() {

	}
}
