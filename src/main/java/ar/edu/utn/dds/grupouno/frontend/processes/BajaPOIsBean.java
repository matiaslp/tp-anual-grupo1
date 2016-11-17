package ar.edu.utn.dds.grupouno.frontend.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;

import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.DB_Usuarios;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;


@ManagedBean
@RequestScoped
public class BajaPOIsBean {

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

	public String ejecutar() {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

		FuncBajaPOIs funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
		funcion.darDeBajaPOI(usuario, token, cantidadReintentos, enviarEmail, filePath);
		return "index";
	}
	
	public String preparar(){
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		
		FuncBajaPOIs funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
		funcion.prepDarDeBajaPOI(usuario, token, cantidadReintentos, enviarEmail, filePath);
		return "index";
	}
}
