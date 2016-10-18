package ar.edu.utn.dds.grupouno.db.poi;

public class Rubro {
	private String nombre;
	private int cercania;

	public Rubro(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getCercania() {
		return cercania;
	}

	public void setCercania(int distancia) {
		this.cercania = distancia;
	}

}
