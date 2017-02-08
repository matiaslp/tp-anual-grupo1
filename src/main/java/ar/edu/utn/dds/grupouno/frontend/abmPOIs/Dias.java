package ar.edu.utn.dds.grupouno.frontend.abmPOIs;

public enum Dias {
	DOMINGO(0), LUNES(1), MARTES(2), MIERCOLES(3), JUEVES(4), VIERNES(5), SABADO(6);
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private Dias(int value) {
		this.value = value;
	}
	
	
	
	
}
