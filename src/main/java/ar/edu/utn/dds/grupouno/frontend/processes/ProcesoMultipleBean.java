package ar.edu.utn.dds.grupouno.frontend.processes;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncMultiple;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@RequestScoped
public class ProcesoMultipleBean {

	private int cantidadReintentos;
	private String enviarEmail;
	private List<Proceso> listProc = new ArrayList<Proceso>();

	public int getCantidadReintentos() {
		return cantidadReintentos;
	}

	public void setCantidadReintentos(int cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
	}

	public String getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(String enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public List<Proceso> getListProc() {
		return listProc;
	}

	public void setListProc(List<Proceso> listProc) {
		this.listProc = listProc;
	}

	public String ejecutar() {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

		FuncMultiple funcion = (FuncMultiple) AuthAPI.getInstance()
				.getAccion("procesoMultiple");
		funcion.procesoMultiple(usuario, token, cantidadReintentos, Boolean.parseBoolean(enviarEmail), (ArrayList)listProc);
		return "index";
	}

}
