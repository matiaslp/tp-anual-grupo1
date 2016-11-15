package ar.edu.utn.dds.grupouno.procesos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.DateDeserializer;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.ItemBorrarConstructor;
import ar.edu.utn.dds.grupouno.abmc.poi.Item_Borrar;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.DB_ResultadosProcesos;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class BajaPOIs extends Proceso {

	private DB_POI dbPOI = Repositorio.getInstance().pois();
	private String filePath;

	@Override
	public ResultadoProceso procesado() {
		return bajaPoi(filePath);
	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, Usuario unUser, String filePath) {
		super(cantidadReintentos, enviarEmail, unUser);
		this.filePath = filePath;
	}

	public ResultadoProceso bajaPoi(String filePath) {
		DateTime start = new DateTime();
		DateTime end;
		ResultadoProceso resultado = null;
		try {
			List<Item_Borrar> listadoItems = new ArrayList<Item_Borrar>();

			Gson gson = generarGson();

			listadoItems = leerJson(gson, filePath);

			Map<Long, Boolean> resumen = darDeBaja(listadoItems);
			end = new DateTime();

			// Si el listado de resumen tiene algun elemento con value false
			// significa que ese elemento no se pudo borrar
			if (!resumen.containsValue(false)) {
				resultado = new ResultadoProceso(start, end, TiposProceso.BAJAPOIS, user.getId(),
						"Todos los POIs fueron eliminados correctamente", Resultado.OK);
			} else {
				List<Long> pois_fallidos = new ArrayList<Long>();
				for (Entry<Long, Boolean> e : resumen.entrySet()) {
					if (!e.getValue())
						pois_fallidos.add(e.getKey());
				}
				resultado = new ResultadoProceso(start, end, TiposProceso.BAJAPOIS, user.getId(),
						generarMensaje(pois_fallidos), Resultado.ERROR);
			}

			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);

		} catch (IOException e) {
			end = new DateTime();

			resultado = new ResultadoProceso(start, end, TiposProceso.BAJAPOIS, user.getId(),
					"FileNotFoundException:No existe archivo " + filePath, Resultado.ERROR);
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			e.printStackTrace();
		}
		return resultado;
	}

	private String generarMensaje(List<Long> keys) {
		String mensaje = "El poi con ID: ";
		for (Long key : keys) {
			mensaje += key + " - ";
		}
		mensaje += " intentaron ser eliminados pero fallaron";
		return mensaje;
	}

	private Gson generarGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Item_Borrar.class, new ItemBorrarConstructor());
		return gsonBuilder.create();
	}

	private List<Item_Borrar> leerJson(Gson gson, String filepath) throws FileNotFoundException {
		JsonReader jsonReader = new JsonReader(new FileReader(filePath));
		Type listType = new TypeToken<ArrayList<Item_Borrar>>() {
		}.getType();
		return gson.fromJson(jsonReader, listType);
	}

	private Map<Long, Boolean> darDeBaja(List<Item_Borrar> listadoItems) {
		String[] valoresBusqueda = new String[listadoItems.size()];
		List<String> valores = new ArrayList<String>();
		List<DateTime> fechas = new ArrayList<DateTime>();
		for (Item_Borrar item : listadoItems) {
			valores.add(item.getParametro());
			fechas.add(new DateTime(item.getFechaBorrado()));
		}
		valores.toArray(valoresBusqueda);
		return dbPOI.bajaPoi(valoresBusqueda, fechas);
	}
}