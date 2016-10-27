package ar.edu.utn.dds.grupouno.db.repositorio;

import java.util.List;

import javax.persistence.EntityManager;

import ar.edu.utn.dds.grupouno.autentification.Usuario;

public class Usuarios extends Repositorio {
	Usuarios(EntityManager em) {
		super(em);
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
