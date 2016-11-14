package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.PersistibleConNombre;

@Entity
@Table(name = "Usuario")
@NamedQueries({
@NamedQuery(name = "getUsuarioByName", query = "SELECT u FROM Usuario u WHERE u.username LIKE :unombre"),
@NamedQuery(name = "getUsuarioById", query = "SELECT u FROM Usuario u WHERE u.id LIKE :unId"),
@NamedQuery(name = "getAllUsers", query = "SELECT u FROM Usuario u"),
@NamedQuery(name = "updateUsername", query = "UPDATE Usuario SET username = :username where id = :id")})
public class Usuario extends PersistibleConNombre{
	
//	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH})
	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn (name = "Rol", nullable = false)
	private Rol rol;
	private String username;
	private String password;
//	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH})
	@ManyToMany(cascade = { CascadeType.ALL})
	@JoinTable(name="USUARIO_FUNCIONALIDAD", 
		joinColumns={@JoinColumn(name="user_id")}, 
		inverseJoinColumns={@JoinColumn(name="func_id")})
	private Set<Accion> funcionalidades  = new HashSet<Accion>();;
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
		this.rol = AuthAPI.getInstance().getRol(rol.getValue());
	}
	
	public void setFuncionalidad (Accion acc){
		this.funcionalidades.add(acc);
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

	public Set<Accion> getFuncionalidades() {
		return funcionalidades;
	}

	public List<Accion> getProceses() {
		List<Accion> resultado = new ArrayList<Accion>();

		for (Accion accion : funcionalidades) {
			if (accion.isProcess())
				resultado.add(accion);
		}
		return resultado;
	}

	public void setFuncionalidades(Set<Accion> funcionalidades) {
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
	
	public void agregarFuncionalidad(Accion func){
		funcionalidades.add(func);
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


	public Object getFuncionalidad(String funcionalidad) {
		for(Accion accion : this.funcionalidades){
			if(accion.getNombreFuncion().equals(funcionalidad)){
				return accion;
			}
		}
		return null;
	}


}
