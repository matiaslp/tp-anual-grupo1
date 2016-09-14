package poi;

import geolocation.GeoLocation;

public class ParadaColectivo extends POI {

	// esta distancia es en referencia a qué?
	int cercania = 100;

	public int getDistancia() {
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
	}

}
