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

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.DateDeserializer;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.ItemBorrarConstructor;
import ar.edu.utn.dds.grupouno.abmc.poi.Item_Borrar;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.quartz.Proceso;

public class BajaPOIs extends Proceso {

	public BajaPOIs() {
		
	}
	
	@Override
	public void execute(JobExecutionContext contexto) throws JobExecutionException {
		ResultadoProceso resultado = null;
		SchedulerContext schedulerContext = null;
		Usuario usuario = null;
		
		JobDataMap dataMap = contexto.getJobDetail().getJobDataMap();
		String filePath = dataMap.getString("filePath");
		
		try {
			//Traigo el contexto
			schedulerContext = contexto.getScheduler().getContext();
			
			// Obtengo del contexto el usuario y el resultado de proceso.
			usuario = (Usuario)schedulerContext.get("Usuario");
			resultado = (ResultadoProceso)schedulerContext.get("ResultadoProceso");
						
			resultado.setUserID(usuario.getId());
			resultado.setProc(TiposProceso.BAJAPOIS);
		
		} catch (SchedulerException excepcion) {
			excepcion.printStackTrace();
        }
		
		try {
			List<Item_Borrar> listadoItems = new ArrayList<Item_Borrar>();
			Gson gson = generarGson();
			listadoItems = leerJson(gson,filePath);
			Map<Long, Boolean> resumen = darDeBaja(listadoItems);
						
			// Si el listado de resumen tiene algun elemento con value false
			// significa que ese elemento no se pudo borrar
			if (!resumen.containsValue(false)) {
				resultado.setResultado(Resultado.OK);
			} else {
				List<Long> pois_fallidos = new ArrayList<Long>();
				for (Entry<Long, Boolean> e : resumen.entrySet()) {
					if (!e.getValue())
						pois_fallidos.add(e.getKey());
				}

				resultado.setResultado(Resultado.ERROR);
				resultado.setMensajeError(generarMensaje(pois_fallidos));
				schedulerContext.replace("ResultadoProceso", resultado);
				JobExecutionException e2 = new JobExecutionException();
				throw e2;
			}
	
		} catch (IOException e) {
			resultado.setResultado(Resultado.ERROR);
			resultado.setMensajeError("FileNotFoundException:No existe archivo " + filePath);
			schedulerContext.replace("ResultadoProceso", resultado);
			JobExecutionException e2 = new JobExecutionException(e);
			throw e2;
		}
		
		schedulerContext.replace("ResultadoProceso", resultado);
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
		JsonReader jsonReader = new JsonReader(new FileReader(filepath));
		Type listType = new TypeToken<ArrayList<Item_Borrar>>() {
		}.getType();
		return gson.fromJson(jsonReader, listType);
	}

	private Map<Long,Boolean> darDeBaja(List<Item_Borrar> listadoItems){
		return getDbPOI().bajaPoi(listadoItems);	
	}
	
}