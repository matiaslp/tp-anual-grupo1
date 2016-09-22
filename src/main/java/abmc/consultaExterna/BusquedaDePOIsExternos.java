package abmc.consultaExterna;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import abmc.POI_DTO;
import abmc.consultaExterna.dtos.Banco_Converter;
import abmc.consultaExterna.dtos.CGP_Converter;
import poi.POI;
import poi.TiposPOI;

public class BusquedaDePOIsExternos {

	public static List<POI> buscarPOIsExternos(String url, String textoLibre1, String textoLibre2)
			throws JSONException, MalformedURLException, IOException {
		String urlCreada;
		List<POI_DTO> listaResultado = null;
		List<POI> listaResultadoEnPOI = new ArrayList<POI>();
		POI poi;
		// nombre de banco y servicio
		// http://trimatek.org/Consultas/banco?banco=Santander&servicio=Pagos
		if (textoLibre1 != "" || textoLibre2 != "") {
			urlCreada = url + "banco?banco=" + textoLibre1 + "&servicio=" + textoLibre2;

			listaResultado = Banco_Converter.getBancos(urlCreada);

			for (POI_DTO poi_dto : listaResultado) {
				// Setiando tipo de POI
				poi_dto.setTipo(TiposPOI.BANCO);
				// Convirtiendo
				poi = poi_dto.converttoPOI();
				listaResultadoEnPOI.add(poi);
			}
			// urlCreada = url + "banco?banco=" + textoLibre2 + "&servicio=" +
			// textoLibre1;
			//
			// listaResultado = Banco_Converter.getBancos(urlCreada);
			//
			// for (POI_DTO poi_dto : listaResultado) {
			// // Setiando tipo de POI
			// poi_dto.setTipo(TiposPOI.BANCO);
			// // Convirtiendo
			// poi = poi_dto.converttoPOI();
			// listaResultadoEnPOI.add(poi);
			// }
		}

		return listaResultadoEnPOI;
	}

	public static List<POI> buscarPOIsExternos(String url, String textoLibre1)
			throws JSONException, MalformedURLException, IOException {
		String urlCreada;
		List<POI_DTO> listaResultado1 = null;
		List<POI_DTO> listaResultado2 = null;

		List<POI> listaResultadoEnPOI = new ArrayList<POI>();
		POI poi;

		// Agrego POIs con zona ejemplo
		// http://trimatek.org/Consultas/centro?zona=Boedo
		if (textoLibre1 != "") {
			urlCreada = url + "centro?zona=" + textoLibre1;
			listaResultado1 = CGP_Converter.getCGPs(urlCreada);
			// Agrego POIs con domicilio ejemplo
			// http://trimatek.org/Consultas/centro?domicilio=Boedo
			// PARA MI "LUCAS" ANDA MAL EL SERVICIO DE BUSQUEDA
			// /Consultas/centro?domicilio=Boedo que devuelve 15 objetos
			// YA QUE NO TIENEN DOMICILIO Y LO DEVUELVE IGUAL
			urlCreada = url + "centro?domicilio=" + textoLibre1;
			listaResultado2 = CGP_Converter.getCGPs(urlCreada);

			// agrego listaResultado1 y convierto
			for (POI_DTO poi_dto : listaResultado1) {

				poi_dto.setTipo(TiposPOI.CGP);
				// Convirtiendo
				poi = poi_dto.converttoPOI();
				// if (!listaResultadoEnPOI.contains(poi))
				listaResultadoEnPOI.add(poi);
			}
			// agrego listaResultado2 y convierto
			for (POI_DTO poi_dto : listaResultado2) {
				poi_dto.setTipo(TiposPOI.CGP);
				// Convirtiendo
				poi = poi_dto.converttoPOI();
				// if (!listaResultadoEnPOI.contains(poi))
				listaResultadoEnPOI.add(poi);
			}
		}

		// convertimos a lista de pois

		return listaResultadoEnPOI;
	}

}
