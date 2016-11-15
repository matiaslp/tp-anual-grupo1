package ar.edu.utn.dds.grupouno.abmc.poi;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.PersistibleConNombre;

@Entity
@Table(name = "RUBRO")
public class Rubro extends PersistibleConNombre {
	private int cercania;

	public Rubro(String nombre) {
		this.nombre = nombre;
	}

	public int getCercania() {
		return cercania;
	}

	public void setCercania(int distancia) {
		this.cercania = distancia;
	}

	public Rubro() {

	}
}
