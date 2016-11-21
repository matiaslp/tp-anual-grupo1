package ar.edu.utn.dds.grupouno.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;

public class DB_HistorialBusquedas extends Repositorio {

	private static ArrayList<RegistroHistorico> listadoRegistroHistorico;

	public DB_HistorialBusquedas(EntityManager em) {
		super(em);
		listadoRegistroHistorico = new ArrayList<RegistroHistorico>();
	}

	public ArrayList<RegistroHistorico> getListado() {
		listadoRegistroHistorico.clear();

		listadoRegistroHistorico = (ArrayList<RegistroHistorico>) em.createNamedQuery("RegistroHistorico.findAll")
				.getResultList();

		return listadoRegistroHistorico;
	}

	public RegistroHistorico getRegistroHistoricobyId(long id) {
		return em.find(RegistroHistorico.class, id);
	}

	public List<RegistroHistorico> getHistoricobyUserId(Long userID) {

		List<RegistroHistorico> resultado = null;
		resultado = em.createNamedQuery("getHistoricobyUserId").setParameter("ruserid", userID).getResultList();
		return resultado;
	}

	public boolean agregarHistorialBusqueda(RegistroHistorico nuevoRegistroHistorico) {
		// try {

		// nuevoPOI.setId(listadonuevoRegistroHistorico.size() + 1);
		////persistir(nuevoRegistroHistorico);
		RepoMongo.getInstance().getDatastore().save(nuevoRegistroHistorico);
		return true;
		// } catch (Exception ex) {
		// return false;
		// }
	}

	@Transactional
	public boolean actualizarRegistroHistorico(RegistroHistorico unRegistroHistorico) {
		em.getTransaction().begin();
		// em.remove(getRegistroHistoricobyId(unRegistroHistorico.getId()));
		// em.persist(unRegistroHistorico);
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

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
				.createNamedQuery("RegistroHistorico.reporteBusquedasPorFecha").getResultList();

		return resultado;
	}

	public ArrayList<Object[]> reporteCantidadResultadosPorTerminal(Long userID) {

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
				.createNamedQuery("RegistroHistorico.reporteCantidadResultadosPorTerminal")
				.setParameter("ruserid", userID).getResultList();

		return resultado;
	}

	public ArrayList<Object[]> reporteBusquedaPorUsuario() {

		ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
				.createNamedQuery("RegistroHistorico.reporteBusquedaPorUsuario").getResultList();

		return resultado;
	}

}
