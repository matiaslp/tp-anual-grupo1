package ar.edu.utn.dds.grupouno.frontend.reportes;

import java.util.ArrayList;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;

@ManagedBean(name="dtViewReporteCantidadResultadosPorTerminal")
@ViewScoped
public class DtViewReporteCantidadResultadosPorTerminal implements Serializable {
	  /**
	 * 
	 */
/*	private static final long serialVersionUID = 1L;
	private Map<Long, Long> resultado;
	private List<resultado> resultados;
	private DB_HistorialBusquedas historial;
	     
	    @ManagedProperty("#{carService}")
	    private CarService service;
	 
	    @PostConstruct
	    public void init() {
	    	historial = DB_HistorialBusquedas.getInstance();
	    	resultado = historial.reporteCantidadResultadosPorTerminal(10);;
	    }
	     
	    public List<Car> getCars() {
	        return cars;
	    }
	 
	    public void setService(CarService service) {
	        this.service = service;
	    }*/
}
