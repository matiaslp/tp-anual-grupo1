package procesos;

import java.util.ArrayList;

import autentification.Usuario;
import db.Resultado;
import db.ResultadoProceso;

public abstract class Proceso {

	protected int cantidadReintentos = 1;
	protected boolean enviarEmail;
	protected boolean disableAccion;
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
			for (int i = 1; (this.cantidadReintentos > 0 && this.cantidadReintentos < i); i++) {
				resultado = procesado();
				// acumulamos los resultado en una lista para armar el email
				if (resultado.getResultado().equals(Resultado.ERROR))
					listaResultados.add(resultado);
			}
			if (this.enviarEmail) {
				// enviar mail
			}
		}
	}

	public Proceso(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super();
		this.cantidadReintentos = cantidadReintentos;
		this.enviarEmail = enviarEmail;
		this.disableAccion = disableAccion;
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

	public boolean isDisableAccion() {
		return disableAccion;
	}

	public void setDisableAccion(boolean disableAccion) {
		this.disableAccion = disableAccion;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	
	
}
