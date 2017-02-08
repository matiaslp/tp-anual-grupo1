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
    
    
    public boolean isBanco(){
    	return TiposPOI.BANCO.nombre.equals(nombre);
    }
    
    public boolean isCGP(){
    	return TiposPOI.CGP.nombre.equals(nombre);
    }
    
    public boolean isParada(){
    	return TiposPOI.PARADA_COLECTIVO.nombre.equals(nombre);
    }
    
    public boolean isLocal(){
    	return TiposPOI.LOCAL_COMERCIAL.nombre.equals(nombre);
    }

}
