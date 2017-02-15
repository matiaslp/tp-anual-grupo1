package ar.edu.utn.dds.grupouno.frontend.processes;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.abmc.poi.NodoServicio;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncMultiple;
import ar.edu.utn.dds.grupouno.frontend.abmPOIs.Dias;
import ar.edu.utn.dds.grupouno.procesos.ActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.procesos.AgregarAcciones;
import ar.edu.utn.dds.grupouno.procesos.BajaPOIs;
import ar.edu.utn.dds.grupouno.procesos.NodoProceso;
import ar.edu.utn.dds.grupouno.procesos.Proceso;
import ar.edu.utn.dds.grupouno.procesos.ProcesoHandler;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@RequestScoped
public class ProcesoMultipleBean {

	private String cantidadReintentos;
	private String enviarEmail;
	private String filepath;
	private String proceso = "Actualizar Locales Comerciales";
	private List<NodoProcesoBean> listProc = new ArrayList<NodoProcesoBean>();
	private NodoProcesoBean nodoProcesoCreando = new NodoProcesoBean();
	private List<String> procesos = new ArrayList<String>();
	
	public ProcesoMultipleBean(){
		
		procesos.add("Actualizar Locales Comerciales");
		procesos.add("Baja de Pois");
		procesos.add("Agregar Acciones");
		
	}

	public void listener() {
		
	}

	public List<String> getProcesos() {
		return procesos;
	}



	public void setProcesos(List<String> procesos) {
		this.procesos = procesos;
	}



	public String getCantidadReintentos() {
		return cantidadReintentos;
	}



	public void setCantidadReintentos(String cantidadReintentos) {
		this.cantidadReintentos = cantidadReintentos;
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


	public String getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(String enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	
	
	// Agregar un Servicio a la lista
	public List<NodoProcesoBean> getListProc() {
		return listProc;
	}

	public void setListProc(List<NodoProcesoBean> listProc) {
		this.listProc = listProc;
	}

	public NodoProcesoBean getNodoProcesoCreando() {
		return nodoProcesoCreando;
	}

	public void setNodoProcesoCreando(NodoProcesoBean nodoProcesoCreando) {
		this.nodoProcesoCreando = nodoProcesoCreando;
	}

	public void agregarProceso() {
		
		this.nodoProcesoCreando.setCantidadReintentos(this.cantidadReintentos);
		this.nodoProcesoCreando.setEnviarEmail(this.enviarEmail);
		this.nodoProcesoCreando.setFilepath(this.filepath);
		this.nodoProcesoCreando.setProceso(this.proceso);
		this.listProc.add(this.nodoProcesoCreando);
		
		nodoProcesoCreando = new NodoProcesoBean();
		this.filepath = "";
		this.proceso = "Actualizar Locales Comerciales";
		this.enviarEmail = "";
		this.cantidadReintentos = "";
	}

	// Eliminar servicio de la lista de servicios a crear junto con el POI
	public void removeProceso(NodoProcesoBean proc) {
		try {
			this.listProc.remove(proc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String ejecutar() {
		
		
		List<NodoProceso> lstProcesos = new ArrayList<NodoProceso>();
		for (int i = 0; i < this.listProc.size()-2; i++){
			NodoProcesoBean nodoBean = this.listProc.get(i);
			if (nodoBean.getProceso().equals("Actualizar Locales Comerciales")){
			ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales();
			NodoProceso e = new NodoProceso(proceso, nodoBean.getFilepath(), Integer.parseInt(nodoBean.getCantidadReintentos()),
					Boolean.parseBoolean(nodoBean.getEnviarEmail()));
			lstProcesos.add(e);
			} else if (nodoBean.getProceso().equals("Agregar Acciones")){
			AgregarAcciones proceso = new AgregarAcciones();
			NodoProceso e = new NodoProceso(proceso, nodoBean.getFilepath(), Integer.parseInt(nodoBean.getCantidadReintentos()),
					Boolean.parseBoolean(nodoBean.getEnviarEmail()));
			lstProcesos.add(e);
			} else if (nodoBean.getProceso().equals("Baja de Pois")){
			BajaPOIs proceso = new BajaPOIs();
			NodoProceso e = new NodoProceso(proceso, nodoBean.getFilepath(), Integer.parseInt(nodoBean.getCantidadReintentos()),
					Boolean.parseBoolean(nodoBean.getEnviarEmail()));
			lstProcesos.add(e);
			}
		}
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

		FuncMultiple funcion = (FuncMultiple) AuthAPI.getInstance()
				.getAccion("procesoMultiple");
		Proceso proc = null;
		NodoProcesoBean nodoBean = this.listProc.get(this.listProc.size()-1);
		if (nodoBean.getProceso().equals("Actualizar Locales Comerciales")){
		proc = new ActualizacionLocalesComerciales();
		} else if (nodoBean.getProceso().equals("Agregar Acciones")){
		proc = new AgregarAcciones();
		} else if (nodoBean.getProceso().equals("Baja de Pois")){
		proc = new BajaPOIs();
		}
		try {
			Scheduler scheduler = ProcesoHandler.ejecutarProceso(usuario, proc, nodoBean.getFilepath(),Boolean.parseBoolean(nodoBean.getEnviarEmail()),
					Integer.parseInt(nodoBean.getCantidadReintentos()),lstProcesos);
		} catch (NumberFormatException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| SchedulerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "welcome";
	}

}
