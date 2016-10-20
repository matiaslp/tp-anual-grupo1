package ar.edu.utn.dds.grupouno.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;

@ManagedBean
public class ActualizarLocales {
	
	private int cantidadReintentos = 1;
	private boolean enviarEmail;
	private String filePath;
	
	
	public int getCantidadReintentos() {
		return cantidadReintentos;
	}
	public void setCantidadReintentos(int cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
	}
	public boolean isEnviarEmail() {
		return enviarEmail;
	}
	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void ejecutar(){
		String use = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		System.out.println(use);
		Usuario usuario = DB_Usuarios.getInstance().getUsuarioByName(use);
	}

}
