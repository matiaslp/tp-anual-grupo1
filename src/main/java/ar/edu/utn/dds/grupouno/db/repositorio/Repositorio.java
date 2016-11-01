package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;

public class Repositorio {
	private Usuarios usuarios;
	private Pois pois;
	private ResultadosProcesos resultadosProcesos;
	private RegistrosHistoricos registroHistorico;
	protected EntityManager em;

	public Repositorio(EntityManager em) {
		this.em = em;
	}

	public Usuarios usuarios() {
		if (usuarios == null) {
			usuarios = new Usuarios(em);
		}
		return usuarios;
	}
	
	public Pois pois() {
		if (pois == null) {
			pois = new Pois(em);
		}
		return pois;
	}
	
	public ResultadosProcesos resultadosProcesos() {
		if (resultadosProcesos == null) {
			resultadosProcesos = new ResultadosProcesos(em);
		}
		return resultadosProcesos;
	}

	public void cerrar() {
		em.close();
	}
}
