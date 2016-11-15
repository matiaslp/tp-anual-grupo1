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
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@ManagedBean(name="AccionesDeConsultaBean")
@RequestScoped
public class AccionesDeConsultaBean {
	private List<Accion> accionesParaSeleccionar = new ArrayList<Accion>();
	private List<Accion> accionesSeleccionadas = new ArrayList<Accion>();
	private String accion = null;
	
	@SuppressWarnings("unchecked")
	public AccionesDeConsultaBean() {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		
	
		
		 for (Accion unaAccion:AuthAPI.getInstance().getAcciones()){
			 accionesParaSeleccionar.add(unaAccion);
		 }
    }
	
	public List<Accion> getAccionesParaSeleccionar() {
		return accionesParaSeleccionar;
	}
	public void setAccionesParaSeleccionar(List<Accion> accionesParaSeleccionar) {
		this.accionesParaSeleccionar = accionesParaSeleccionar;
	}
	public List<Accion> getAccionesSeleccionadas() {
		return accionesSeleccionadas;
	}
	public void setAccionesSeleccionadas(List<Accion> accionesSeleccionadas) {
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
	
	public void agregar(Accion accion){
		this.accionesSeleccionadas.add(accion);
	}
}
