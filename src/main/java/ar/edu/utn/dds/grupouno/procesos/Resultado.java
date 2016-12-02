package ar.edu.utn.dds.grupouno.procesos;

public enum Resultado {
	OK(true), ERROR(false);

	private boolean a;

	Resultado(boolean bool) {
		this.a = bool;
	}

}
