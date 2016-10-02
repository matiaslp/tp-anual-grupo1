package db;

import java.util.ArrayList;

import autentification.Accion;
import autentification.Usuario;

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
	
	public Usuario getUsuarioById(int terminal){
		return this.listaUsuarios.get(terminal);
	}
}
