package poi;

import java.text.SimpleDateFormat;

public class Item_Borrar {
	private String parametro;
	//private DateTime fechaBorrado;
	private SimpleDateFormat fechaBorrado = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	public SimpleDateFormat getFechaBorrado() {
		return fechaBorrado;
	}
	public void setFechaBorrado(SimpleDateFormat fechaBorrado) {
		this.fechaBorrado = fechaBorrado;
	}
	
	
}
