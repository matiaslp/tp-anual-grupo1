package ar.edu.utn.dds.grupouno.frontend.reportes;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@RequestScoped
public class reportecantidadResultadosPorTerminalBean {
	private ArrayList<Object[]> resultReporte = new ArrayList<Object[]>();
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

	public DB_HistorialBusquedas getHistorial() {
		return historial;
	}

	public void setHistorial(DB_HistorialBusquedas historial) {
		this.historial = historial;
	}

	public FuncReporteCantidadResultadosPorTerminal getReporte() {
		return reporte;
	}

	public void setReporte(FuncReporteCantidadResultadosPorTerminal reporte) {
		this.reporte = reporte;
	}

	private Object[] unaFila;
	private DB_HistorialBusquedas historial;
	private FuncReporteCantidadResultadosPorTerminal reporte= new FuncReporteCantidadResultadosPorTerminal();
	
	public reportecantidadResultadosPorTerminalBean(){
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		
		
		if(usuario!=null){
		reporte =(FuncReporteCantidadResultadosPorTerminal) usuario.getFuncionalidad("reportecantidadResultadosPorTerminal");
		
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
}
