package ar.edu.utn.dds.grupouno.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;

@ManagedBean
@RequestScoped
public class ActualizarLocalesBean {
	
	private int cantidadReintentos;
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
	
	public String ejecutar(){
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = DB_Usuarios.getInstance().getUsuarioByName(username);
		
		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance().getAccion("actualizacionLocalesComerciales");
		funcion.actualizarLocales(usuario, token, cantidadReintentos, enviarEmail, filePath);
		return "index";
	}

}

