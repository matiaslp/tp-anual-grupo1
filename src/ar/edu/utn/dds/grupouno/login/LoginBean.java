package ar.edu.utn.dds.grupouno.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
@ManagedBean
public class LoginBean {
	private String usuario;
	private String contrasena;

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
//		String msg = "";
//		Severity severity = FacesMessage.SEVERITY_INFO;
//		if (usuario.equals("dds") && contrasena.equals("dds")) {
//			msg = "Usuario " + usuario + " autorizado";
//			FacesContext.getCurrentInstance().getExternalContext().
//			getSessionMap().put("username", 0);
//		} else {
//			msg = "Usuario no autorizado";
//			severity = FacesMessage.SEVERITY_ERROR;
//		}
//		FacesContext.getCurrentInstance().addMessage(null, 
//				new FacesMessage(severity, msg, null));
		
		String msg = "";
		Severity severity = FacesMessage.SEVERITY_INFO;
		//testTTTTTTTTTTTTTTTTTTT
		UsuariosFactory fact = new UsuariosFactory();
		fact.crearUsuario("admin", "pass", Rol.ADMIN);
		//-------------------------
		//obtenemos usuario
		String token = AuthAPI.getInstance().iniciarSesion(usuario, contrasena);
		if ( token != null) {
			Usuario user = DB_Usuarios.getInstance().getUsuarioByName(usuario);
			msg = "Usuario " + usuario + " autorizado";
			// Iniciamos sesion
			FacesContext.getCurrentInstance().getExternalContext().
			getSessionMap().put("username", user.getID());
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
