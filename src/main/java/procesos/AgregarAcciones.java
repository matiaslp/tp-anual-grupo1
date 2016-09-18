package procesos;

import java.util.HashMap;
import java.util.Map;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
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
	public static boolean AgregarAccionesA(Usuario unUsuario,Map<String,Accion> accionesNuevas){
		if(unUsuario.getRol()==Rol.ADMIN)
		{
		Map<String, Accion> accionesAnteriores = new HashMap<String, Accion>();
		accionesAnteriores = unUsuario.getFuncionalidades();
		accionesAnteriores.forEach((k,v) -> accionesNuevas.put(k,v));
		unUsuario.setFuncionalidades(accionesNuevas);
		
		return true;
		}
		else{
			
			if(accionesNuevas.containsKey("FuncBusquedaPorUsuario")){
				return false;
		}else{
			return true;
				};
		}
	}
	private boolean validarAccionUsuarioTerminal(Accion accion){
		return true;
	}
	private boolean validarAccionUsuarioAdministrador(Accion accion){
		return true;
	}

	
}
