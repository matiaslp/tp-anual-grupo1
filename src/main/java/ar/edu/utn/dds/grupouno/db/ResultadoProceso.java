package ar.edu.utn.dds.grupouno.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.modelo.Persistible;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.procesos.TiposProceso;

@Entity
@Table(name = "ResultadoProceso")
public class ResultadoProceso extends Persistible{

	private DateTime inicioEjecucion;
	private DateTime finEjecucion;
	TiposProceso proc;
	private long userID;
	Resultado resultado;
	String mensajeError;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public TiposProceso getProc(){
		return this.proc;
	}
	
	public void setProc(TiposProceso p){
		this.proc = p;
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
	public ResultadoProceso(DateTime inicioEjecucion, DateTime finEjecucion, TiposProceso p, long userID,
			String mensajeError, Resultado unResultado) {
		super();
		this.inicioEjecucion = inicioEjecucion;
		this.finEjecucion = finEjecucion;
		this.proc = p;
		this.userID = userID;
		this.mensajeError = mensajeError;
		this.resultado = unResultado;
	}

	public ResultadoProceso(){
		
	}

}
