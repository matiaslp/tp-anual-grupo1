package ar.edu.utn.dds.grupouno.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.db.Sesion;

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

	public void removerSesiones(String user) {
		List<Sesion> sesiones = this.getSesionbyUser(user);
		for (Sesion sesion: sesiones)
			em.remove(sesion);
		

	}
	
}