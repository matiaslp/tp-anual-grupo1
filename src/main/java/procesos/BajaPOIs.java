package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import autentification.Usuario;
import db.DB_POI;
import db.DB_ResultadosProcesos;
import db.Resultado;
import db.ResultadoProceso;
import poi.Item_Borrar;

public class BajaPOIs extends Proceso {

	private DB_POI dbPOI = DB_POI.getInstance();
	private String filePath;
	
	@Override
	public ResultadoProceso procesado() {
		return bajaPoi(filePath);
	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, unUser);
		this.cantidadReintentos = cantidadReintentos;
	}

	public ResultadoProceso bajaPoi(String filePath){
		DateTime start = new DateTime();
		DateTime end;
		Path path = Paths.get(filePath);
		ResultadoProceso resultado = null;
		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String line = null;
			List<Item_Borrar> listadoItems = new ArrayList<Item_Borrar>();
			if ((line = reader.readLine()) != null) {
				//IOUtils.toString(new URL(url), Charset.forName("UTF-8"))
				JSONArray jsonArray = new JSONArray(line);
				Type listType = new TypeToken<ArrayList<Item_Borrar>>(){}.getType();
				Gson gson = new Gson();
				listadoItems = gson.fromJson(jsonArray.toString(), listType);
		    }
			String[] valoresBusqueda = new String[listadoItems.size()];
			List<String> valores = new ArrayList<String>();
			List<DateTime> fechas = new ArrayList<DateTime>();
			for(Item_Borrar item : listadoItems){
				valores.add(item.getParametro());
				fechas.add(item.getFechaBorrado());
			}
			valores.toArray(valoresBusqueda);
			Map<Long, Boolean> resumen = dbPOI.bajaPoi(valoresBusqueda, fechas);
			end = new DateTime();
			
			if(!resumen.containsValue(false)){
				resultado = new ResultadoProceso(start, end, this, user.getID(),
							"Todos los POIs fueron eliminados correctamente" , Resultado.OK);
			} else {
				List<Long> pois_fallidos = new ArrayList<Long>();
				for(Entry<Long, Boolean> e : resumen.entrySet()){
					if(!e.getValue())
						pois_fallidos.add(e.getKey());
				}
				resultado = new ResultadoProceso(start, end, this, user.getID(),
							generarMensaje(pois_fallidos) , Resultado.ERROR);
			}
			
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			
		} catch (IOException e){
			end = new DateTime();
			
			resultado = new ResultadoProceso(start, end, this, user.getID(),
					"FileNotFoundException:No existe archivo " + filePath, Resultado.ERROR);
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			e.printStackTrace();
			
		} 
		return resultado;
	}

	private String generarMensaje(List<Long> keys) {
		String mensaje = "El poi con ID: ";
		for(Long key : keys){
			mensaje += key + " - ";
		}
		mensaje += " intentaron ser eliminados pero fallaron";
		return mensaje;
	}
}