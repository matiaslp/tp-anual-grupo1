package ar.edu.utn.dds.grupouno.frontend.historial;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.DB_Usuarios;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@SessionScoped
public class busquedaHistorialBean {
	
	private Repositorio repositorio;
	private String usuario;
	private String fechaDesde;
	private String fechaHasta;
	private RegistroHistorico rhSeleccionado;
	
	private List<RegistroHistorico> listaRH = new ArrayList<RegistroHistorico>();
	private DateFormat sdf = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
	
	public void buscar(){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		DB_Usuarios usuarios = repositorio.usuarios();
		DB_HistorialBusquedas db_historial = repositorio.resultadosRegistrosHistoricos();
		List<Object[]> listaResultados = new ArrayList<Object[]>();
		
		String username = ((String)sessionMap.get("username"));
		String token = ((String) sessionMap.get("token"));
		Usuario usuario = usuarios.getUsuarioByName(username);
		
		listaRH.clear();
		
		try {
			// println para ver que recibe cada elemento
			System.out.println(this.getFechaDesde());
			System.out.println(this.getFechaHasta());
			System.out.println(this.getUsuario());
		
			// QUERY QUE NO ME ANDA
			Long usuarioId = null;
			Date fechaDesde = null;
			Date fechaHasta = null;
			
			//valida el filtro por usuario
			if(!this.getUsuario().isEmpty()){
				Usuario usuarioBuscado = usuarios.getUsuarioByName(this.getUsuario());
				if(usuarioBuscado != null){
					usuarioId = usuarioBuscado.getId();
				}
			}
		
			//valida el filtro por fecha desde
			if(this.getFechaDesde() != null){
				fechaDesde = sdf.parse(this.getFechaDesde());
			}

			//valida el filtro por fecha hasta			
			if(this.getFechaHasta() != null){
				fechaHasta = sdf.parse(this.getFechaHasta());
			}
		
			listaResultados = db_historial.historialBusquedaEntreFechas(usuarioId, fechaDesde, fechaHasta); 
	
			if (listaResultados != null && listaResultados.size() > 0) {
				for(Object[] resultado : listaResultados){
					if(resultado != null){
						listaRH.add(objectToRegistro(resultado));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public busquedaHistorialBean() {
		
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public RegistroHistorico getRhSeleccionado() {
		return rhSeleccionado;
	}

	public void setRhSeleccionado(RegistroHistorico rhSeleccionado) {
		this.rhSeleccionado = rhSeleccionado;
	}

	public List<RegistroHistorico> getListaRH() {
		return listaRH;
	}

	public void setListaRH(List<RegistroHistorico> listaRH) {
		this.listaRH = listaRH;
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	private RegistroHistorico objectToRegistro(Object[] objetoHistorial){
		RegistroHistorico registro = new RegistroHistorico();
		registro.setTime((DateTime)objetoHistorial[0]);
		registro.setUserID((Long)objetoHistorial[1]);
		registro.setBusqueda((String)objetoHistorial[2]);
		registro.setCantResultados((Long)objetoHistorial[3]);
		registro.setTiempoDeConsulta((double)objetoHistorial[4]);
		registro.setListaDePOIs((ArrayList<POI>)objetoHistorial[5]);
		registro.setId((Long)objetoHistorial[6]);
		return registro;
	}
	
}
