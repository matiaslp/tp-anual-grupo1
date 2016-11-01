package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.db.ResultadoProceso;

public class ResultadosProcesos extends Repositorio {
	ResultadosProcesos(EntityManager em) {
		super(em);
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
