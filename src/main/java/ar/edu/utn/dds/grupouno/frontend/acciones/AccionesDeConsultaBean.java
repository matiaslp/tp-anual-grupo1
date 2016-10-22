package ar.edu.utn.dds.grupouno.frontend.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;

@ManagedBean(name="AccionesDeConsultaBean")
@RequestScoped
public class AccionesDeConsultaBean {
	private List<String> accionesParaSeleccionar = new ArrayList<String>();
	private List<String> accionesSeleccionadas = new ArrayList<String>();
	private String accion = null;
	
	@SuppressWarnings("unchecked")
	public AccionesDeConsultaBean() {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = DB_Usuarios.getInstance().getUsuarioByName(username);
		accionesParaSeleccionar = (List<String>) AuthAPI.getInstance().getAcciones().keySet();
    }
	
	public List<String> getAccionesParaSeleccionar() {
		return accionesParaSeleccionar;
	}
	public void setAccionesParaSeleccionar(List<String> accionesParaSeleccionar) {
		this.accionesParaSeleccionar = accionesParaSeleccionar;
	}
	public List<String> getAccionesSeleccionadas() {
		return accionesSeleccionadas;
	}
	public void setAccionesSeleccionadas(List<String> accionesSeleccionadas) {
		this.accionesSeleccionadas = accionesSeleccionadas;
	}
	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public void confirmar(){
		// agrega las acciones al usuario.
	}
	
	public void eliminar(Accion accion){
		this.accionesSeleccionadas.remove(accion);
	}
	
	public String cancelar(){
		return "cancel";
	}
	
	public void agregar(String accion){
		this.accionesSeleccionadas.add(accion);
	}
}
