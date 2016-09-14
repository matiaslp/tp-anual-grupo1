package autentification;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

	private Rol rol;
	private String username;
	private String password;
	private long id;
	private Map<String, Accion> funcionalidades = new HashMap<String, Accion>();
	private String correo;

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

	public Map<String, Accion> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(Map<String, Accion> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
