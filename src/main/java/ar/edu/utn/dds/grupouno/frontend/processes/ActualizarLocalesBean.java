package ar.edu.utn.dds.grupouno.frontend.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;


@ManagedBean
@RequestScoped
public class ActualizarLocalesBean {

	private String cantidadReintentos;
	private String enviarEmail;
	private String filePath;

	public String getCantidadReintentos() {
		return cantidadReintentos;
	}

	public void setCantidadReintentos(String cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
	}

	public String getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(String enviarEmail) {
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

		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance()
				.getAccion("actualizacionLocalesComerciales");
		funcion.actualizarLocales(usuario, token, Integer.parseInt(cantidadReintentos), Boolean.parseBoolean(enviarEmail), filePath);
		return "welcome";
	}

}
