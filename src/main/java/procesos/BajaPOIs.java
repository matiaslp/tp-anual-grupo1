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
	public void execute() {
		if(!bajaPoi(filePath)){
			for(int i = 1;(this.cantidadReintentos > 0 && this.cantidadReintentos < i && !bajaPoi(filePath)); i++){
				if(this.enviarEmail){
					//enviar mail
				}
			}
		}
	}

	public BajaPOIs(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		this.cantidadReintentos = cantidadReintentos;
	}

	public boolean bajaPoi(String filePath){
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
			for(Entry<Long, Boolean> entry : resumen.entrySet()){
				if(entry.getValue()){
					resultado = new ResultadoProceso(0, start, end, this, user.getID(),
							generarMensaje(entry.getKey(), entry.getValue()) , Resultado.OK);
				} else {
					resultado = new ResultadoProceso(0, start, end, this, user.getID(),
							generarMensaje(entry.getKey(), entry.getValue()) , Resultado.ERROR);
				}
			}
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			return true;
		} catch (IOException e){
			end = new DateTime();
			
			resultado = new ResultadoProceso(0, start, end, this, user.getID(),
					"FileNotFoundException:No existe archivo " + filePath, Resultado.ERROR);
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			e.printStackTrace();
			return false;
		}
	}

	private String generarMensaje(Long key, Boolean value) {
		String mensaje = "El poi con ID: " + key;
		if(value)
			mensaje += " fue eliminado de manera correcta";
		else
			mensaje += " intento ser eliminado pero fallo";
		return mensaje;
	}
}