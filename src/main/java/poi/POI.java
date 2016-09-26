package poi;

import java.util.ArrayList;

import org.joda.time.DateTime;

import abmc.POI_DTO;
import geolocation.GeoLocation;
import helpers.LevDist;
import helpers.MetodosComunes;

public abstract class POI {

	long id;
	String nombre;
	String callePrincipal;
	String calleLateral;
	long numeracion;
	long piso;
	String departamento;
	String unidad;
	long codigoPostal;
	String localidad;
	String barrio;
	String provincia;
	String pais;
	GeoLocation ubicacion;
	DateTime fechaBaja;
	
	public DateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(DateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public boolean darDeBaja(DateTime fecha) {
		//Si retorna false significa que ya estaba dado de baja
		if (fechaBaja != null)
			return false;
		fechaBaja = fecha;
		return true;
	}

	public void darAlta() {
		this.fechaBaja = null;
	}
	
	public boolean dadoDeBaja() {
		return (this.fechaBaja != null);
	}

	long comuna;
	// define cuando otro punto es cercano.
	long cercania = 500;
	// este atributo hay que ver si nos sirve porque
	// las subclases tienen el nombre del tipo, de por si.
	TiposPOI tipo;
	public ArrayList<NodoServicio> servicios = new ArrayList<NodoServicio>();
	// pueden ser varias y se crean a travez de
	// FlyweightFactoryEtiqueta.listarEtiquetas(String etiquetas[])
	Etiqueta[] etiquetas;

	public boolean estaXMetrosDePOI(double x, POI unPOI) {
		return (distanciaCoordDosPOIs(this, unPOI) * 1000 < x);
	}

	public static double distanciaEntreDosPuntos(double lat1, double lng1, double lat2, double lng2) {
		// double radioTierra = 3958.75;//en millas
		double radioTierra = 6371;// en kil�metros
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		double distancia = radioTierra * va2;

		return distancia;
	}

	public static double distanciaCoordDosPOIs(POI unPOI, POI segundoPOI) {
		double lat1 = segundoPOI.ubicacion.getLatitudeInDegrees();
		double lng1 = segundoPOI.ubicacion.getLongitudeInDegrees();
		double lat2 = unPOI.ubicacion.getLatitudeInDegrees();
		double lng2 = unPOI.ubicacion.getLongitudeInDegrees();
		return distanciaEntreDosPuntos(lat1, lng1, lat2, lng2);
	}

	// Se le pregunta a un POI si es cercano.
	public boolean esCercano(POI poi) {
		double distancia = this.ubicacion.distanceTo(poi.ubicacion);
		long tcercania = this.getCercania();
		int retval = Double.compare(distancia, tcercania);
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

	public long getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(long numeracion) {
		this.numeracion = numeracion;
	}

	public long getPiso() {
		return piso;
	}

	public void setPiso(long piso) {
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

	public long getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(long codigoPostal) {
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
		return ubicacion.getLatitudeInDegrees();
	}

	public void setLatitud(double latitud) {
		this.ubicacion.setDegLat(latitud);
	}

	public double getLongitud() {
		return ubicacion.getLongitudeInDegrees();
	}

	public void setLongitud(double longitud) {
		this.ubicacion.setDegLon(longitud);
	}

	public long getComuna() {
		return comuna;
	}

	public void setComuna(long comuna) {
		this.comuna = comuna;
	}

	public long getCercania() {
		return cercania;
	}

	public void setCecania(long valor) {
		this.cercania = valor;
	}

	public POI getPOI() {
		return this;
	}

	public GeoLocation getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(GeoLocation ubic) {
		ubicacion = ubic;
	}

	public TiposPOI getTipo() {
		return tipo;
	}

	public void setTipo(TiposPOI tipo) {
		this.tipo = tipo;
	}

	public void setCercania(int cercania) {
		this.cercania = cercania;
	}

	public void setEtiquetas(String nombres[]) {
		this.etiquetas = new Etiqueta[nombres.length];
		for (int i = 0; i < nombres.length; i++) {
			this.etiquetas[i] = FlyweightFactoryEtiqueta.getEtiqueta(nombres[i]);
		}
	}

	public String[] getEtiquetas() {
		String[] nombres = new String[etiquetas.length];
		for (int i = 0; i < etiquetas.length; i++) {
			nombres[i] = etiquetas[i].nombre;
		}
		return nombres;
	}

	public String getEtiqueta(int num) {

		return etiquetas[num].nombre;
	}

	public Boolean buscarEtiqueta(String etiquetaNombre) {
		if (etiquetas != null)
			for (int i = 0; i < etiquetas.length; i++) {
				if (LevDist.calcularDistancia(etiquetaNombre, this.etiquetas[i].getNombre())) {
					return true;
				}
			}

		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean determinarCercaniaPOI(GeoLocation ubicacion) {
		double lat1 = this.ubicacion.getLatitudeInDegrees();
		double lng1 = this.ubicacion.getLongitudeInDegrees();
		double lat2 = ubicacion.getLatitudeInDegrees();
		double lng2 = ubicacion.getLongitudeInDegrees();
		double distancia = distanciaEntreDosPuntos(lat1, lng1, lat2, lng2);

		return this.cercania > distancia;
	}

	public void setDatos(POI_DTO dto) {
		this.setCallePrincipal(dto.getCallePrincipal());
		this.setCalleLateral(dto.getCalleLateral());
		this.setNumeracion(dto.getNumeracion());
		this.setPiso(dto.getPiso());
		this.setDepartamento(dto.getDepartamento());
		this.setUnidad(dto.getUnidad());
		this.setCodigoPostal(dto.getCodigoPostal());
		this.setLocalidad(dto.getLocalidad());
		this.setBarrio(dto.getBarrio());
		this.setProvincia(dto.getProvincia());
		this.setPais(dto.getPais());
		this.setComuna(dto.getComuna());
	}

	public boolean busquedaEstandar(String filtros[]) {

		// List<String> filtros = new ArrayList<String>();
		// if (texto1 != null )
		// filtros.add(texto1);
		// if (texto2 != null )
		// filtros.add(texto2);
		for (String filtro : filtros) {
			if (MetodosComunes.isNumeric(filtro)) {
				long valor = Long.parseLong(filtro);
				if (numeracion == valor)
					return true;
				else if (piso == valor)
					return true;
				else if (codigoPostal == valor)
					return true;
				else if (comuna == valor)
					return true;
			} else if (LevDist.calcularDistancia(filtro, this.nombre))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.callePrincipal))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.calleLateral))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.departamento))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.unidad))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.localidad))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.barrio))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.provincia))
				return true;
			else if (LevDist.calcularDistancia(filtro, this.pais))
				return true;
			else if (LevDist.calcularDistancia(filtro, TiposPOI.BANCO.name()))
				return true;
			else if (LevDist.calcularDistancia(filtro, TiposPOI.CGP.name()))
				return true;
			else if (LevDist.calcularDistancia(filtro, TiposPOI.LOCAL_COMERCIAL.name()))
				return true;
			else if (LevDist.calcularDistancia(filtro, TiposPOI.PARADA_COLECTIVO.name()))
				return true;
			else if (buscarEtiqueta(filtro))
				return true;
		}
		return false;
	}

	public boolean compararServicios(String filtro) {
		for (NodoServicio servicio : servicios) {
			if (LevDist.calcularDistancia(filtro, servicio.nombre)) {
				return true;
			} else if (MetodosComunes.isNumeric(filtro)) {
				long filtroNumerico = Long.parseLong(filtro);
				if (servicio.horaInicio < filtroNumerico && filtroNumerico < servicio.horaFin) {
					return true;
				} else if (servicio.listaDias.contains(filtroNumerico)) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean compararPOI(POI poi) {
		POI other = (POI) poi;
		if (calleLateral == null) {
			if (other.calleLateral != null)
				return false;
		} else if (!calleLateral.equals(other.calleLateral))
			return false;
		if (callePrincipal == null) {
			if (other.callePrincipal != null)
				return false;
		} else if (!callePrincipal.equals(other.callePrincipal))
			return false;
		if (codigoPostal != other.codigoPostal)
			return false;
		if (comuna != other.comuna)
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (localidad == null) {
			if (other.localidad != null)
				return false;
		} else if (!localidad.equals(other.localidad))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeracion != other.numeracion)
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (piso != other.piso)
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		if (tipo != other.tipo)
			return false;
		if (barrio == null) {
			if (other.barrio != null)
				return false;
		} else if (!barrio.equals(other.barrio))
			return false;
		if (unidad == null) {
			if (other.unidad != null)
				return false;
		} else if (!unidad.equals(other.unidad))
			return false;
		return true;
	}

}
