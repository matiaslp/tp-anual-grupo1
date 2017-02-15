package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

import java.util.List;

public class NodoServicioDTO {
	String nombre;
	List<HorariosDTO> horarios;
	private String bandaHoraria;

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

	public String getBandaHoraria() {
		return bandaHoraria;
	}

	public void setBandaHoraria(String bandaHoraria) {
		this.bandaHoraria = bandaHoraria;
	}

}
