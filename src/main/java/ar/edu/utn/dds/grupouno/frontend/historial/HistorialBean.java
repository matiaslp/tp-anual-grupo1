package ar.edu.utn.dds.grupouno.frontend.historial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.DB_Usuarios;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@RequestScoped
public class HistorialBean {

	private List<RegistroHistorico> listaRH = new ArrayList<RegistroHistorico>();

	String ServicioAPI = "http://trimatek.org/Consultas/";

	private Repositorio repositorio;
	private String textBoxUsuario;
	private DateTime textBoxFechaDesde;
	private DateTime textBoxFechaHasta;
	private RegistroHistorico selectedRH;
	
	public HistorialBean() {

		RegistroHistorico unRegistroHistorico, unRH, registroHistoricoRecuperado;
		POI banco1, local1, banco2, local2;
		ArrayList<POI> listaDePOIs = new ArrayList<POI>();
		DateTime fecha = new DateTime(2016, 02, 02, 0, 0);
		POI_DTO local_dto = new POI_DTO();
		POI_DTO banco_dto = new POI_DTO();
		repositorio = Repositorio.getInstance();
		
		local_dto.setNombre("local1");
		local_dto.setId((long) 11);
		local_dto.setTipo(TiposPOI.LOCAL_COMERCIAL);
		
		banco_dto.setNombre("banco1");
		banco_dto.setId((long) 22);
		banco_dto.setTipo(TiposPOI.BANCO);
		
		local1 = local_dto.converttoPOI();
		banco1 = banco_dto.converttoPOI();

		listaDePOIs.add(local1);
		listaDePOIs.add(banco1);

		unRegistroHistorico = new RegistroHistorico(fecha, 1, "unaStringDeBusqueda", 2, 12, listaDePOIs);

		listaRH.add(unRegistroHistorico);
		
		this.setSelectedRH(unRegistroHistorico);
	}

	public String getTextBoxUsuario() {
		return textBoxUsuario;
	}

	public void setTextBoxUsuarioID(String textBoxUsuarioID) {
		this.textBoxUsuario = textBoxUsuarioID;
	}

	public DateTime getTextBoxFechaDesde() {
		return textBoxFechaDesde;
	}

	public void setTextBoxFechaDesde(DateTime textBoxFechaDesde) {
		this.textBoxFechaDesde = textBoxFechaDesde;
	}

	public DateTime getTextBoxFechaHasta() {
		return textBoxFechaHasta;
	}

	public void setTextBoxFechaHasta(DateTime textBoxFechaHasta) {
		this.textBoxFechaHasta = textBoxFechaHasta;
	}
	
	public List<RegistroHistorico> getListaRH() {
		return listaRH;
	}

	public void setListaRH(List<RegistroHistorico> listaRH) {
		this.listaRH = listaRH;
	}

	public String getServicioAPI() {
		return ServicioAPI;
	}

	public void setServicioAPI(String servicioAPI) {
		ServicioAPI = servicioAPI;
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public RegistroHistorico getSelectedRH() {
		return selectedRH;
	}

	public void setSelectedRH(RegistroHistorico selectedRH) {
		this.selectedRH = selectedRH;
	}
	
	public void buscar() {
		//TODO: BORRAR CUANDO FUNCIONE TODO
//		System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()));
//		System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
		System.out.println(this.getTextBoxUsuario());
		 
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		DB_Usuarios usuarios = repositorio.usuarios();
		
		String username = ((String)sessionMap.get("username"));
		String token = ((String) sessionMap.get("token"));
		Usuario usuario = usuarios.getUsuarioByName(username);
		
		List<Object[]> listaResultados = new ArrayList<Object[]>();
		
		listaRH.clear();
		
		try {
			//TODO: BORRAR CUANDO FUNCIONE TODO
			// println para ver que recibe cada elemento
//			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()));
//			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
			System.out.println(Long.parseLong(this.getTextBoxUsuario()));
			
			DB_HistorialBusquedas db_historial = repositorio.resultadosRegistrosHistoricos();
			
			// QUERY QUE NO ME ANDA
			Long usuarioId = null;
			Date fechaDesde = null;
			Date fechaHasta = null;
			
			if(!this.getTextBoxUsuario().isEmpty()){
				Usuario usuarioBuscado = null;
				if((usuarioBuscado = usuarios.getUsuarioByName(this.getTextBoxUsuario())) != null){
					usuarioId = usuarioBuscado.getId();
				}
			}
			
			if(this.getTextBoxFechaDesde() != null){
				fechaDesde = this.getTextBoxFechaDesde().toDate();
			}
			
			if(this.getTextBoxFechaHasta() != null){
				fechaHasta = this.getTextBoxFechaHasta().toDate();
			}
			
			listaResultados = db_historial.historialBusquedaEntreFechas(usuarioId, fechaDesde, fechaHasta); 

			if (listaResultados != null && listaResultados.size() > 0) {
				for(Object[] resultado : listaResultados){
					if(resultado != null){
						listaRH.add(objectToRegistro(resultado));
					}
				}
			}
			//TODO: BORRAR CUANDO FUNCIONE TODO
			// IMPRIME POR PANTALLA LOS DATOS DE BUSQUEDA Y LO QUE TRAE Y LA LISTA
			System.out.println(listaResultados.size());
			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()));
			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
			System.out.println(Long.parseLong(this.getTextBoxUsuario()));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({"unchecked"})
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
