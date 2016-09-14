package procesos;


public abstract class Proceso {
	
	
	int cantidadReintentos = 1;
	boolean enviarEmail;
	boolean disableAccion;
	
	public void execute(){
		
	}

	public Proceso(int cantidadReintentos, boolean enviarEmail, boolean disableAccion) {
		super();
		this.cantidadReintentos = cantidadReintentos;
		this.enviarEmail = enviarEmail;
		this.disableAccion = disableAccion;
	}


	
	

}
