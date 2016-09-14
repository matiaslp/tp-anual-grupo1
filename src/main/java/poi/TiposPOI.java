package poi;

public enum TiposPOI {
	BANCO("banco"), CGP("cgp"), LOCAL_COMERCIAL("local comercial"), PARADA_COLECTIVO("parada colectivo");

	private String nombre;

	TiposPOI(String unNombre) {
		this.nombre = unNombre;
	}

	public String nombre() {
		return this.nombre;
	}

}
