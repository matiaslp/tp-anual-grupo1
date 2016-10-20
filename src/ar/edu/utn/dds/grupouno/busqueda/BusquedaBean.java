package ar.edu.utn.dds.grupouno.busqueda;


 
import java.io.IOException;

import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import org.json.JSONException;


 

@ManagedBean(name="dtBasicView")
@ViewScoped
public class BusquedaBean  {
	private String usuario;
	private String textoLibre;
    


	private ArrayList<POI> pois;
     
    @ManagedProperty("#{poiService}")
    private PoiService service;
 
    @PostConstruct
    public void init() throws JSONException, MalformedURLException, IOException, MessagingException {
        pois = service.busquedaPois(textoLibre,usuario);
    }
     
   
 
    public void setService(PoiService service) {
        this.service = service;
    }
    


	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getTextoLibre() {
		return textoLibre;
	}



	public void setTextoLibre(String textoLibre) {
		this.textoLibre = textoLibre;
	}



	public ArrayList<POI> getPois() {
		return pois;
	}



	public void setPois(ArrayList<POI> pois) {
		this.pois = pois;
	}



	public PoiService getService() {
		return service;
	}

}