package ar.edu.utn.dds.grupouno.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.abmc.poi.Etiqueta;

public class DB_Etiqueta extends Repositorio {

	private static ArrayList<Etiqueta> listadoEtiquetas;

	public DB_Etiqueta(EntityManager em) {
		super(em);
		listadoEtiquetas = new ArrayList<Etiqueta>();
	}

	public ArrayList<Etiqueta> getListado() {
		listadoEtiquetas.clear();

		listadoEtiquetas = (ArrayList<Etiqueta>) em.createNamedQuery("Etiqueta.findAll").getResultList();

		return DB_Etiqueta.listadoEtiquetas;
	}

	public List<Etiqueta> getEtiquetabyNombre(String nombre) {

		List<Etiqueta> etiquetas = null;
		etiquetas = em.createNamedQuery("Etiqueta.getEtiquetabyNombre").setParameter("enombre", "%" + nombre + "%")
				.getResultList();
		return etiquetas;
	}

	public boolean agregarEtiqueta(Etiqueta nuevaEtiqueta) {
		em.getTransaction().begin();
		em.persist(nuevaEtiqueta);
		em.getTransaction().commit();
		return true;
	}
}
