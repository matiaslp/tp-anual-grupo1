package ar.edu.utn.dds.grupouno.abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.BusquedaDePOIsExternos;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.POI;

//Esta clase funciona se encuentra detras de un Facade y si
// se realiza una busqueda desde aca la misma no verificara
// tiempos de busqueda ni tampoco registrara historial de busqueda
// Es recomendable consultar a la clase POI_ABMC.

class Consulta implements Busqueda {

	private static Consulta instance = null;

	public static Consulta getInstance() {
		if (instance == null)
			instance = new Consulta();
		return instance;
	}

	// Busqueda por texto libre ABIERTA (busca todos los pois que contengan al
	// menos UNA palabra contenida en la busqueda)
	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException {
		String filtros[] = texto.split("\\s+");
		ArrayList<POI> resultado = new ArrayList<POI>();
		ArrayList<POI> listaLocal = DB_POI.getListado();
		for (POI nodo : listaLocal) {
			if (nodo.busquedaEstandar(filtros) && nodo.getFechaBaja() == null) {
				resultado.add(nodo);
			}
		}
		if (url != "") {
			List<POI> listaExterna = new ArrayList<POI>();
			for (String palabra : filtros) {
				listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra);// cgp
				if (!(listaExterna.isEmpty())) {
					for (POI x : listaExterna) {
						if (!poiExists(resultado, x))
							resultado.add(x);
					}
				}
				if (filtros.length > 1) {
					for (String palabra2 : filtros) {
						listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra, palabra2);// bancos
						if (!(listaExterna.isEmpty())) {
							for (POI x : listaExterna) {
								if (!poiExists(resultado, x))
									resultado.add(x);
							}
						}
					}

					// si hay una sola palabra se busca solo por servicio o solo
					// por banco
				}
				listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, palabra, "");// bancos
				if (!(listaExterna.isEmpty())) {
					for (POI x : listaExterna) {
						if (!poiExists(resultado, x))
							resultado.add(x);
					}
				}
				listaExterna = BusquedaDePOIsExternos.buscarPOIsExternos(url, "", palabra);// bancos
				if (!(listaExterna.isEmpty())) {
					for (POI x : listaExterna) {
						if (!poiExists(resultado, x))
							resultado.add(x);
					}
				}
			}
		}
		return resultado;
	}

	private boolean poiExists(ArrayList<POI> list, POI poi) {

		if (list.size() > 0)
			for (POI nodo : list) {
				if (nodo.compararPOI(poi))
					return true;
			}
		return false;
	}

}
