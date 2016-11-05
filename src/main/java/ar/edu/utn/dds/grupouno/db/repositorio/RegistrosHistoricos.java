package ar.edu.utn.dds.grupouno.db.repositorio;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.POI;



public class RegistrosHistoricos extends Repositorio {
	
	RegistrosHistoricos(EntityManager em) {
		super (em);
	}


	public RegistroHistorico getRegistroHistoricobyId(long id) {
		return em.find(RegistroHistorico.class, id);
	}
	
public List<RegistroHistorico> getHistoricobyUserId(Long userID) {
		
		List<RegistroHistorico> resultado = null;
		resultado = em.createNamedQuery("getHistoricobyUserId").setParameter("ruserid",userID).getResultList();
		return resultado;
	}
	

	
	
	public boolean agregarRegistroHistorico(RegistroHistorico nuevoRegistroHistorico) {
//		try {
	    
		//	nuevoPOI.setId(listadonuevoRegistroHistorico.size() + 1);
			persistir(nuevoRegistroHistorico);
			return true;
//		} catch (Exception ex) {
//			return false;
//		}
	}
	

	@Transactional
	public boolean actualizarRegistroHistorico(RegistroHistorico unRegistroHistorico) {
		em.getTransaction().begin();
//		em.remove(getRegistroHistoricobyId(unRegistroHistorico.getId()));
//		em.persist(unRegistroHistorico);
		em.flush();
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
