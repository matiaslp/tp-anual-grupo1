package ar.edu.utn.dds.grupouno.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.procesos.Proceso;

@Entity
@Table(name = "ResultadoProcesos")
public class ResultadoProceso {

	@Id
	@GeneratedValue
	private long id;
	private DateTime inicioEjecucion;
	private DateTime finEjecucion;
//	Proceso proc;
	private long userID;
	Resultado resultado;
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

//	public Proceso getProc() {
//		return proc;
//	}
//
//	public void setProc(Proceso proc) {
//		this.proc = proc;
//	}

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

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	// Si el id no se desea setear, el mismo es generado internamente
	// al colocar el paramentro en cero
	public ResultadoProceso(DateTime inicioEjecucion, DateTime finEjecucion, Proceso proc, long userID,
			String mensajeError, Resultado unResultado) {
		super();
		this.id = 0;
		this.inicioEjecucion = inicioEjecucion;
		this.finEjecucion = finEjecucion;
	//	this.proc = proc;
		this.userID = userID;
		this.mensajeError = mensajeError;
		this.resultado = unResultado;
	}

	public ResultadoProceso(){
		
	}
}
