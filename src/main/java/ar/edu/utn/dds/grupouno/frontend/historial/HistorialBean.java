package ar.edu.utn.dds.grupouno.frontend.historial;

import java.io.IOException;
import java.util.ArrayList;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

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
	
	private String textoLibre;
	String item;
	
	private List<RegistroHistorico> listaRH = new ArrayList<RegistroHistorico>();
	
	String ServicioAPI = "http://trimatek.org/Consultas/";
	
	Repositorio repositorio;

	RegistroHistorico unRegistroHistorico;
	ArrayList<POI> listaDePOIs;
	POI banco1, local1, banco2, local2;
	DateTime fecha;
	RegistroHistorico unRH;
	POI_DTO local_dto, banco_dto;

	RegistroHistorico registroHistoricoRecuperado;
	
	public HistorialBean() {

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
	
	public String getTextoLibre() {
		return textoLibre;
	}

	public void setTextoLibre(String textoLibre) {
		this.textoLibre = textoLibre;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public RegistroHistorico getUnRegistroHistorico() {
		return unRegistroHistorico;
	}

	public void setUnRegistroHistorico(RegistroHistorico unRegistroHistorico) {
		this.unRegistroHistorico = unRegistroHistorico;
	}

	public ArrayList<POI> getListaDePOIs() {
		return listaDePOIs;
	}

	public void setListaDePOIs(ArrayList<POI> listaDePOIs) {
		this.listaDePOIs = listaDePOIs;
	}

	public POI getBanco1() {
		return banco1;
	}

	public void setBanco1(POI banco1) {
		this.banco1 = banco1;
	}

	public POI getLocal1() {
		return local1;
	}

	public void setLocal1(POI local1) {
		this.local1 = local1;
	}

	public POI getBanco2() {
		return banco2;
	}

	public void setBanco2(POI banco2) {
		this.banco2 = banco2;
	}

	public POI getLocal2() {
		return local2;
	}

	public void setLocal2(POI local2) {
		this.local2 = local2;
	}

	public DateTime getFecha() {
		return fecha;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}

	public RegistroHistorico getUnRH() {
		return unRH;
	}

	public void setUnRH(RegistroHistorico unRH) {
		this.unRH = unRH;
	}

	public POI_DTO getLocal_dto() {
		return local_dto;
	}

	public void setLocal_dto(POI_DTO local_dto) {
		this.local_dto = local_dto;
	}

	public POI_DTO getBanco_dto() {
		return banco_dto;
	}

	public void setBanco_dto(POI_DTO banco_dto) {
		this.banco_dto = banco_dto;
	}

	public RegistroHistorico getRegistroHistoricoRecuperado() {
		return registroHistoricoRecuperado;
	}

	public void setRegistroHistoricoRecuperado(RegistroHistorico registroHistoricoRecuperado) {
		this.registroHistoricoRecuperado = registroHistoricoRecuperado;
	}

	public void filtrar() {

		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		ArrayList<RegistroHistorico> lstRH = null;
		try {
			//lstRH = POI_ABMC.getInstance().buscar(ServicioAPI, textoLibre, usuario.getId());

		} catch (JSONException | IOException | MessagingException e) {
			
			e.printStackTrace();
		}
		if (lstRH != null && lstRH.size() > 0) {

			listaRH.clear();
			
			listaRH.addAll(lstRH);

		}

	}
}
