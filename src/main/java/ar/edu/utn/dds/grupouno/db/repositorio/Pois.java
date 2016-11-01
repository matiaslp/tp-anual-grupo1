package ar.edu.utn.dds.grupouno.db.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
	@Transactional
	public boolean actualizarPOI(POI poi) {
		em.flush();
//		if(eliminarPOI(poi.getId()))
//			return agregarPOI(poi);
		em.getTransaction().commit();
//		else
			return false;
	}
	
	public boolean eliminarPOI(long id) {
		em.getTransaction().begin();
		em.remove(getPOIbyId(id));
		em.getTransaction().commit();
		return true;
	}
	

	private void persistir(POI poi) {
		em.getTransaction().begin();
		em.persist(poi);
		em.getTransaction().commit();
	}

}
