package procesos;

import java.util.ArrayList;

import autentification.Usuario;

public class ProcesoMultiple extends Proceso {

	private static ArrayList<Proceso> listadoProcesos;

	public ProcesoMultiple(int cantidadReintentos, boolean enviarEmail,
			ArrayList<Proceso> procList, Usuario unUser) {
		super(cantidadReintentos, enviarEmail, unUser);
		listadoProcesos = procList;
	}

	public void agregarProceso(Proceso proc) {
		listadoProcesos.add(proc);
	}

	public void removerProceso(Proceso proc) {
		listadoProcesos.remove(proc);
	}

	
	// se tomo como decision de diseño que proceso Multiple no tenga la opcion de
	// emviar email o ejecutarse N veces y delega dichas responsabilidades en
	// los procesos que ejecuta
	@Override
	public void execute() {
		for (Proceso proc : listadoProcesos)
			proc.execute();
	}

}
