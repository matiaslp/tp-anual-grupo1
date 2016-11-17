package ar.edu.utn.dds.grupouno.procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.Proceso;

public class ActualizacionLocalesComerciales extends Proceso {

	public ActualizacionLocalesComerciales(){
		
	}
	
	@Override
	public void execute(JobExecutionContext contexto) throws JobExecutionException {
		
		JobDataMap dataMap = contexto.getJobDetail().getJobDataMap();
		String filePath = dataMap.getString("filePath");
		
		Map<String, String[]> locales = new HashMap<String, String[]>();
		ResultadoProceso resultado = null;
		SchedulerContext schedulerContext = null;
		Usuario usuario = null;
				
		try {
			// Traigo el contexto
			schedulerContext = contexto.getScheduler().getContext();
			
			// Obtengo del contexto el usuario y el resultado de proceso.
			usuario = (Usuario)schedulerContext.get("Usuario");
			resultado = (ResultadoProceso)schedulerContext.get("ResultadoProceso");
						
			resultado.setUserID(usuario.getId());
			resultado.setProc(TiposProceso.ACTUALIZACIONLOCALESCOMERCIALES);
		
		} catch (SchedulerException excepcion) {
			excepcion.printStackTrace();
        } 
		
		if((locales = parsearArchivo(filePath)) != null){
			boolean resultadoActualizar = actualizar(locales);
			
			if (resultadoActualizar) {
				resultado.setResultado(Resultado.OK);
			} else {
				resultado.setResultado(Resultado.ERROR);
				resultado.setMensajeError("No se pudieron actualizar todos los locales");
			}
		} else {
			resultado.setResultado(Resultado.ERROR);
			resultado.setMensajeError("No existe el archio " + filePath);
		}
	}
	
	public boolean actualizar(Map<String, String[]> locales) {
		try {
			List<Boolean> resultados = new ArrayList<Boolean>();
			for (Entry<String, String[]> e : locales.entrySet()) {
				POI local = (LocalComercial) getDbPOI().getPOIbyNombre(e.getKey());
				
				// En caso de que el local exista se lo actualiza mientras que 
				// en caso contrario se crea un local con los datos del archivo.
				if (local != null) {
					local.setEtiquetas(e.getValue());
					resultados.add(Repositorio.getInstance().pois().actualizarPOI(local));
				} else {
					local = new LocalComercial();
					local.setNombre(e.getKey());
					local.setEtiquetas(e.getValue());
					resultados.add(Repositorio.getInstance().pois().agregarPOI(local));
				}
			}
			if(!resultados.contains(false))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
		
	private Map<String, String[]> parsearArchivo(String filePath){
		Path path = Paths.get(filePath);
		Map<String, String[]> locales = new HashMap<String, String[]>();
		
		// Leo el archivo
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] parametros = line.split(";");

				if (parametros.length == 2) {
					String[] palabrasClaves = parametros[1].split(" ");
					locales.put(parametros[0], palabrasClaves);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			locales = null;
		}
		
		return locales;
	}
}
