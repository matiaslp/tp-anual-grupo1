package autentification;

import java.util.Map;

public class Usuario {

	Rol rol;
	String username;
	String password;
	long id;
	public Map<String,Accion> funcionalidades;
	
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getID() {
		return id;
	}
	public void setID(long unId) {
		id = unId;
	}
	
	
	
	
}
