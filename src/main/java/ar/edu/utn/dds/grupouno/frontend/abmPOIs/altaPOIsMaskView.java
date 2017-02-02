package ar.edu.utn.dds.grupouno.frontend.abmPOIs;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;
 
@ManagedBean
@ViewScoped
public class altaPOIsMaskView {
	 private String nombre;
	 private String callePrinsipal;
	 private String calleLateral;
	 private String numeracion;
	 private String piso;
	 private String departamento;
	 private String unidad;
	 private String codigoPostal;
	 private String localidad;
	 private String barrio;
	 private String provincia;
	 private String pais;
	 private String latitud;
	 private String longitud;
	 private String comuna;
	 private String tipo;
	 private String servicios;
	 private String etiquetas;
	 private List<String> tipos = new ArrayList<String>();
	 

	 private POI_DTO poiDTO;
	 
	 @SuppressWarnings("unchecked")
	 public altaPOIsMaskView(){
			tipos.add(TiposPOI.BANCO.nombre());
			tipos.add(TiposPOI.CGP.nombre());
			tipos.add(TiposPOI.LOCAL_COMERCIAL.nombre());
			tipos.add(TiposPOI.PARADA_COLECTIVO.nombre());
	 }
	 
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCallePrinsipal() {
		return callePrinsipal;
	}

	public void setCallePrinsipal(String callePrinsipal) {
		this.callePrinsipal = callePrinsipal;
	}

	public String getCalleLateral() {
		return calleLateral;
	}

	public void setCalleLateral(String calleLateral) {
		this.calleLateral = calleLateral;
	}

	public String getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
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

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}
	
	
	public void altaPOI() {

		poiDTO= new POI_DTO();
		
		poiDTO.setNombre(this.getNombre());
		poiDTO.setCallePrincipal(this.getCallePrinsipal());
		poiDTO.setCalleLateral(this.getCalleLateral());
		if (numeracion != "")
			poiDTO.setNumeracion(Integer.parseInt( this.getNumeracion()));
		if (piso != "")
			poiDTO.setPiso(Integer.parseInt(this.getPiso()));
		poiDTO.setDepartamento(this.getDepartamento());
		poiDTO.setUnidad(this.getUnidad());
		if (codigoPostal != "")
			poiDTO.setCodigoPostal(Integer.parseInt(this.getCodigoPostal()));
		poiDTO.setLocalidad(this.getLocalidad());
		poiDTO.setBarrio(this.getBarrio());
		poiDTO.setProvincia((this.getProvincia()));
		poiDTO.setPais(this.getPais());
		if (latitud != "")
			poiDTO.setLatitud(Double.parseDouble(this.getLatitud()));
		if (longitud != "")
			poiDTO.setLongitud(Double.parseDouble(this.getLongitud()));
		if (comuna != "")
			poiDTO.setComuna(Integer.parseInt(this.getComuna()));
		poiDTO.setTipo(TiposPOI.getEnumByString(tipo));

		/*poiDTO.setServicios(serv);
		 private String servicios;
		 private String etiquetas;*/
		
		// particularidaddes de subitipos por agregar
		
		
		
		POI nuevoPOI = poiDTO.converttoPOI();
		Repositorio.getInstance().pois().agregarPOI(nuevoPOI);
		
		
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public List<String> getTipos() {
		return tipos;
	}


	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}	
	
}
