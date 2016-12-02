package ar.edu.utn.dds.grupouno.repositorio;

import java.util.Date;

public class CantBusquedasDate {
	Date _id;
	int cantResultados;
	
	public CantBusquedasDate(){
		this._id = null;
		this.cantResultados = 0;
	}
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