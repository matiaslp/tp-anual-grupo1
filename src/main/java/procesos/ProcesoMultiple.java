package procesos;

import java.util.ArrayList;

import autentification.Usuario;

public class ProcesoMultiple extends Proceso {

	private static ArrayList<Proceso> listadoProcesos;

	public ProcesoMultiple(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			ArrayList<Proceso> procList, Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		listadoProcesos = procList;
	}

	public void agregarProceso(Proceso proc) {
		listadoProcesos.add(proc);
	}

	public void removerProceso(Proceso proc) {
		listadoProcesos.remove(proc);
	}

	@Override
	public void execute() {
		for (Proceso proc : listadoProcesos)
			proc.execute();
	}

}
