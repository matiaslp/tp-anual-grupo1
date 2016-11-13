package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import ar.edu.utn.dds.grupouno.db.DB_Etiqueta;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.poi.POI;

public class Repositorio {
	private DB_Usuarios usuarios;
	private DB_POI pois;
	private ResultadosProcesos resultadosProcesos;
	private DB_HistorialBusquedas registroHistorico;
	private DB_Etiqueta etiquetas;
	protected EntityManager em;
	private static Repositorio instance = null;

	public static Repositorio getInstance() {
		if (instance == null) {
			final String PERSISTENCE_UNIT_NAME = "tp-anual";
			EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			instance = new Repositorio(emFactory.createEntityManager());
		}
		return instance;
	}

	// public Repositorio() {
	// }

	public Repositorio(EntityManager emanager) {
		this.em = emanager;
	}

	public DB_Usuarios usuarios() {
		if (usuarios == null) {
			usuarios = new DB_Usuarios(em);
		}
		return usuarios;
	}

	public DB_POI pois() {
		if (pois == null) {
			pois = new DB_POI(em);
		}
		return pois;
	}

	public ResultadosProcesos resultadosProcesos() {
		if (resultadosProcesos == null) {
			resultadosProcesos = new ResultadosProcesos(em);
		}
		return resultadosProcesos;
	}

	public DB_HistorialBusquedas resultadosRegistrosHistoricos() {
		if (registroHistorico == null) {
			registroHistorico = new DB_HistorialBusquedas(em);
		}
		return registroHistorico;
	}

	public DB_Etiqueta etiquetas() {
		if (etiquetas == null) {
			etiquetas = new DB_Etiqueta(em);
		}
		return etiquetas;
	}

	// @Transactional
	public void remove(Object obj) {
		if (obj != null) {
			// try {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
			// } catch (Exception ex) {
			// em.getTransaction().rollback();
			// }
		}
	}
	
	public void persistir(Object obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
	}

	public void cerrar() {
		em.close();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
