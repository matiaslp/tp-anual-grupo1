package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

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

import ar.edu.utn.dds.grupouno.db.poi.NodoServicio;

public class CGP_Converter {

	public static List<POI_DTO> getCGPs(String url) throws JSONException, MalformedURLException, IOException {
		// Obtengo el array que me devuelve el JSON
		JSONArray jsonArray = new JSONArray(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
		Type listType = new TypeToken<ArrayList<CGP_DTO>>() {
		}.getType();
		Gson gson = new Gson();
		List<CGP_DTO> listadoCGP_DTO = gson.fromJson(jsonArray.toString(), listType);
		List<POI_DTO> listadoPOI_DTO = new ArrayList<POI_DTO>();

		for (CGP_DTO dto : listadoCGP_DTO) {
			POI_DTO dtoGenerico = new POI_DTO();
			dtoGenerico.setComuna(dto.getComuna());
			dtoGenerico.setZonas(dto.getZonas());
			dtoGenerico.setDirector(dto.getDirector());
			if (!dto.getDomicilio().isEmpty()) {
				dtoGenerico.setCallePrincipal(parseCalle(dto.getDomicilio()));
				dtoGenerico.setNumeracion(parseNumero(dto.getDomicilio()));
			}
			dtoGenerico.setTelefono(dto.getTelefono());

			for (NodoServicioDTO servicioDTO : dto.getServicios()) {
				NodoServicio servicio = new NodoServicio();
				servicio.setName(servicioDTO.getNombre());
				for (HorariosDTO horariosDTO : servicioDTO.getHorarios()) {
					servicio.agregarDia(horariosDTO.getDiaSemana());
					servicio.setHoras(horariosDTO.getHoraDesde(), horariosDTO.getHoraHasta());
				}
				dtoGenerico.addServicio(servicio);
			}

			listadoPOI_DTO.add(dtoGenerico);
		}

		return listadoPOI_DTO;
	}

	private static String parseCalle(String domicilio) {
		int corte = domicilio.length();
		for (int i = corte - 1; 0 < i; i--) {
			if (domicilio.charAt(i) != ' ') {
				corte = i;
				break;
			}
		}
		return domicilio.substring(0, corte);
	}

	private static int parseNumero(String domicilio) {
		int corte = domicilio.length();
		for (int i = corte - 1; 0 < i; i--) {
			if (domicilio.charAt(i) != ' ') {
				corte = i + 1;
				break;
			}
		}

		return Integer.parseInt(domicilio.substring(corte, domicilio.length()));
	}

}
