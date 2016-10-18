package ar.edu.utn.dds.grupouno.db.poi;

import java.util.Date;

public class Item_Borrar {
	private String parametro;
	private Date fechaBorrado;
	
	public Item_Borrar(){
		
	}
	
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	public Date getFechaBorrado() {
		return fechaBorrado;
	}
	public void setFechaBorrado(Date fechaBorrado) {
		this.fechaBorrado = fechaBorrado;
	}
	
	
}
