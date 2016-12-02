package ar.edu.utn.dds.grupouno.procesos;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.Persistible;

@SuppressWarnings("serial")
@Entity
@Table(name = "ResultadoProceso")
@NamedQueries({
@NamedQuery(name = "ResultadoProceso.findAll", query = "SELECT r FROM ResultadoProceso r")})
public class ResultadoProceso extends Persistible{

	private ZonedDateTime inicioEjecucion;
	private ZonedDateTime finEjecucion;
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

	public TiposProceso getProc() {
		return this.proc;
	}

	public void setProc(TiposProceso p) {
		this.proc = p;
	}

	public ZonedDateTime getInicioEjecucion() {
		return inicioEjecucion;
	}

	public void setInicioEjecucion(ZonedDateTime inicioEjecucion) {
		this.inicioEjecucion = inicioEjecucion;
	}

	public ZonedDateTime getFinEjecucion() {
		return finEjecucion;
	}

	public void setFinEjecucion(ZonedDateTime finEjecucion) {
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
	public ResultadoProceso(ZonedDateTime inicioEjecucion, ZonedDateTime finEjecucion, TiposProceso p, long userID,
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
