package ar.edu.utn.dds.grupouno.autentification;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.PersistibleConNombre;

@Entity
@Table(name = "Usuario")
public class Usuario extends PersistibleConNombre{
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn (name = "Rol", referencedColumnName="id")
	private Rol rol;
	private String username;
	private String password;
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="USUARIO_FUNCIONALIDAD", 
		joinColumns={@JoinColumn(name="user_id")}, 
		inverseJoinColumns={@JoinColumn(name="func_id")})
	@MapKeyColumn(name= "func_nombre")
	private Map<String, Accion> funcionalidades;
	private String correo;
	private boolean mailHabilitado;
	private boolean notificacionesActivadas;
	private boolean auditoriaActivada;
	private boolean log = true;

	public Usuario() {
	}
	

	public boolean isLog() {
		return log;
	}


	public boolean setLog(boolean log) {
		this.log = log;
		return true;
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
