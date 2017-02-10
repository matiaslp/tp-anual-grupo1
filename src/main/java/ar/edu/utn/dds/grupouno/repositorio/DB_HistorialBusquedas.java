package ar.edu.utn.dds.grupouno.repositorio;

import static org.mongodb.morphia.aggregation.Group.grouping;
import static org.mongodb.morphia.aggregation.Group.sum;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.converters.DateConverter;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.RegistroHistoricoMorphia;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;

public class DB_HistorialBusquedas extends Repositorio {

	private static ArrayList<RegistroHistorico> listadoRegistroHistorico;

	public DB_HistorialBusquedas(EntityManager em) {
		super(em);
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

	public List<RegistroHistoricoMorphia> getHistoricobyUserId(Long userID) {

//		List<RegistroHistorico> resultado = null;
//		resultado = em.createNamedQuery("getHistoricobyUserId").setParameter("ruserid", userID).getResultList();
		
		List<RegistroHistoricoMorphia> resultado = RepoMongo.getInstance().getDatastore().createQuery(RegistroHistoricoMorphia.class)
				.filter("userID", userID).asList();
		
		return resultado;
	}

	public boolean agregarHistorialBusqueda(RegistroHistorico nuevoRegistroHistorico) {
		// try {

		// nuevoPOI.setId(listadonuevoRegistroHistorico.size() + 1);
		//// persistir(nuevoRegistroHistorico);
		
		RegistroHistoricoMorphia registroMorphia = new RegistroHistoricoMorphia();
		registroMorphia.setBusqueda(nuevoRegistroHistorico.getBusqueda());
		registroMorphia.setCantResultados(nuevoRegistroHistorico.getCantResultados());
		registroMorphia.setPois(nuevoRegistroHistorico.getListaDePOIs());
		registroMorphia.setTiempoDeConsulta(nuevoRegistroHistorico.getTiempoDeConsulta());
		registroMorphia.setUserID(nuevoRegistroHistorico.getUserID());
		registroMorphia.setTime(MetodosComunes.convertJodatoJava(nuevoRegistroHistorico.getTime()));
		registroMorphia.setId(nuevoRegistroHistorico.getId());
		
		RepoMongo.getInstance().getDatastore().save(registroMorphia);
		return true;
		// } catch (Exception ex) {
		// return false;
		// }
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

		// ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
		// .createNamedQuery("RegistroHistorico.reporteBusquedasPorFecha").getResultList();

		Iterator<RegistroHistoricoMorphia> query1 = RepoMongo.getInstance().getDatastore()
				.createAggregation(RegistroHistoricoMorphia.class)
				.group("time", grouping("cantBusquedas", new Accumulator("$sum", 1)))
				.out("cantBusquedasDate", RegistroHistoricoMorphia.class);

		Query<cantBusquedasDate> query = RepoMongo.getInstance().getDatastore().createQuery(cantBusquedasDate.class);
		MorphiaIterator<cantBusquedasDate, cantBusquedasDate> recuperado = query.fetch();
		ArrayList<Object[]> resultado = new ArrayList<Object[]>();
		for (cantBusquedasDate nodo : recuperado) {
			Object[] objeto = new Object[2];
			DateConverter conversor = new DateConverter();
			objeto[0] = (Object) nodo.get_id();
			objeto[1] = (Object) nodo.getCantResultados();

			resultado.add(objeto);
		}
		RepoMongo.getInstance().getDatastore().delete(query);
		return resultado;
	}

	public ArrayList<Object[]> reporteCantidadResultadosPorTerminal(Long userID) {

		// ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
		// .createNamedQuery("RegistroHistorico.reporteCantidadResultadosPorTerminal")
		// .setParameter("ruserid", userID).getResultList();
		Query<RegistroHistoricoMorphia> query = RepoMongo.getInstance().getDatastore().find(RegistroHistoricoMorphia.class)
				.retrievedFields(true, "cantResultados", "busqueda").filter("userID", 10);
		List<RegistroHistoricoMorphia> recuperado = query.asList();
		ArrayList<Object[]> resultado = new ArrayList<Object[]>();
		for (RegistroHistoricoMorphia nodo : recuperado) {
			Object[] objeto = new Object[2];
			objeto[0] = (Object) nodo.getCantResultados();
			objeto[1] = (Object) nodo.getBusqueda();

			resultado.add(objeto);
		}
		RepoMongo.getInstance().getDatastore().delete(query);

		return resultado;
	}

	public ArrayList<Object[]> reporteBusquedaPorUsuario() {

		// ArrayList<Object[]> resultado = (ArrayList<Object[]>) em
		// .createNamedQuery("RegistroHistorico.reporteBusquedaPorUsuario").getResultList();

		// Persiste en mongo la sumatoria
		RepoMongo.getInstance().getDatastore().createAggregation(RegistroHistoricoMorphia.class)
				.group("userID", grouping("cantResultados", sum("cantResultados")))
				.out("cantBusquedas", RegistroHistoricoMorphia.class);

		// lo obtiene y lo borra de mongo
		Query<cantBusquedas> query = RepoMongo.getInstance().getDatastore().createQuery(cantBusquedas.class);
		List<cantBusquedas> recuperado = query.asList();
		ArrayList<Object[]> resultado = new ArrayList<Object[]>();
		for (cantBusquedas nodo : recuperado) {
			Object[] objeto = new Object[2];
			objeto[0] = (Object) nodo.get_id();
			objeto[1] = (Object) nodo.getCantResultados();

			resultado.add(objeto);
		}
		RepoMongo.getInstance().getDatastore().delete(query);
		return resultado;
	}
	
	public ArrayList<Object[]> cargarRegistros(List<RegistroHistoricoMorphia> lista){
		ArrayList<Object[]> resultado = new ArrayList<Object[]>();
		for(RegistroHistoricoMorphia nodo : lista){
			Object[] objeto = new Object[7];
			objeto[0] = (Object) nodo.getTime();
			objeto[1] = (Object) nodo.getUserID();
			objeto[2] = (Object) nodo.getBusqueda();
			objeto[3] = (Object) nodo.getCantResultados();
			objeto[4] = (Object) nodo.getTiempoDeConsulta();
			objeto[5] = (Object) nodo.getPois();
			objeto[6] = (Object) nodo.getId();
			
			resultado.add(objeto);
		}
		
		return resultado;
	}

	public ArrayList<Object[]> historialBusquedaEntreFechas(Long userID, Date fechaDesde, Date fechaHasta){
		List<RegistroHistoricoMorphia> recuperado = new ArrayList<RegistroHistoricoMorphia>();
		Query<RegistroHistoricoMorphia> query = RepoMongo.getInstance().getDatastore()
				.createQuery(RegistroHistoricoMorphia.class);
		
		if(userID != null){
			query = query.filter("userID", userID);
		} else {
			return new ArrayList<Object[]>();
		}
		
		if (fechaDesde == null && fechaHasta == null) {
			recuperado = query.asList();
		} else if (fechaDesde == null) {
			recuperado = query.field("time").lessThan(fechaHasta).asList();
		} else if (fechaHasta == null) {
			recuperado = query.field("time").greaterThan(fechaDesde).asList();
		} else {
			recuperado = query.field("time").greaterThan(fechaDesde)
					.field("time").lessThan(fechaHasta).asList();
		}
		
		return cargarRegistros(recuperado);
	}
}
