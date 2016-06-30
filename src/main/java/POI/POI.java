package POI;

import Geolocation.GeoLocation;

public abstract class POI {

	String nombre;
	String callePrincipal;
	String calleLateral ;
	int numeracion;
	int piso;
	String departamento;
	String unidad;
	int codigoPostal;
	String localidad;
	String barrio;
	String provincia;
	String pais;
	GeoLocation Ubicacion;
//	TipoPOI tipo;
	int comuna;
	int cercania = 500; //define cuando otro punto es cercano.
	String tipo; //este atributo hay que ver si nos sirve porque
				//las subclases tienen el nombre del tipo, de por si.
	


	
	public boolean estaXMetrosDePOI(double x, POI unPOI){
		
		
		return (distanciaCoordDosPOIs(this,unPOI)*1000 < x);
	}
	
	public static double distanciaCoordDosPOIs(POI unPOI,POI segundoPOI) {  
		double lat1 = segundoPOI.Ubicacion.getLatitudeInDegrees();
		double lng1 = segundoPOI.Ubicacion.getLongitudeInDegrees();
		double lat2 = unPOI.Ubicacion.getLatitudeInDegrees();
		double lng2 = unPOI.Ubicacion.getLongitudeInDegrees();
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kil�metros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
    }  


	
	// Se le pregunta a un POI si es cercano.
	public boolean esCercano(POI poi){
	double distancia = this.Ubicacion.distanceTo(poi.Ubicacion);
	int tcercania = this.getCercania();
	int retval = Double.compare(distancia, tcercania)
	if (retval > 0)
		return false;
	else
		return true;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCallePrincipal() {
		return callePrincipal;
	}

	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}

	public String getCalleLateral() {
		return calleLateral;
	}

	public void setCalleLateral(String calleLateral) {
		this.calleLateral = calleLateral;
	}

	public int getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(int numeracion) {
		this.numeracion = numeracion;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public double getLatitud() {
		return Ubicacion.getLatitudeInDegrees();
	}

	public void setLatitud(double latitud) {
		this.Ubicacion.setDegLat(latitud);
	}

	public double getLongitud() {
		return Ubicacion.getLongitudeInDegrees();
	}

	public void setLongitud(double longitud) {
		this.Ubicacion.setDegLon(longitud);
	}

//	public void setTipo(TipoPOI tipo) {
//		this.tipo = tipo;
//	}

	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

	public int getCercania(){
		return cercania;
	}
	
	public void setCecania(int valor){
		this.cercania=valor;
	}
		
	public POI getPOI() {
		return this;
	}
	

	public GeoLocation getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(GeoLocation ubicacion) {
		Ubicacion = ubicacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setCercania(int cercania) {
		this.cercania = cercania;
	}
	
}
