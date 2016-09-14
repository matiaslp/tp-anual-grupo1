package procesos;

import procesos.Proceso;

public class AgregarAcciones extends Proceso {
	
	String filePath;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, boolean disableAccion, String filePath) {
		super(cantidadReintentos, enviarEmail, disableAccion);
		this.filePath = filePath;
	}
}
