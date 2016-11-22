package ar.edu.utn.dds.grupouno.db;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;




public class DB_HistorialBusquedas extends Repositorio {
	
	private static ArrayList<RegistroHistorico> listadoRegistroHistorico;
	
	public DB_HistorialBusquedas(EntityManager em) {
		super (em);
		listadoRegistroHistorico = new ArrayList<RegistroHistorico>();
	}
	
	
	public ArrayList<RegistroHistorico> getListado() {
		listadoRegistroHistorico.clear();

		listadoRegistroHistorico = (ArrayList<RegistroHistorico>) em.createNamedQuery("RegistroHistorico.findAll").getResultList();

		return listadoRegistroHistorico;
	}


	public RegistroHistorico getRegistroHistoricobyId(long id) {
		return em.find(RegistroHistorico.class, id);
	}
	
public List<RegistroHistorico> getHistoricobyUserId(long userID) {
		
		List<RegistroHistorico> resultado = null;
		resultado = em.createNamedQuery("getHistoricobyUserId").setParameter("ruserid",userID).getResultList();
		return resultado;
	}

public List<RegistroHistorico> getHistoricobyEntreFechas(ZonedDateTime desde, ZonedDateTime hasta) {
	
	List<RegistroHistorico> resultado = null;
	resultado = em.createNamedQuery("getHistoricobyEntreFechas").setParameter("desde",desde).setParameter("hasta",hasta).getResultList();
	return resultado;
}

public List<RegistroHistorico> getHistoricobyEntreFechasConUserId(ZonedDateTime desde, ZonedDateTime hasta,Long userID) {
	
	List<RegistroHistorico> resultado = null;
	resultado = em.createNamedQuery("getHistoricobyEntreFechasConUserId").setParameter("desde",desde).setParameter("hasta",hasta).setParameter("ruserid",userID).getResultList();
	return resultado;
}

	
	
	public boolean agregarHistorialBusqueda(RegistroHistorico nuevoRegistroHistorico) {
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

	
	public ArrayList<Object[]> reporteBusquedasPorFecha() {

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em.createNamedQuery("RegistroHistorico.reporteBusquedasPorFecha").getResultList();

		return resultado;
	}
	
	public ArrayList<Object[]> reporteCantidadResultadosPorTerminal(Long userID) {

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em.createNamedQuery("RegistroHistorico.reporteCantidadResultadosPorTerminal").setParameter("ruserid",userID).getResultList();

		return resultado;
	}
	
	public ArrayList<Object[]> reporteBusquedaPorUsuario() {

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em.createNamedQuery("RegistroHistorico.reporteBusquedaPorUsuario").getResultList();

		return resultado;
	}
	
	public static Repositorio getInstance() {
		if (instance == null) {
			final String PERSISTENCE_UNIT_NAME = "tp-anual";
			EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			instance = new DB_HistorialBusquedas(emFactory.createEntityManager());
		}
		return instance;
	}

}
