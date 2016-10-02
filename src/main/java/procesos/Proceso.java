package procesos;

import autentification.Usuario;

public abstract class Proceso {

	protected int cantidadReintentos = 1;
	protected boolean enviarEmail;
	protected boolean disableAccion;
	protected Usuario user;

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

	public int getCantidadReintentos() {
		return cantidadReintentos;
	}

	public void setCantidadReintentos(int cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
	}

	public boolean isEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public boolean isDisableAccion() {
		return disableAccion;
	}

	public void setDisableAccion(boolean disableAccion) {
		this.disableAccion = disableAccion;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	
	
}
