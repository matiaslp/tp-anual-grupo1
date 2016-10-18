package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

import java.util.List;

class NodoServicioDTO {
	String nombre;
	List<HorariosDTO> horarios;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<HorariosDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorariosDTO> horarios) {
		this.horarios = horarios;
	}

}
