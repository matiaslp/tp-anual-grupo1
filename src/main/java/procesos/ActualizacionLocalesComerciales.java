package procesos;

public class ActualizacionLocalesComerciales extends Proceso {

	String filePath;

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public ActualizacionLocalesComerciales(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			String filePath) {
		super(cantidadReintentos, enviarEmail, disableAccion);
		this.filePath = filePath;
	}

}
