package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.db.Sesion;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.modelo.Persistible;

public class DB_Sesiones extends Repositorio {


	public DB_Sesiones(EntityManager em) {
		super(em);
	}


	public List<Sesion> getDiccionarioTokenUser() {
		return (ArrayList<Sesion>) em.createNamedQuery("Sesion.findAll").getResultList();
	}
	
	public List<Sesion> getSesionbyUser(String nombre) {

		List<Sesion> sesiones = null;
		sesiones = em.createNamedQuery("Sesion.getSesionbyUser").setParameter("susername", "%" + nombre + "%").getResultList();
		return sesiones;
	}
	
	public String validarToken(String nombre) {

		return (String)em.createNamedQuery("Sesion.validarToken").setParameter("stoken", "%" + nombre + "%").getResultList().get(0);
	}


	//per
	public boolean agregarSesion(String token, String user){
		Sesion sesion = new Sesion(token,user);
		 try {
		em.getTransaction().begin();
		em.persist(sesion);
		em.getTransaction().commit();
		return true;
		 } catch (Exception ex) {
		 em.getTransaction().rollback();
		 return false;
		 }
	}
	//per
	public void removerSesion(Sesion sesion) {
		em.getTransaction().begin();
		em.remove(sesion);
		em.getTransaction().commit();
	}
	
	public void removerSesion(String token, String user) {
		em.getTransaction().begin();
		Sesion sesion = (Sesion)em.createNamedQuery("Sesion.getSesionbyUserAndToken")
		.setParameter("susername", "%" + user + "%")
		.setParameter("stoken", token)
		.getResultList().get(0);
		em.remove(sesion);
		em.getTransaction().commit();
	}
	//per
	public void removerSesiones(String user) {
		List<Sesion> sesiones = this.getSesionbyUser(user);
		for (Sesion sesion: sesiones)
			removerSesion(sesion);
		
	}

}
