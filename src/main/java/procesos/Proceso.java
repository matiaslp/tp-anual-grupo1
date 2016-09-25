package procesos;

import autentification.Usuario;

public abstract class Proceso {

	int cantidadReintentos = 1;
	boolean enviarEmail;
	boolean disableAccion;
	Usuario user;

	public void execute() {

	}

	public Proceso(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super();
		this.cantidadReintentos = cantidadReintentos;
		this.enviarEmail = enviarEmail;
		this.disableAccion = disableAccion;
		this.user = unUser;
	}

}
