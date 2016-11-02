package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class DB_POI extends Repositorio {

	private static ArrayList<POI> listadoPOI;
	private static DB_POI instance = null;

	public DB_POI(EntityManager em) {
		super(em);
		listadoPOI = new ArrayList<POI>();
	}

	public static ArrayList<POI> getListado() {
		return DB_POI.listadoPOI;
	}

	public POI getPOIbyId(long id) {
		return em.find(POI.class, id);
	}
	
	public List<POI> getPOIbyNombre(String nombre) {
		List<POI> pois = null;
		pois = em.createNamedQuery("getPOIbyNombre").setParameter("pnombre", "%" + nombre + "%").getResultList();
		return pois;
	}
	@Transactional
	public boolean agregarPOI(POI nuevoPOI) {
		try {
			em.getTransaction().begin();
			em.persist(nuevoPOI);
			em.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			return true;
		}
	}
	@Transactional
	public boolean actualizarPOI(POI poi) {
		em.getTransaction().begin();
		em.remove(getPOIbyId(poi.getId()));
		em.persist(poi);
		em.getTransaction().commit();
		return true;
	}
	
	@Transactional
	public boolean eliminarPOI(long id) {
		em.getTransaction().begin();
		POI poi = getPOIbyId(id);
		if (poi != null && poi.getFechaBaja() == null) {
			DateTime now = new DateTime();
			poi.setFechaBaja(now);
			em.getTransaction().commit();;
			return true;
		} else {
			em.getTransaction().commit();;
			return false;
		}
	}
	
	@Transactional
	private void persistir(POI poi) {
		em.getTransaction().begin();
		em.persist(poi);
		em.getTransaction().commit();
	}

	// TODO falta convertirlo a hibernate pero como debemos rehacer la entrega4 de procesos se hara en dicho momento
	public Map<Long, Boolean> bajaPoi(String[] valoresBusqueda, List<DateTime> fechasBaja) {
		Map<Long, Boolean> resumen = new HashMap<Long, Boolean>();
		for (int i = listadoPOI.size(); i > 1 || i == 1; i--) {
			//Si el POI coincide con la busqueda.
			POI poi = listadoPOI.get(i-1);
			int indexFechas=0;
			for(String valor : valoresBusqueda){
				String[] arrayValor = new String[1];
				arrayValor[0] = valor;
				if(poi.busquedaEstandar(arrayValor) && poi.dadoDeBaja(fechasBaja.get(indexFechas))){
					resumen.put(poi.getId(), eliminarPOI(poi.getId()));
					break;
				}
				indexFechas++;
			}
			
			
			
			//if (poi.busquedaEstandar(valoresBusqueda)) {
			//	for(DateTime fecha : fechasBaja){
			//		if(poi.dadoDeBaja(fecha)){
			//			resumen.put(poi.getId(), eliminarPOI(poi.getId()));
			//			break;
			//		}
			//	}
			//}
		}
		
		return resumen;
	}
}
