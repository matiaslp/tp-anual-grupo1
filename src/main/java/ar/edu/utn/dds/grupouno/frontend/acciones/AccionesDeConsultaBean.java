package ar.edu.utn.dds.grupouno.frontend.acciones;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean(name = "AccionesDeConsultaBean")
@SessionScoped
public class AccionesDeConsultaBean {
	private List<Accion> accionesParaSeleccionar = new ArrayList<Accion>();
	private List<Accion> accionesSeleccionadas = new ArrayList<Accion>();
	private List<String> nombresAcciones = new ArrayList<String>();
	private Usuario usuario = null;
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private String username = null;
	private String accion = null;

	@SuppressWarnings("unchecked")
	public AccionesDeConsultaBean() {
		//String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
		//String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		//Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

		for (Accion unaAccion:AuthAPI.getInstance().getAcciones()){
			accionesParaSeleccionar.add(unaAccion);
		}

		for (Accion unaAccion : accionesParaSeleccionar){
			nombresAcciones.add(unaAccion.getNombreFuncion());
		}
	}

	public List<String> getNombresAcciones() {
		return nombresAcciones;
	}

	public void setNombresAcciones(List<String> nombresAcciones) {
		this.nombresAcciones = nombresAcciones;
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

	public void confirmar() {
		Iterator<Accion> iter = this.usuario.getFuncionalidades().iterator();
		ArrayList<Accion> accionesAEliminar =new ArrayList<Accion>(); //no me lo deja eliminar mientras itera, por eso lo hice asi--Maxi.
		while(iter.hasNext()){
				Accion unaAccion = iter.next();
				if(unaAccion !=null && !this.accionesSeleccionadas.contains(unaAccion)){
					accionesAEliminar.add(unaAccion);
				}
		}
		
		for(Accion accionAEliminar : accionesAEliminar){
			this.usuario.getFuncionalidades().remove(accionAEliminar);
		}

		for(Accion otraAccion : this.accionesSeleccionadas){
			if(!this.usuario.getFuncionalidades().contains(otraAccion)){
				this.usuario.getFuncionalidades().add(otraAccion);
			}
		}
		Repositorio.getInstance().usuarios().persistirUsuario(this.usuario);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('funcionesAgregadas').show();");
	}

	public void eliminar(Accion accion) {
		this.accionesSeleccionadas.remove(accion);
	}

	public String cancelar() {
		return "cancel";
	}

	public void agregar() {
		for(Accion unaAccion : accionesParaSeleccionar){
			if(unaAccion.getNombreFuncion().equals(this.accion) && !this.accionesSeleccionadas.contains(unaAccion)){
				if(unaAccion.getRoles().contains(usuario.getRol())){
					this.accionesSeleccionadas.add(unaAccion);
					break;
				}else{
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('permisos').show();");
					break;
				}
			}
		}
	}

	public void cargarUsuario(){
		this.usuario = Repositorio.getInstance().usuarios().getUsuarioByName(this.getUsername());
		Iterator<Accion> iterFunc = this.usuario.getFuncionalidades().iterator();
		accionesSeleccionadas.clear();
		while(iterFunc.hasNext()){
			Accion unaAccion =iterFunc.next();
			if( unaAccion != null){
				accionesSeleccionadas.add(unaAccion);
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void reset(){
		
	}

}
