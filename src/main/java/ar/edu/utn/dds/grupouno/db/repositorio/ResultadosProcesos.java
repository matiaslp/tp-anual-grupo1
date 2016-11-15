package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.db.ResultadoProceso;

public class ResultadosProcesos {
	protected EntityManager em;
	
	ResultadosProcesos(EntityManager emanager) {
		this.em = emanager;
	}

	public ResultadoProceso resultadoProcesoPorId(Long id) {
		return em.find(ResultadoProceso.class, id);
	}

	public void persistir(ResultadoProceso resProc) {
		em.getTransaction().begin();
		em.persist(resProc);
		em.getTransaction().commit();
	}
}
