package com.tutorial;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

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

	public void login() {
		String msg = "";
		Severity severity = FacesMessage.SEVERITY_INFO;
		if (usuario.equals("dds") && contrasena.equals("dds")) {
			msg = "Usuario " + usuario + " autorizado";			
		} else {
			msg = "Usuario no autorizado";
			severity = FacesMessage.SEVERITY_ERROR;
		}
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, msg, null));
	}
	
	public void cancelar(){
		setUsuario(null);
		setContrasena(null);
	}
}
