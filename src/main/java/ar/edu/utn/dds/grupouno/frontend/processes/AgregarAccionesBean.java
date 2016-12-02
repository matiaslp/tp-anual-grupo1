package ar.edu.utn.dds.grupouno.frontend.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncAgregarAcciones;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@RequestScoped
public class AgregarAccionesBean {

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

	public String ejecutar() throws InstantiationException, ClassNotFoundException, IllegalAccessException, SchedulerException, InterruptedException {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(usuario, token, cantidadReintentos, enviarEmail, filePath);
		return "index";
	}
	
//	public String preparar(){
//		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
//		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
//		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
//		
//		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
//		funcion.prepAgregarAcciones(usuario, token, cantidadReintentos, enviarEmail, filePath);
//		return "index";
//	}

}
