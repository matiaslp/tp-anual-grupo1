package ar.edu.utn.dds.grupouno.frontend.busqueda;



import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ar.edu.utn.dds.grupouno.db.poi.NodoServicio;
 
@ManagedBean(name = "servicios")
@ApplicationScoped
public class PoiService {
     
	private ArrayList<NodoServicio> nodos;
}