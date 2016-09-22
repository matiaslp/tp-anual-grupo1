package abmc.consultaExterna.dtos;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import abmc.POI_DTO;
import poi.NodoServicio;

public class Banco_Converter {

	public static List<POI_DTO> getBancos(String url) throws JSONException, MalformedURLException, IOException {
		// Obtengo el array que me devuelve el JSON
		JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
		Type listType = new TypeToken<ArrayList<Banco_DTO>>() {
		}.getType();
		Gson gson = new Gson();
		List<Banco_DTO> listadoBanco_DTO = gson.fromJson(jsonArray.toString(), listType);
		List<POI_DTO> listadoPOI_DTO = new ArrayList<POI_DTO>();

		for (Banco_DTO dto : listadoBanco_DTO) {
			POI_DTO dtoGenerico = new POI_DTO();
			dtoGenerico.setNombre(dto.getBanco());
			dtoGenerico.setLongitud(dto.getX());
			dtoGenerico.setLatitud(dto.getY());
			dtoGenerico.setSucursal(dto.getSucursal());
			dtoGenerico.setGerente(dto.getGerente());

			for (String servicioDTO : dto.getServicios()) {
				NodoServicio servicio = new NodoServicio();
				servicio.setName(servicioDTO);
				dtoGenerico.addServicio(servicio);
			}

			listadoPOI_DTO.add(dtoGenerico);
		}

		return listadoPOI_DTO;

	}
}
