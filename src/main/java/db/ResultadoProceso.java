package db;

import org.joda.time.DateTime;
import procesos.Proceso;

public class ResultadoProceso {
	
	private long id;
	private DateTime inicioEjecucion;
	private DateTime finEjecucion;
	Proceso proc;
	private long userID;
	enum resultado { OK,ERROR }
	String mensajeError;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public DateTime getInicioEjecucion() {
		return inicioEjecucion;
	}
	public void setInicioEjecucion(DateTime inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}
	public DateTime getFinEjecucion() {
		return finEjecucion;
	}
	public void setFinEjecucion(DateTime finEjecucion) {
		this.finEjecucion = finEjecucion;
	}
	public Proceso getProc() {
		return proc;
	}
	public void setProc(Proceso proc) {
		this.proc = proc;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	
}
