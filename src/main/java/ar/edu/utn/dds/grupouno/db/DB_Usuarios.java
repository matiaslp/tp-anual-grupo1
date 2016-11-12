package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class DB_Usuarios extends Repositorio {

	private ArrayList<Usuario> listaUsuarios;
	private static DB_Usuarios instance = null;

	public DB_Usuarios(EntityManager em) {
		super (em);
		listaUsuarios = new ArrayList<Usuario>();
	}
	
	public Long getRolId(String nombre){
			Long id;
			List<Long> lista = em.createNamedQuery("getRolId").setParameter("valor", nombre).getResultList();
			return lista.get(0);
	}
	
	public Rol getRolById(Long id){
		List<Rol> lista = em.createNamedQuery("getRolById").setParameter("id", id).getResultList();
		return lista.get(0);
	}

	@Transactional
	public void persistirUsuario(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}

	public List<Usuario> getListaUsuarios() {
		return (ArrayList<Usuario>) Repositorio.getInstance().getEm()
				.createNamedQuery("getAllUsers").getResultList();
	}
	
	public ArrayList<Accion> getListadoAcciones() {

		ArrayList<Accion> listadoAcciones = (ArrayList<Accion>) em.createNamedQuery("Accion.findAll").getResultList();

		return listadoAcciones;
	}
	
	public Usuario getUsuarioByName(String username){
		List<Usuario> lista= Repositorio.getInstance().usuarios().getEm()
				.createNamedQuery("getUsuarioByName")
				.setParameter("unombre", username)
				.getResultList();
		if(lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional
	public boolean actualizarUsuario(Usuario user){
		
		if (user != null && em.contains(user)) {
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
	
	public boolean deleteUsuario( long l) {
		Usuario user = getUsuarioById(l);
		if ( user != null) {
			listaUsuarios.remove(user);
			DB_Sesiones.getInstance().removerSesiones(user.getUsername());
			return true;
		}
		return false;
	}
	
	public Usuario getUsuarioById(long l){
		for ( Usuario user : listaUsuarios ) {
			if (user.getId() == l)
				return user;
		}
		return null;
	}
}
