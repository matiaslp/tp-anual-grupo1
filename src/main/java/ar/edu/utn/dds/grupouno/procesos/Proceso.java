package ar.edu.utn.dds.grupouno.procesos;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.email.EnviarEmail;
import ar.edu.utn.dds.grupouno.modelo.Persistible;


public abstract class Proceso {

	protected int cantidadReintentos = 1;
	protected boolean enviarEmail;
	protected Usuario user;

	
	// debe ser implementado en las clases hijo
	public ResultadoProceso procesado() {
		return null;
		
	}
	
	public void execute() {
		// ejecutamos el proceso
		ResultadoProceso resultado = procesado();
		ArrayList<ResultadoProceso> listaResultados = new ArrayList<ResultadoProceso>();
		// Si el resultado es erroneo
		if (resultado.getResultado().equals(Resultado.ERROR)) {
			listaResultados.add(resultado);
			// Se reintenta cantidadReintentos veces
			for (int i = 1; (this.cantidadReintentos > 0 && this.cantidadReintentos < i 
					&& resultado.getResultado().equals(Resultado.ERROR)); i++) {
				resultado = procesado();
				// acumulamos los resultado en una lista para armar el email
				if (resultado.getResultado().equals(Resultado.ERROR))
					listaResultados.add(resultado);
			}
			if (this.enviarEmail && user.getCorreo() != null) {
				EnviarEmail.mandarCorreoProcesoError(user,listaResultados);
			}
		}
	}

	public Proceso(int cantidadReintentos, boolean enviarEmail,
			Usuario unUser) {
		super();
		this.cantidadReintentos = cantidadReintentos;
		this.enviarEmail = enviarEmail;
		this.user = unUser;
	}

	public int getCantidadReintentos() {
		return cantidadReintentos;
	}

	public void setCantidadReintentos(int cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
	}

	public boolean isEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Proceso(){
		
	}
	
}
