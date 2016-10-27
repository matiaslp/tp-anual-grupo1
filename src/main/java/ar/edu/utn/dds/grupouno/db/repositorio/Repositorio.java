package ar.edu.utn.dds.grupouno.db.repositorio;

import javax.persistence.EntityManager;

public class Repositorio {
	private Usuarios usuarios;
	protected EntityManager em;

	public Repositorio(EntityManager em) {
		this.em = em;
	}

	public Usuarios usuarios() {
		if (usuarios == null) {
			usuarios = new Usuarios(em);
		}
		return usuarios;
	}

	public void cerrar() {
		em.close();
	}
}
