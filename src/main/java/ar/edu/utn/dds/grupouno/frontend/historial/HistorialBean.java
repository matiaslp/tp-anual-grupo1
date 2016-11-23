package ar.edu.utn.dds.grupouno.frontend.historial;

import java.io.IOException;
import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.json.JSONException;

@ManagedBean
@ApplicationScoped
public class HistorialBean {

	private List<RegistroHistorico> listaRH = new ArrayList<RegistroHistorico>();

	String ServicioAPI = "http://trimatek.org/Consultas/";

	Repositorio repositorio;

	private String textBoxUsuario;
	private DateTime textBoxFechaDesde;
	private DateTime textBoxFechaHasta;

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

	public HistorialBean() {

		RegistroHistorico unRegistroHistorico;
		ArrayList<POI> listaDePOIs;
		POI banco1, local1, banco2, local2;
		DateTime fecha;
		RegistroHistorico unRH;
		POI_DTO local_dto, banco_dto;

		RegistroHistorico registroHistoricoRecuperado;

		repositorio = Repositorio.getInstance();
		listaDePOIs = new ArrayList<POI>();
		local_dto = new POI_DTO();
		banco_dto = new POI_DTO();
		fecha = new DateTime(2016, 02, 02, 0, 0);

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

		repositorio.pois().agregarPOI(local1);
		repositorio.pois().agregarPOI(banco1);
		unRegistroHistorico = new RegistroHistorico(fecha, 1, "unaStringDeBusqueda", 2, 12, listaDePOIs);

		listaRH.add(unRegistroHistorico);
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

	public void buscar() {

		
		System.out.println(MetodosComunes.convertJodatoJava(this.
		 getTextBoxFechaDesde()));
		  System.out.println(MetodosComunes.convertJodatoJava(this.
		  getTextBoxFechaHasta()));
		  System.out.println(this.getTextBoxUsuario());
		 
		listaRH.clear();
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		List<RegistroHistorico> lstRH = null;
		try {

			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()));
			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
			System.out.println(Long.parseLong(this.getTextBoxUsuario()));
			// QUERY QUE NO ME ANDA
			if (this.getTextBoxUsuario() != null
					&& (this.getTextBoxFechaDesde() == null || this.getTextBoxFechaHasta() == null)) {
				lstRH = repositorio.resultadosRegistrosHistoricos().getHistoricobyUserId(
						Repositorio.getInstance().usuarios().getUsuarioByName(this.getTextBoxUsuario()).getId());
			} else {
				if (this.getTextBoxUsuario() == null && this.getTextBoxFechaDesde() != null
						&& this.getTextBoxFechaHasta() != null) {
					lstRH = repositorio.resultadosRegistrosHistoricos().getHistoricobyEntreFechas(
							MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()),
							MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
				} else {
					lstRH = repositorio.resultadosRegistrosHistoricos().getHistoricobyEntreFechasConUserId(
							MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()),
							MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()),
							Repositorio.getInstance().usuarios().getUsuarioByName(this.getTextBoxUsuario()).getId());
				}
			}

			// IMPRIME POR PANTALLA LOS DATOS DE BUSQUEDA Y LO QUE TRAE Y LA
			// LISTA
			System.out.println(lstRH.size());
			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaDesde()));
			System.out.println(MetodosComunes.convertJodatoJava(this.getTextBoxFechaHasta()));
			System.out.println(Long.parseLong(this.getTextBoxUsuario()));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lstRH != null && lstRH.size() > 0) {
			listaRH.addAll(lstRH);

		}
	}
}
