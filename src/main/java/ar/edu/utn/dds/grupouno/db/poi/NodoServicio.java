package ar.edu.utn.dds.grupouno.db.poi;

import java.util.ArrayList;

public class NodoServicio {

	String nombre;
	ArrayList<Integer> listaDias = new ArrayList<Integer>();
	int horaInicio;
	int horaFin;

	public void setName(String nombre) {
		this.nombre = nombre;
	}

	public String getName() {
		return this.nombre;
	}

	public void agregarDia(Integer numero) {
		listaDias.add(numero);
	}

	public ArrayList<Integer> getListaDias() {
		return this.listaDias;
	}

	public void setHoras(int inicio, int fin) {
		this.horaInicio = inicio;
		this.horaFin = fin;
	}

	public int getHoraInicio() {
		return this.horaInicio;
	}

	public int getHoraFin() {
		return this.horaFin;
	}
}
