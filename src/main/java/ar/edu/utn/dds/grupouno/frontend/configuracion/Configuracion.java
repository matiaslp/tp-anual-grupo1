package ar.edu.utn.dds.grupouno.frontend.configuracion;

import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ViewScoped
public class Configuracion {

	private String username;
	private String token;
	private Usuario usuario;
	private Set<Accion> funcionalidades;
	private String logueo;
	private String mailHabilitado;

	public Configuracion() {
		username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		funcionalidades = usuario.getFuncionalidades();
		logueo = Boolean.toString(usuario.isLog());
		mailHabilitado = Boolean.toString(usuario.isMailHabilitado());
	}

	public boolean isFuncionalidad(String funcionalidad) {
		for (Accion accion : this.funcionalidades) {
			if (accion.getNombreFuncion().equals(funcionalidad)) {
				return true;
			}
		}
		return false;
	}
	
	public void guardar(){
		usuario.setLog(Boolean.parseBoolean(logueo));
		usuario.setMailHabilitado(Boolean.parseBoolean(mailHabilitado));
		if (Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(usuario)){
			// Mostrar pantalla de configuracion exitosa
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('configGuardadaDialog').show();");
			
		} else {
			// Mostrar pantalla de configuracion erronea
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('configError').show();");
		}
			
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getLogueo() {
		return logueo;
	}

	public void setLogueo(String logueo) {
		this.logueo = logueo;
	}

	public String getMailHabilitado() {
		return mailHabilitado;
	}

	public void setMailHabilitado(String mailHabilitado) {
		this.mailHabilitado = mailHabilitado;
	}

}
