package procesos;

import autentification.Usuario;

public class ActualizacionLocalesComerciales extends Proceso {

	String filePath;

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public ActualizacionLocalesComerciales(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			String filePath, Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		this.filePath = filePath;
	}

}
