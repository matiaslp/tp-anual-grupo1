package ar.edu.utn.dds.grupouno.db;

import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Usuario;

public class DB_Usuarios extends Accion {

	private ArrayList<Usuario> listaUsuarios;

	private static DB_Usuarios instance = null;

	public DB_Usuarios() {
		setListaUsuarios(new ArrayList<Usuario>());
	}

	public static DB_Usuarios getInstance() {
		if (instance == null) {
			instance = new DB_Usuarios();
		}
		return instance;
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
	

	public boolean agregarUsuarioALista(Usuario user) {
		for (Usuario usuario : getListaUsuarios()) {
			if (user.getUsername().equals(usuario.getUsername()) || user.getID() == usuario.getID()) {
				return false;
			}
		}
		getListaUsuarios().add(user);
		return true;
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioByName(String username) {
		
		Usuario usuarioNoEncontrado = null;
		for (Usuario unUsuario : this.getListaUsuarios()) {
			if (unUsuario.getUsername().equals(username)) {
			return unUsuario;
			}
		}
		return usuarioNoEncontrado;

	}
	
	public Usuario getUsuarioById(long l){
		for ( Usuario user : listaUsuarios ) {
			if (user.getID() == l)
				return user;
		}
		return null;
	}
}
