package ar.edu.utn.dds.grupouno.db.repositorio;


import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.db.RegistroHistorico;


public class RegistrosHistoricos extends Repositorio {
	RegistrosHistoricos(EntityManager em) {
		super(em);
	}

	public RegistroHistorico getRegistroHistoricobyId(Long id) {
		return em.find(RegistroHistorico.class, id);
	}
	
	/*public List<RegistroHistorico> getRegistroHistoricobyNombre(String nombre) {
		List<RegistroHistorico> RegistrosHistoricos = null;
		RegistrosHistoricos = em.createNamedQuery("getRegistroHistoricobyNombre").setParameter("pnombre", "%" + nombre + "%").getResultList();
		return RegistrosHistoricos;
	}
	*/
	public boolean agregarRegistroHistorico(RegistroHistorico nuevoRegistroHistorico) {
		try {
			// testear
		//	nuevoPOI.setId(listadoPOI.size() + 1);
			persistir(nuevoRegistroHistorico);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	// Persistent objects are updated by the framework automatically????
	public boolean actualizarRegistroHistorico(RegistroHistorico unRegistroHistorico) {
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
	

	public void persistir(RegistroHistorico unRegistroHistorico) {
		em.getTransaction().begin();
		em.persist(unRegistroHistorico);
		em.getTransaction().commit();
	}

}
