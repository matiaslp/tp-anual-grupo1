package ar.edu.utn.dds.grupouno.frontend.busqueda;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.json.JSONException;
import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@ManagedBean
@ApplicationScoped
public class BusquedaBean {
	private String textoLibre;
	private List<POI> pois = new ArrayList<POI>();
	String ServicioAPI = "http://trimatek.org/Consultas/";

	public BusquedaBean() {
	}

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

	public void buscar() {
		pois.clear();
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		ArrayList<POI> lstPOI = null;
		try {
			lstPOI = POI_ABMC.getInstance().buscar(ServicioAPI, textoLibre, usuario.getId());

		} catch (JSONException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lstPOI != null && lstPOI.size() > 0) {
			pois.addAll(lstPOI);

		}

	}

}
