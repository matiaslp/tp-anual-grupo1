package ar.edu.utn.dds.grupouno.frontend.busqueda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.NodoServicioDTO;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.resultadoBusquedaDTO;
import ar.edu.utn.dds.grupouno.abmc.poi.NodoServicio;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ApplicationScoped
public class BusquedaBean {
	private String textoLibre;
	private String textoBuscar;

	private List<resultadoBusquedaDTO> pois = new ArrayList<resultadoBusquedaDTO>();
	private resultadoBusquedaDTO selectedPoi;
	String ServicioAPI = "http://trimatek.org/Consultas/";

	private List<Item> items;

	public BusquedaBean() {
		items = new ArrayList<Item>();
		items.add(new Item());
	}

	public String getTextoLibre() {
		return textoLibre;
	}

	public List<resultadoBusquedaDTO> getPois() {
		return pois;

	}

	public void setPois(List<resultadoBusquedaDTO> pois) {
		this.pois = pois;
	}

	public void setTextoLibre(String textoLibre) {
		this.textoLibre = textoLibre;
	}

	public resultadoBusquedaDTO getSelectedPoi() {
		return selectedPoi;
	}

	public void setSelectedPoi(resultadoBusquedaDTO selectedPoi) {
		this.selectedPoi = selectedPoi;
	}

	public void buscar() {
		pois.clear();
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		ArrayList<POI> lstPOI = null;
		try {
			textoBuscar = new String();

			for (Item unText : items) {
				textoBuscar = textoBuscar + " " + unText.getValue();

			}

			lstPOI = POI_ABMC.getInstance().buscar(ServicioAPI, textoBuscar, usuario.getId());

		} catch (JSONException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lstPOI != null && lstPOI.size() > 0) {
			pois = listToDTO(lstPOI);
		}

	}

	private List<resultadoBusquedaDTO> listToDTO(ArrayList<POI> lstPOI) {
		List<resultadoBusquedaDTO> resultados = new ArrayList<resultadoBusquedaDTO>();
		if (lstPOI.size() > 0) {
			for (POI point : lstPOI) {
				resultados.add(toDTO(point));
			}
		}
		return resultados;
	}

	@SuppressWarnings("null")
	private resultadoBusquedaDTO toDTO(POI point) {
		resultadoBusquedaDTO resultado = new resultadoBusquedaDTO();

		resultado.setTipo(point.getTipo());

		if (point.getBarrio() == null) {
			if (point.getDireccion() == null) {
				resultado.setDireccion(null);
			} else {
				resultado.setDireccion(point.getDireccion());
			}
		} else {
			if (point.getDireccion() == null) {
				resultado.setDireccion(point.getBarrio());
			} else {
				resultado.setDireccion(point.getBarrio() + ", " + point.getDireccion());
			}
		}


		if (point.getNombre() != null) {
			resultado.setNombre(point.getNombre());
		}

		if (point.getTipo().equals(TiposPOI.PARADA_COLECTIVO)) {
			resultado.setLinea(((ParadaColectivo) point).getLinea());
		}

		List<NodoServicio> servicios = point.getServicios();
		if (servicios != null && servicios.size() > 0) {
			for (int i = 0; i < servicios.size(); i++) {
				resultado.addServicio(toDTO(servicios.get(i)));
			}
		}
		return resultado;
	}

	private NodoServicioDTO toDTO(NodoServicio servicio) {
		NodoServicioDTO dto = new NodoServicioDTO();
		dto.setNombre(servicio.getName());
		dto.setBandaHoraria(formatBandaHoraria(servicio.getHoraInicio(), servicio.getHoraFin()));
		return dto;
	}

	private String formatBandaHoraria(int horaInicio, int horaFin) {
		return String.valueOf(horaInicio) + " - " + String.valueOf(horaFin);
	}

	public void add() {
		items.add(new Item());

	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> pois) {
		this.items = pois;
	}
}
