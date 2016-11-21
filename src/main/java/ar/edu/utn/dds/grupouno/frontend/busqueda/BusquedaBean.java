package ar.edu.utn.dds.grupouno.frontend.busqueda;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.Busqueda;
import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.BusquedaDePOIsExternos;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@ManagedBean
@ApplicationScoped
public class BusquedaBean {
	// private int dataTableSize = 1;
	private String textoLibre;
	String item;
	// public String getItem() {
	// return item;
	// }

	// public void setItem(String item) {
	// this.item = item;
	// }

	// private List<Item> items;
	private List<POI> pois = new ArrayList<POI>();
	
	String ServicioAPI = "http://trimatek.org/Consultas/";

	public BusquedaBean() {
	}

	// @ManagedProperty("#{poiService}")
	// private PoiService service;

	/*
	 * @PostConstruct public void init() throws JSONException,
	 * MalformedURLException, IOException, MessagingException { pois =
	 * service.busquedaPois(textoLibre,usuario); }
	 */

	// public void setService(PoiService service) {
	// this.service = service;
	// }

	public String getTextoLibre() {
		return textoLibre;
	}

	public List<POI> getPois() {
		return pois;

	}

	public void setPois(List<POI> pois) {
		this.pois = pois;
	}

	public void setTextoLibre(String textoLibre) {
		this.textoLibre = textoLibre;
	}

	// public List<Item> getItems() {
	//
	//
	//
	// return items;
	// }
	//
	// public void setItems(List<Item> pois) {
	// this.items = pois;
	// }
	//
	// public PoiService getService() {
	// return service;
	// }

	// public void add() {
	// items.add(new Item());
	//
	// }

	public void buscar() {	
		pois.clear();
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		ArrayList<POI> lstPOI = null;
		try {
			lstPOI = POI_ABMC.getInstance().buscar("", textoLibre, usuario.getId());

		} catch (JSONException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lstPOI != null && lstPOI.size() > 0) {
			pois.addAll(lstPOI);

		}

	}

}
