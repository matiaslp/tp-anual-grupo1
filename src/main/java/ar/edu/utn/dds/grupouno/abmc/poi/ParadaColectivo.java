package ar.edu.utn.dds.grupouno.abmc.poi;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PARADA")
@PrimaryKeyJoinColumn(name = "id")
public class ParadaColectivo extends POI {

	long cercania = 100;

	public long getDistancia() {
		return cercania;
	}

	public void setDistancia(int distancia) {
		this.cercania = distancia;
	}

	// funcion para saber si la parada de colectivo está disponible
	public boolean disponible() {
		return true;
	}

	public ParadaColectivo(String nombre, double latitud, double longitud) {
		this.ubicacion = GeoLocation.fromDegrees(latitud, longitud);
		this.setNombre(nombre);
		this.setTipo(TiposPOI.PARADA_COLECTIVO);
	}

	public ParadaColectivo() {

	}

}
