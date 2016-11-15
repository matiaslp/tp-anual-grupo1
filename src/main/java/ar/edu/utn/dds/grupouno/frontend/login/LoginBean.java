package ar.edu.utn.dds.grupouno.frontend.login;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.db.RegistroHistorico;

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@ManagedBean
@SessionScoped
public class LoginBean {
	private String usuario;
	private String contrasena;
	
	//creacion de registros historicos terminal 
	private DB_HistorialBusquedas historial;
	//..
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String login() {	
		String msg = "";
		Severity severity = FacesMessage.SEVERITY_INFO;
		//testTTTTTTTTTTTTTTTTTTT
		UsuariosFactory fact = new UsuariosFactory();
		fact.crearUsuario("admin", "pass", "ADMIN");
		fact.crearUsuario("terminal", "pass", "TERMINAL");
		//-------------------------
		
		//creacion de registros historicos terminal 
		
		//historial = DB_HistorialBusquedas.getInstance();
		/*DateTime time = new DateTime(2016, 1, 1, 1, 1);
		RegistroHistorico registro = new RegistroHistorico(0, time
		, DB_Usuarios.getInstance().getUsuarioByName("terminal").getID(), "busqueda1", 10, 5);
		
		historial.agregarHistorialBusqueda(registro);
		//..*/
		
		//obtenemos usuario
		String token = AuthAPI.getInstance().iniciarSesion(usuario, contrasena);
		if ( token != null) {
			Usuario user = Repositorio.getInstance().usuarios().getUsuarioByName(usuario);
			msg = "Usuario " + usuario + " autorizado";
			// Iniciamos sesion
			FacesContext.getCurrentInstance().getExternalContext().
			getSessionMap().put("username", user.getUsername());	
			FacesContext.getCurrentInstance().getExternalContext().
			getSessionMap().put("token", token);	
			return "success";
			// error de logueo
		} else {
			msg = "Usuario no autorizado";
			severity = FacesMessage.SEVERITY_ERROR;
		}
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, msg, null));
		return "failure";
	}
	
	public void cancelar(){
		setUsuario(null);
		setContrasena(null);
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
}
