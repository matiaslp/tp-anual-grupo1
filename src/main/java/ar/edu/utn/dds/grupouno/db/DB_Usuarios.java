package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.hibernate.HibernateUtil;

public class DB_Usuarios extends Repositorio {

	private ArrayList<Usuario> listaUsuarios;
	private static DB_Usuarios instance = null;

	public DB_Usuarios(EntityManager em) {
		super(em);
		listaUsuarios = new ArrayList<Usuario>();
	}

	public Long getRolId(String nombre) {
		Long id;
		List<Long> lista = em.createNamedQuery("getRolId").setParameter("valor", nombre).getResultList();
		return lista.get(0);
	}

	public Rol getRolById(Long id) {
		List<Rol> lista = em.createNamedQuery("getRolById").setParameter("id", id).getResultList();
		return lista.get(0);
	}

	public Rol getRolByName(String nombre) {
		List<Rol> lista = Repositorio.getInstance().usuarios().getEm().createNamedQuery("Rol.getRolByName")
				.setParameter("rnombre", nombre).getResultList();
		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public void persistirUsuario(Usuario usuario) {
		em.getTransaction().begin();
		Set<Accion> acciones = usuario.getFuncionalidades();
		usuario.setFuncionalidades(new HashSet<Accion>());
		// Adding manually (using your cascade ALL)
		for (Accion acc : acciones) {
			for (Accion accDB : this.getListadoAcciones())
				if (acc != null && acc.getNombreFuncion().equals(accDB.getNombreFuncion())) {
					// accion.setRol(this.getRolByName(rol.getValue())); //
					// already exists
					//Accion accPersisted = em.merge(acc);
					usuario.setFuncionalidad(accDB);
					break;
				}
//			if (usuario.getFuncionalidad(acc.getNombreFuncion()) == null){
//					this.persistir(acc);// I don't exist persist
//					usuario.setFuncionalidad(acc);
//				}
		}
		 
		 em.merge(usuario);
		 em.getTransaction().commit();
	}

	public List<Usuario> getListaUsuarios() {
		return (ArrayList<Usuario>) Repositorio.getInstance().getEm().createNamedQuery("getAllUsers").getResultList();
	}

	public ArrayList<Accion> getListadoAcciones() {

		ArrayList<Accion> listadoAcciones = (ArrayList<Accion>) em.createNamedQuery("Accion.findAll").getResultList();

		return listadoAcciones;
	}

	public ArrayList<Rol> getListadoRoles() {

		ArrayList<Rol> listadoRoles = (ArrayList<Rol>) em.createNamedQuery("Rol.findAll").getResultList();

		return listadoRoles;
	}

	public Usuario getUsuarioByName(String username) {
		List<Usuario> lista = Repositorio.getInstance().usuarios().getEm().createNamedQuery("getUsuarioByName")
				.setParameter("unombre", username).getResultList();
		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	@Transactional
	public boolean actualizarUsuario(Usuario user) {

		if (user != null && em.contains(user)) {
			try {
				em.getTransaction().begin();
				for (Accion acc : user.getFuncionalidades())
					em.refresh(acc);
				em.merge(user);
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
	
	@Transactional
	public boolean removeUsuario(Usuario user) {

		if (user != null && em.contains(user)) {
//			try {
				em.getTransaction().begin();
				for (Accion acc : user.getFuncionalidades())
					em.refresh(acc);
				em.remove(user);
				em.getTransaction().commit();
				return true;
//			} catch (Exception ex) {
//				em.getTransaction().rollback();
//				return false;
//			}
		} else {
			return false;
		}
	}

	public boolean deleteUsuario(long l) {
		Usuario user = getUsuarioById(l);
		if (user != null) {
			listaUsuarios.remove(user);
			DB_Sesiones.getInstance().removerSesiones(user.getUsername());
			return true;
		}
		return false;
	}

	public Usuario getUsuarioById(long l) {
		List<Usuario> lista = Repositorio.getInstance().usuarios().getEm().createNamedQuery("getUsuarioById")
				.setParameter("unId", l).getResultList();
		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	public void persistirAccion(Accion accion) {
		// Session session = HibernateUtil.getSessionFactory().openSession();
		//
		// session.saveOrUpdate(accion);
		//
		// session.close();

		Set<Rol> roles = accion.getRoles();
		accion.setRoles(new HashSet<Rol>());
		// Adding manually (using your cascade ALL)
		for (Rol rol : roles) {
			for (Rol rolDB : this.getListadoRoles())
				if (rol != null && rol.getValue().equals(rolDB.getValue())) {
					// accion.setRol(this.getRolByName(rol.getValue())); //
					// already exists
					Rol rolPersisted = em.merge(rol);
					accion.setRol(rolPersisted);
				} else {
					this.persistir(rol);// I don't exist persist
					accion.setRol(rol);
				}
		}
		em.merge(accion);
		// em.persist(accion);//using a similar "don't duplicate me" approach.
		// this.persistir(accion);
	}
}
