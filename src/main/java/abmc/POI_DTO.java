package abmc;

import java.util.ArrayList;

import org.joda.time.DateTime;

import geolocation.GeoLocation;
import poi.Banco;
import poi.CGP;
import poi.Etiqueta;
import poi.LocalComercial;
import poi.NodoServicio;
import poi.POI;
import poi.ParadaColectivo;
import poi.Rubro;
import poi.TiposPOI;

public class POI_DTO {
	double id;
	String nombre;
	String callePrincipal;
	String calleLateral;
	int numeracion;
	int piso;
	String departamento;
	String unidad;
	int codigoPostal;
	String localidad;
	String barrio;
	String provincia;
	String pais;
	GeoLocation ubicacion;
	DateTime fechaBaja;
	int comuna;
	TiposPOI tipo;
	double latitud;
	double longitud;
	Rubro rubro;
	ArrayList<NodoServicio> servicios = new ArrayList<NodoServicio>();
	Etiqueta[] etiquetas;
	String telefono;
	String director;
	String zonas;
	String gerente;
	String sucursal;
	ArrayList<Integer> dias = new ArrayList<Integer>();
	ArrayList<Integer> horas = new ArrayList<Integer>();

	public POI converttoPOI() {
		POI nuevoPOI = null;

		if (this.getTipo().equals(TiposPOI.CGP)) {
			nuevoPOI = new CGP(this.getNombre(), this.getLatitud(), this.getLongitud());
		} else if (this.getTipo().equals(TiposPOI.LOCAL_COMERCIAL)) {
			nuevoPOI = new LocalComercial(this.getNombre(), this.getLatitud(), this.getLongitud(), this.getRubro());
		} else if (this.getTipo().equals(TiposPOI.BANCO)) {
			nuevoPOI = new Banco(this.getNombre(), this.getLatitud(), this.getLongitud());
		} else if (this.getTipo().equals(TiposPOI.PARADA_COLECTIVO)) {
			nuevoPOI = new ParadaColectivo(this.getNombre(), this.getLatitud(), this.getLongitud());
		}

		nuevoPOI.setCallePrincipal(callePrincipal);
		nuevoPOI.setCalleLateral(calleLateral);
		nuevoPOI.setNumeracion(numeracion);
		nuevoPOI.setPiso(piso);
		nuevoPOI.setDepartamento(departamento);
		nuevoPOI.setUnidad(unidad);
		nuevoPOI.setCodigoPostal(codigoPostal);
		nuevoPOI.setLocalidad(localidad);
		nuevoPOI.setBarrio(barrio);
		nuevoPOI.setProvincia(provincia);
		nuevoPOI.setPais(pais);
		nuevoPOI.setComuna(comuna);
		nuevoPOI.setTipo(tipo);
		nuevoPOI.setFechaBaja(fechaBaja);
		nuevoPOI.setEsLocal(false);
		return nuevoPOI;

	}

	public double getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public GeoLocation getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(GeoLocation ubic) {
		ubicacion = ubic;
	}

	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

	public TiposPOI getTipo() {
		return tipo;
	}

	public void setTipo(TiposPOI tipo) {
		this.tipo = tipo;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public Etiqueta[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Etiqueta[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getZonas() {
		return zonas;
	}

	public void setZonas(String zonas) {
		this.zonas = zonas;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public void setId(double id) {
		this.id = id;
	}

	public void addServicio(NodoServicio servicio) {
		this.servicios.add(servicio);
	}

	public ArrayList<NodoServicio> getServicios() {
		return servicios;
	}

	public void setServicios(ArrayList<NodoServicio> serv) {
		servicios = serv;
	}

	public ArrayList<Integer> getDias() {
		return dias;
	}

	public void setDias(ArrayList<Integer> dias) {
		this.dias = dias;
	}

	public ArrayList<Integer> getHoras() {
		return horas;
	}

	public void setHoras(ArrayList<Integer> horas) {
		this.horas = horas;
	}

	public void darBaja(DateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public void darAlta() {
		this.fechaBaja = null;
	}
	
	public DateTime getFechaBaja() {
		return fechaBaja;
	}
	
}
