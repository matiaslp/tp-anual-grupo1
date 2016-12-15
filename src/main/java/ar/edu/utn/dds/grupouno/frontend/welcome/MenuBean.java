package ar.edu.utn.dds.grupouno.frontend.welcome;

import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.autentification.Accion;

@ManagedBean
@RequestScoped
public class MenuBean {
	
	private String username;
	private String token;
	private Usuario usuario;
	private  Set<Accion> funcionalidades;

	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	


	public Set<Accion> getFuncionalidades() {
		return funcionalidades;
	}


	public void setFuncionalidades(Set<Accion> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	
	public boolean isFuncionalidad(String funcionalidad) {
		for (Accion accion : this.funcionalidades) {
			if (accion.getNombreFuncion().equals(funcionalidad)) {
				return true;
			}
		}
		return false;
	}


	public MenuBean(){
		username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		funcionalidades = usuario.getFuncionalidades();
	}
	
}
