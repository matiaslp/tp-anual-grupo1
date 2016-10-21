package ar.edu.utn.dds.grupouno.busqueda;


 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class BusquedaBean  {
	private int dataTableSize =1;
	private String textoLibre;
    private List<Item> items;

    public BusquedaBean() {
    	items = new ArrayList<Item>();
    	items.add(new Item());
    }

     
    @ManagedProperty("#{poiService}")
    private PoiService service;
 
    /*@PostConstruct
    public void init() throws JSONException, MalformedURLException, IOException, MessagingException {
        pois = service.busquedaPois(textoLibre,usuario);
    }*/
     
   
 
    public void setService(PoiService service) {
        this.service = service;
    }

	public String getTextoLibre() {
		return textoLibre;
	}

	public void setTextoLibre(String textoLibre) {
		this.textoLibre = textoLibre;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> pois) {
		this.items = pois;
	}

	public PoiService getService() {
		return service;
	}

	public void add(){
		items.add(new Item());
		
	}
}

