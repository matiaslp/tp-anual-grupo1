package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;

public class resultadoBusquedaDTO {
	private String nombre;
	private List<NodoServicioDTO> servicios = new ArrayList<NodoServicioDTO>();
	private TiposPOI tipo;
	private Long linea;
	private String barrio;
	private String direccion;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<NodoServicioDTO> getServicios() {
		return servicios;
	}
	public void setServicios(List<NodoServicioDTO> servicios) {
		this.servicios = servicios;
	}
	public void addServicio(NodoServicioDTO servicio){
		servicios.add(servicio);
	}
	public TiposPOI getTipo() {
		return tipo;
	}
	public void setTipo(TiposPOI tipo) {
		this.tipo = tipo;
	}
	public Long getLinea() {
		return linea;
	}
	public void setLinea(Long linea) {
		this.linea = linea;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
}
