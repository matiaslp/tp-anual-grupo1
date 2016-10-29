package ar.edu.utn.dds.grupouno.db.repositorio;

import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.db.poi.POI;

public class Pois extends Repositorio {
	Pois(EntityManager em) {
		super(em);
	}

	public POI getPOIbyId(Long id) {
		return em.find(POI.class, id);
	}
	
	public List<POI> getPOIbyNombre(String nombre) {
		List<POI> pois = null;
		pois = em.createNamedQuery("getPOIbyNombre").setParameter("pnombre", "%" + nombre + "%").getResultList();
		return pois;
	}
	
	public boolean agregarPOI(POI nuevoPOI) {
		try {
			// testear
		//	nuevoPOI.setId(listadoPOI.size() + 1);
			persistir(nuevoPOI);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	// Persistent objects are updated by the framework automatically????
	public boolean actualizarPOI(POI poi) {
//		boolean result;
//		POI old_poi;
//		old_poi = (POI) em.createNamedQuery("actualizarPOI").setParameter("pid", "%" + poi.getId() + "%").;
//		if (poi != null)
			
		return true;
	}
	
//	public boolean eliminarPOI(long id) {
//		boolean result;
//		em.g
//		result = em.createNamedQuery("eliminarPOI").setParameter("pnombre", "%" + nombre + "%").getResultList();
//		return result;
//	}
	

	public void persistir(POI poi) {
		em.getTransaction().begin();
		em.persist(poi);
		em.getTransaction().commit();
	}

}
