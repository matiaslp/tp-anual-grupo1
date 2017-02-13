package ar.edu.utn.dds.grupouno.frontend.login;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@SessionScoped
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
		String msg = "";
		Severity severity = FacesMessage.SEVERITY_INFO;
		// testTTTTTTTTTTTTTTTTTTT
		UsuariosFactory fact = new UsuariosFactory();
		Usuario admin = fact.crearUsuario("admin", "pass", "ADMIN");
		Usuario terminal = fact.crearUsuario("terminal", "pass", "TERMINAL");
		if (admin != null) {
			AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
			AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
			AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
			AuthAPI.getInstance().agregarFuncionalidad("busquedaPOI", admin);
			AuthAPI.getInstance().agregarFuncionalidad("auditoria", admin);
			AuthAPI.getInstance().agregarFuncionalidad("generarLog", admin);
			AuthAPI.getInstance().agregarFuncionalidad("cambiarEstadoMail", admin);
			AuthAPI.getInstance().agregarFuncionalidad("notificarBusquedaLarga", admin);
			AuthAPI.getInstance().agregarFuncionalidad("procesoMultiple", admin);
			AuthAPI.getInstance().agregarFuncionalidad("obtenerInfoPOI", admin);
			AuthAPI.getInstance().agregarFuncionalidad("reporteBusquedaPorUsuario", admin);
			AuthAPI.getInstance().agregarFuncionalidad("reporteBusquedasPorFecha", admin);
			AuthAPI.getInstance().agregarFuncionalidad("reportecantidadResultadosPorTerminal", admin);
		}

		if ((Repositorio.getInstance().usuarios().getUsuarioByName("admin")) == null) {
			Repositorio.getInstance().usuarios().persistir(admin);
		}
		if ((Repositorio.getInstance().usuarios().getUsuarioByName("terminal")) == null) {
			Repositorio.getInstance().usuarios().persistir(terminal);
		}
		// -------------------------

		// obtenemos usuario

		String token = AuthAPI.getInstance().iniciarSesion(usuario, contrasena);
		if (token != null) {
			Usuario user = Repositorio.getInstance().usuarios().getUsuarioByName(usuario);
			msg = "Usuario " + usuario + " autorizado";
			// Iniciamos sesion
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", user.getUsername());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);
			return "success";
			// error de logueo
		} else {
			msg = "Usuario no autorizado";
			severity = FacesMessage.SEVERITY_ERROR;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
		return "failure";
	}

	public void cancelar() {
		setUsuario(null);
		setContrasena(null);
	}

	public void logout() {
		
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		
		AuthAPI.getInstance().cerrarSesion(usuario.getUsername(), token);
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/tp-anual/faces/login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
