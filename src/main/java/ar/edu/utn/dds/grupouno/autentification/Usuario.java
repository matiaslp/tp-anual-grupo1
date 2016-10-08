package ar.edu.utn.dds.grupouno.autentification;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

	private Rol rol;
	private String username;
	private String password;
	private long id;
	private Map<String, Accion> funcionalidades;
	private String correo;
	private boolean mailHabilitado;
	private boolean notificacionesActivadas;
	private boolean auditoriaActivada;

	public Usuario() {
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

	public boolean validarUsuarioYPass(String user, String pass) {
		if (user.equals(this.username) && pass.equals(this.password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void agregarFuncionalidad(String funcionalidad, Accion func){
		funcionalidades.put(funcionalidad, func);
	}

	public Accion getFuncionalidad(String funcionalidad){
		return funcionalidades.get(funcionalidad);
	}
	
	public boolean isMailHabilitado() {
		return mailHabilitado;
	}


	public boolean setMailHabilitado(boolean mailHabilitado) {
		this.mailHabilitado = mailHabilitado;
		return true;
	}


	public boolean isNotificacionesActivadas() {
		return notificacionesActivadas;
	}


	public void setNotificacionesActivadas(boolean notificacionesActivadas) {
		this.notificacionesActivadas = notificacionesActivadas;
	}


	public boolean isAuditoriaActivada() {
		return auditoriaActivada;
	}


	public void setAuditoriaActivada(boolean auditoriaActivada) {
		this.auditoriaActivada = auditoriaActivada;
	}


}
