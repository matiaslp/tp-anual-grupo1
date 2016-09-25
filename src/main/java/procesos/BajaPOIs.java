package procesos;

import autentification.Usuario;

public class BajaPOIs extends Proceso {

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
	}

}
