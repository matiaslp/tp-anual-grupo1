package ar.edu.utn.dds.grupouno.repositorio;

public class CantBusquedas {
	int _id;
	int cantResultados;
	
	public CantBusquedas(){
		
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getCantResultados() {
		return cantResultados;
	}
	public void setCantResultados(int cantResultados) {
		this.cantResultados = cantResultados;
	}
}