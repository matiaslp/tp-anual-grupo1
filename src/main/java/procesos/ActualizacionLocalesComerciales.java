package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import db.DB_POI;
import poi.LocalComercial;
import poi.POI;

import autentification.Usuario;

public class ActualizacionLocalesComerciales extends Proceso {

	String filePath = "";

	@Override
	public void execute() {
		procesarArchivo(this.filePath);
	}

	public ActualizacionLocalesComerciales(int cantidadReintentos, boolean enviarEmail, boolean disableAccion,
			String filePath, Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		this.filePath = filePath;

	}

	public void procesarArchivo(String filePath) {
		Path path = Paths.get(filePath);
		Map<String, String[]> locales = new HashMap<String,String[]>();
		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String line = null;

			while ((line = reader.readLine()) != null) {
		    	String[] parametros = line.split(";");

		    	if(parametros.length == 2){
		    		String[] palabrasClaves = parametros[1].split(" ");
		    		locales.put(parametros[0], palabrasClaves);
		    	}
		    }
			actualizar(locales);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void actualizar(Map<String, String[]> locales){
		for(Entry<String, String[]> e: locales.entrySet()){
			POI local = (LocalComercial) DB_POI.getInstance().getPOIbyNombre(e.getKey());
			if(local != null){
				local.setEtiquetas(e.getValue());
				DB_POI.getInstance().actualizarPOI(local);
			}
			else {
				local = new LocalComercial();
				local.setNombre(e.getKey());
				local.setEtiquetas(e.getValue());
				DB_POI.getInstance().agregarPOI(local);
				// Revisar todo lo que involucre LocalComercial por los nuevos valores null
				// que pueden llegar a tener
			}
		}
	}
	
}
