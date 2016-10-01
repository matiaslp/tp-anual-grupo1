package autentification;

import java.util.HashMap;
import java.util.Map;

import db.DB_Usuarios;

public class Usuario {

	private Rol rol;
	private String username;
	private String password;
	private long id;
	private Map<String, Accion> funcionalidades;
	private String correo;
	
	public Usuario(String username, String password, Rol rol) {
		this.setID(DB_Usuarios.getInstance().getListaUsuarios().size() + 1);
		this.setPassword(password);
		this.setUsername(username);
		this.setRol(rol);
				
		if(rol.equals(Rol.ADMIN)){
			funcionalidades = AuthAPI.Acciones;
		}else{
			funcionalidades = new HashMap<String,Accion>();
			funcionalidades.put("busquedaPOI", AuthAPI.Acciones.get("busquedaPOI"));
			funcionalidades.put("obtenerInfoPOI", AuthAPI.Acciones.get("obtenerInfoPOI"));
		}
		
	//AGREGADO POR LUCAS
		DB_Usuarios.getInstance().agregarUsuarioALista(this);
	}
	

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

	public Map<String, Accion> getProceses() {
		Map<String, Accion> resultado = new HashMap<String, Accion>();

		for (Map.Entry<String, Accion> accion : funcionalidades.entrySet()) {
			if (accion.getValue().isProcess())
				resultado.put(accion.getKey(), accion.getValue());
		}
		return resultado;
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

	public boolean chequearFuncionalidad(String funcionalidad) {
		if (this.getFuncionalidades().get(funcionalidad) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validarUsuarioYPass(String user, String pass) {
		if (user.equals(this.username) && pass.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean agregarFuncionalidad(String funcionalidad){
		Accion func = AuthAPI.getInstance().getAccion(funcionalidad);
		if(func !=null){ //existe
			funcionalidades.put(funcionalidad, func);
			return true;
		}else{
			return false; //no existe
		}
	}

	public Accion getFuncionalidad(String funcionalidad){
		return funcionalidades.get(funcionalidad);
	}
	
	


}
