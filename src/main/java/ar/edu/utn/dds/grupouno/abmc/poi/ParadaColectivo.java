package ar.edu.utn.dds.grupouno.abmc.poi;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.helpers.LevDist;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;

@Entity
@Table(name = "PARADA")
@PrimaryKeyJoinColumn(name = "id")
public class ParadaColectivo extends POI {

	long cercania = 100;
	long linea;

	public long getDistancia() {
		return cercania;
	}

	public void setDistancia(int distancia) {
		this.cercania = distancia;
	}

	public long getLinea() {
		return linea;
	}

	public void setLinea(long linea) {
		this.linea = linea;
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

	@Override
	public boolean busquedaEstandar(String filtros[]) {

		if (super.busquedaEstandar(filtros)) {
			return true;
		}

		for (String filtro : filtros) {
			if (MetodosComunes.isNumeric(filtro)) {
				long valor = Long.parseLong(filtro);
				if (linea == valor)
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean compararPOI(POI poi) {
		if (!super.compararPOI(poi)) {
			return false;
		}

		ParadaColectivo other = (ParadaColectivo) poi;
		if (linea != other.linea)
			return false;
		return true;
	}

	public ParadaColectivo() {

	}

}
