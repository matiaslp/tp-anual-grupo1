package autentification;

import java.util.Map;

import javax.mail.MessagingException;

import db.RegistroHistorico;

public abstract class Accion {
	
	public boolean enviarMail(String nombreDeBusqueda, Long Segundos) throws MessagingException {
		return false;
	}
	
	public Map<String,Long>obtenerBusquedasPorFecha(){
		return null;
	}
	
	public Map<Long, Long> obtenerCantidadResultadosPorTerminal(long terminal){
		return null;
	}
	
	public Map<Long, Long> obtenerBusquedaPorUsuario(){
		return null;
	}
	
	public RegistroHistorico obtenerRegistroPorId(long id){
		return null;
	}

}