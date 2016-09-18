package procesos;

import java.util.ArrayList;

public class ProcesoMultiple extends Proceso {

	private static ArrayList<Proceso> listadoProcesos;
	
	
	
	public ProcesoMultiple(int cantidadReintentos, boolean enviarEmail, boolean disableAccion) {
		super(cantidadReintentos, enviarEmail, disableAccion);
		listadoProcesos = new ArrayList<Proceso>();
	}
	
	public void agregarProceso( Proceso proc){
		listadoProcesos.add(proc);
	}
	
	public void removerProceso ( Proceso proc){
		listadoProcesos.remove(proc);
	}
	
	@Override
	public void execute() {
		for ( Proceso proc : listadoProcesos)
			proc.execute();
	}

}
