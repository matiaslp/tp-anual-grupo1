package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.autentification.Usuario;

public class Usuarios {
	
	protected EntityManager em;
	
	Usuarios(EntityManager emanager) {
		this.em = emanager;
	}

	public Usuario buscarPorId(Long id) {
		return em.find(Usuario.class, id);
	}

	public void persistir(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}

}
