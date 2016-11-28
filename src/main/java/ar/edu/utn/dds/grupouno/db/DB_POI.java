package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.Etiqueta;
import ar.edu.utn.dds.grupouno.db.poi.FlyweightFactoryEtiqueta;
import ar.edu.utn.dds.grupouno.db.poi.Item_Borrar;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@SuppressWarnings({ "unchecked", "unchecked" })
public class DB_POI extends Repositorio {

	private static ArrayList<POI> listadoPOI;

	public DB_POI(EntityManager em) {
		super(em);
		listadoPOI = new ArrayList<POI>();
	}

	public ArrayList<POI> getListado() {
		listadoPOI.clear();

		listadoPOI = (ArrayList<POI>) em.createNamedQuery("POI.findAll").getResultList();

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
	
	public List<POI> getPOIbyNombreConEliminados(String nombre) {
		List<POI> pois = null;
		pois = em.createNamedQuery("getPOIbyNombreConEliminados").setParameter("pnombre", "%" + nombre + "%").getResultList();
		return pois;
	}
	
	
	
	

	// @Transactional
	public boolean agregarPOI(POI nuevoPOI) {
		try {
			em.getTransaction().begin();
			FlyweightFactoryEtiqueta.getInstance().refresh();
			nuevoPOI.refreshEtiquetas();
			em.persist(nuevoPOI);
			em.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			em.getTransaction().rollback();
			return true;
		}
	}

	@Transactional
	public boolean actualizarPOI(POI poi) {
		if (em.contains(poi)) {
			try {
				em.getTransaction().begin();
				em.flush();
				em.getTransaction().commit();
				return true;
			} catch (Exception ex) {
				em.getTransaction().rollback();
				return false;
			}
		} else {
			return false;
		}
	}

	// @Transactional
	public boolean eliminarPOI(long id) {
		try {
			em.getTransaction().begin();
			POI poi = getPOIbyId(id);
			if (poi != null && poi.getFechaBaja() == null) {
				DateTime now = new DateTime();
				poi.setFechaBaja(now);
				em.getTransaction().commit();
				return true;
			} else {
				em.getTransaction().commit();
				return false;
			}
		} catch (Exception ex) {
			em.getTransaction().rollback();
			return false;
		}
	}

	// @Transactional
	private void persistir(POI poi) {
		em.getTransaction().begin();
		em.persist(poi);
		em.getTransaction().commit();
	}

	// TODO falta convertirlo a hibernate pero como debemos rehacer la entrega4 de procesos se hara en dicho momento
	public Map<Long, Boolean> bajaPoi(List<Item_Borrar> itemsABorrar) {
		Map<Long, Boolean> resumen = new HashMap<Long, Boolean>();
		if(itemsABorrar.size() > 0){
			for(Item_Borrar itemABorrar : itemsABorrar) {
				String[] arrayValor = new String[1];
				arrayValor[0] = itemABorrar.getParametro();
				
				for (int i = listadoPOI.size() -1; i > 1 || i == 1; i--) {
					POI poi = listadoPOI.get(i);
					//En caso de ser el item que estaba buscando, lo elimino y salgo del ciclo.
					if(poi.busquedaEstandar(arrayValor)){
						resumen.put(poi.getId(), eliminarPOI(poi.getId()));
						break;
					}
				}
			}
		}
		return resumen;	
	}
}
