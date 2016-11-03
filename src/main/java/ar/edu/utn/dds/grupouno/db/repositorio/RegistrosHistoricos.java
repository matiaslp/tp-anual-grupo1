package ar.edu.utn.dds.grupouno.db.repositorio;


import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ar.edu.utn.dds.grupouno.db.RegistroHistorico;



public class RegistrosHistoricos {
	protected EntityManager em;
	
	RegistrosHistoricos(EntityManager emanager) {
		this.em = emanager;
	}


	public RegistroHistorico getRegistroHistoricobyId(Long id) {
		return em.find(RegistroHistorico.class, id);
	}
	public List<RegistroHistorico> getRegistroHistoricobyNombre(String nombre) {
		List<RegistroHistorico> unRegistroHistorico = null;
		unRegistroHistorico = em.createNamedQuery("getPOIbyNombre").setParameter("pnombre", "%" + nombre + "%").getResultList();
		return unRegistroHistorico;
	}
	
	
	public boolean agregarRegistroHistorico(RegistroHistorico nuevoRegistroHistorico) {
		try {
	    
		//	nuevoPOI.setId(listadonuevoRegistroHistorico.size() + 1);
			persistir(nuevoRegistroHistorico);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	

	@Transactional
	public boolean actualizarRegistroHistorico(RegistroHistorico unRegistroHistorico) {
		em.getTransaction().begin();
		em.remove(getRegistroHistoricobyId(unRegistroHistorico.getId()));
		em.persist(unRegistroHistorico);
		em.getTransaction().commit();
		return true;
	}
	
	public boolean eliminarRegistroHistorico(long id) {
		em.getTransaction().begin();
		em.remove(getRegistroHistoricobyId(id));
		em.getTransaction().commit();
		return true;
	}
	public void persistir(RegistroHistorico unRegistroHistorico) {
		em.getTransaction().begin();
		em.persist(unRegistroHistorico);
		em.getTransaction().commit();
	}
	
	

}
