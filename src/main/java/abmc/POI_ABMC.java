package abmc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

import db.DB_Server;
import poi.BusquedaDePOIsExternos;
import poi.POI;

public class POI_ABMC implements Busqueda {

	private ArrayList<POI> resultado;
	private static POI_ABMC instance = null;
	
	public static POI_ABMC getInstance() {
		if(instance == null)
			instance = new POI_ABMC();
		return instance;
	}
	

	public boolean alta(POI_DTO dto) {

		POI nuevoPOI = dto.converttoPOI();
		if (nuevoPOI.equals(null)) {
			return false;
		} else {
			DB_Server.agregarPOI(nuevoPOI);
			return true;
		}
	}

	public boolean delete(int ID) {
		if (DB_Server.getPOIbyId(ID) != null) {
			return DB_Server.eliminarPOI(ID);
		} else
			return false;
	}

	public boolean modificar(POI_DTO dto) {
		POI poi = null;
		poi = DB_Server.getPOIbyId(dto.getId());
		if (poi != null) {
			poi.setDatos(dto);
			return true;
		}
		return false;
	}
	
	public boolean poiExists (ArrayList<POI> list, POI poi){
		
		if (list.size() > 0)
			for (POI nodo : list){
				if (nodo.compararPOI(poi))
					return true;
			}
		return false;
	}
	
	
// Busqueda por texto libre ABIERTA (busca todos los pois que contengan al menos UNA palabra contenida en la busqueda)
	public ArrayList<POI> buscar(String url,String texto,long userID) throws JSONException, MalformedURLException, IOException{
		String filtros[] = texto.split("\\s+");
		resultado = new ArrayList<POI>();
		ArrayList<POI> listaLocal = DB_Server.getListado();
		for(POI nodo : listaLocal){
			if(nodo.busquedaEstandar(filtros)){
				resultado.add(nodo);
			}
		}
		if(url != ""){
			List<POI> listaExterna = new ArrayList<POI>();
			for ( String palabra : filtros ){
				listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra);//cgp
				if(!(listaExterna.isEmpty())){
					for (POI x : listaExterna){
						   if (!poiExists(resultado,x))
						      resultado.add(x);
					}
				}
				if(filtros.length > 1){
					for ( String palabra2 : filtros ){
						listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra, palabra2);//bancos
						if(!(listaExterna.isEmpty())){
							for (POI x : listaExterna){
								if (!poiExists(resultado,x))
								      resultado.add(x);
							}
						}
					}
					
				// si hay una sola palabra se busca solo por servicio o solo por banco
				}
					listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra, "");//bancos
					if(!(listaExterna.isEmpty())){
						for (POI x : listaExterna){
							if (!poiExists(resultado,x))
							      resultado.add(x);
						}
					}
					listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, "", palabra);//bancos
					if(!(listaExterna.isEmpty())){
						for (POI x : listaExterna){
							if (!poiExists(resultado,x))
							      resultado.add(x);
						}
					}
			}
		}
		return resultado;
	}
}
