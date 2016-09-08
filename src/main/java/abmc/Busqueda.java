package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;

import poi.POI;

public interface Busqueda {

	public ArrayList<POI> buscar(String url,String texto, long userID);
	
	
	
}
