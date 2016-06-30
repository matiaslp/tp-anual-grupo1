package POI;

import Geolocation.GeoLocation;

public class CGP extends POI {

	int cercania = 0;
	
	
	public int getDistancia() {
		return cercania;
	}
	public void setDistancia(int distancia) {
		this.cercania = distancia;
	}

	
	// Se le pregunta a un POI si es cercano.
	boolean esCercano(POI poi){
		
	if (this.getComuna() == poi.getComuna())
		return true;
	else
		return false;
	}
	
	public POI ConstructorCGP(double latitud, double longitud){
		
		POI poi = new CGP();
		poi.Ubicacion = GeoLocation.fromDegrees(latitud, longitud);
		poi.setNombre(nombre);
		
		return poi;
	}
	
}