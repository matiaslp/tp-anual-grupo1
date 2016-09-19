package autentification;

import java.util.ArrayList;
import java.util.Map;

import javax.mail.MessagingException;

import db.RegistroHistorico;
import poi.POI;

public abstract class Accion {
	
	protected static ArrayList<Rol> Roles;
	protected String nombreFuncion;

	public boolean enviarMail(Usuario user, String Token, String nombreDeBusqueda, String correo) throws MessagingException {
		return false;
	}

	public Map<String, Long> obtenerBusquedasPorFecha(Usuario user, String Token) {
		return null;
	}

	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(Usuario user, String Token, long terminal) {
		return null;
	}

	public Map<Long, Long> obtenerBusquedaPorUsuario(Usuario user, String Token) {
		return null;
	}

	public RegistroHistorico obtenerRegistroPorId(Usuario user, String Token, long id) {
		return null;
	}
	
	public void darDeBajaPOI(Usuario user, String Token){
	}
	
	public ArrayList<POI> busquedaPOI(Usuario user, String Token){
		return null;	
	}
	
	public void crearProcesoMultilpe(Usuario user, String Token){
		
	}
	
	public void agregarAcciones(Usuario user, String Token){
		
	}
	
	public POI obtenerInfoPOI(Usuario user, String Token){
		return null;
		
	}
	
	protected boolean validarsesion(Usuario user, String Token){
		return AuthAPI.getInstance().validarToken(Token) && user.chequearFuncionalidad(nombreFuncion);
	}


}
