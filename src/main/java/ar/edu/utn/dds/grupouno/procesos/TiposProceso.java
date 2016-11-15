package ar.edu.utn.dds.grupouno.procesos;

public enum TiposProceso {
	ACTUALIZACIONLOCALESCOMERCIALES(ActualizacionLocalesComerciales.class.toString()), AGREGARACIONES(
			AgregarAcciones.class.toString()), BAJAPOIS(
					BajaPOIs.class.toString()), PROCESOMULTIPLE(ProcesoMultiple.class.toString());

	private String nombre;

	TiposProceso(String unNombre) {
		this.nombre = unNombre;
	}

	public String nombre() {
		return this.nombre;
	}

}
