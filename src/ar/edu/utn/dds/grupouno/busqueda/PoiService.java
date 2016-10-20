package ar.edu.utn.dds.grupouno.busqueda;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.MessagingException;

import org.json.JSONException;


import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.poi.POI;
 
@ManagedBean(name = "carService")
@ApplicationScoped
public class PoiService {
     
    private ArrayList<POI> pois;
    private String ServicioAPI = "http://trimatek.org/Consultas/";
 

    public ArrayList<POI> busquedaPois(String textoLibre,String usuario) throws JSONException, MalformedURLException, IOException, MessagingException {
    	ArrayList<POI> list = new ArrayList<POI>();
    	Usuario user = DB_Usuarios.getInstance().getUsuarioByName(usuario);
	list=POI_ABMC.getInstance().buscar(this.getServicioAPI(), textoLibre, user.getID());
         
        return list;
    }     
    

    public ArrayList<POI> getPois() {
		return pois;
	}

	public void setPois(ArrayList<POI> pois) {
		this.pois = pois;
	}

	public String getServicioAPI() {
		return ServicioAPI;
	}

	public void setServicioAPI(String servicioAPI) {
		ServicioAPI = servicioAPI;
	}

    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
 

}