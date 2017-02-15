package ar.edu.utn.dds.grupouno.frontend.reportes;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mongodb.morphia.converters.DateConverter;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.resultadoBusquedaDTO;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.repositorio.CantBusquedasDate;

@ManagedBean
@RequestScoped
public class reporteBusquedaPorFechaBean {
	private ArrayList<Object[]> resultReporte = new ArrayList<Object[]>();
	private Object[] unaFila;
	private DB_HistorialBusquedas historial;
	private FuncReporteCantidadResultadosPorTerminal reporte= new FuncReporteCantidadResultadosPorTerminal();
	
	public reporteBusquedaPorFechaBean(){
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		
		
		if(usuario!=null){
		reporte =(FuncReporteCantidadResultadosPorTerminal) usuario.getFuncionalidad("reporteBusquedasPorFecha");
		
		for(Object[] unResultReport:reporte.obtenerCantidadResultadosPorTerminal(usuario, token, usuario.getId())){
			
		System.out.println(unResultReport.toString());
		}
		/*for (cantBusquedasDate nodo : recuperado) {
			Object[] objeto = new Object[2];
			DateConverter conversor = new DateConverter();
			objeto[0] = (Object) nodo.get_id();
			objeto[1] = (Object) nodo.getCantResultados();

			resultado.add(objeto);
		}	*/
		
		}
	}
	
	public void CargarReporte(){
		
	}
	
	public ArrayList<Object[]> getResultReporte() {
		return resultReporte;
	}
	public void setResultReporte(ArrayList<Object[]> resultReporte) {
		this.resultReporte = resultReporte;
	}
	public Object[] getUnaFila() {
		return unaFila;
	}
	public void setUnaFila(Object[] unaFila) {
		this.unaFila = unaFila;
	}

	public FuncReporteCantidadResultadosPorTerminal getReporte() {
		return reporte;
	}

	public void setReporte(FuncReporteCantidadResultadosPorTerminal reporte) {
		this.reporte = reporte;
	}

}
