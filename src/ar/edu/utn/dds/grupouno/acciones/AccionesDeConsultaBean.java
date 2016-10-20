package ar.edu.utn.dds.grupouno.acciones;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;

@ManagedBean(name="AccionesDeConsultaBean")
@ViewScoped
public class AccionesDeConsultaBean {
	private Set<String> accionesParaSeleccionar = new HashSet<String>();
	private Set<String> accionesSeleccionadas = new HashSet<String>();
	private String accion = null;
	
	@PostConstruct
    public void init() {
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = DB_Usuarios.getInstance().getUsuarioByName(username);

		accionesParaSeleccionar = AuthAPI.getInstance().getAcciones().keySet();
    }
	
	public Set<String> getAccionesParaSeleccionar() {
		return accionesParaSeleccionar;
	}
	public void setAccionesParaSeleccionar(Set<String> accionesParaSeleccionar) {
		this.accionesParaSeleccionar = accionesParaSeleccionar;
	}
	public Set<String> getAccionesSeleccionadas() {
		return accionesSeleccionadas;
	}
	public void setAccionesSeleccionadas(Set<String> accionesSeleccionadas) {
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
