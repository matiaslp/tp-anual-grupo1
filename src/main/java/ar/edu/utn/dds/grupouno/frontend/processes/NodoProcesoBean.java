package ar.edu.utn.dds.grupouno.frontend.processes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class NodoProcesoBean {
	
	private String cantidadReintentos;
	private String enviarEmail;
	private String filepath;
	private String proceso;
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
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	
	

}
