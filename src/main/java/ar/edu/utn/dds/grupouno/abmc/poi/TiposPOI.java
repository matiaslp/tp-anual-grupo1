package ar.edu.utn.dds.grupouno.abmc.poi;

public enum TiposPOI {
	BANCO("banco"), CGP("cgp"), LOCAL_COMERCIAL("local comercial"), PARADA_COLECTIVO("parada colectivo");

	private String nombre;

	TiposPOI(String unNombre) {
		this.nombre = unNombre;
	}

	public String nombre() {
		return this.nombre;
	}
	
    public static TiposPOI getEnumByString(String code){
        for(TiposPOI e : TiposPOI.values()){
            if(e.nombre().equals(code)) return e;
        }
        return null;
    }

}
