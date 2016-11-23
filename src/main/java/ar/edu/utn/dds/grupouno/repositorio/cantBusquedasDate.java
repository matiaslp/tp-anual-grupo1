package ar.edu.utn.dds.grupouno.repositorio;

import java.util.Date;

public class cantBusquedasDate {
	Date _id;
	int cantResultados;
	public Date get_id() {
		return _id;
	}
	public void set_id(Date _id) {
		this._id = _id;
	}
	public int getCantResultados() {
		return cantResultados;
	}
	public void setCantResultados(int cantResultados) {
		this.cantResultados = cantResultados;
	}
}
