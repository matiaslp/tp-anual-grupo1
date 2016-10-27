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

import org.joda.time.DateTime;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;


public class ActualizacionLocalesComerciales extends Proceso {

	String filePath = "";

	@Override
	public ResultadoProceso procesado() {
		return procesarArchivo(this.filePath);
	}

	public ActualizacionLocalesComerciales(int cantidadReintentos, boolean enviarEmail, String filePath,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, unUser);
		this.filePath = filePath;

	}

	public ResultadoProceso procesarArchivo(String filePath) {
		Path path = Paths.get(filePath);
		Map<String, String[]> locales = new HashMap<String, String[]>();
		ResultadoProceso resultado = null;
		DateTime start = new DateTime();
		DateTime end;
		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] parametros = line.split(";");

				if (parametros.length == 2) {
					String[] palabrasClaves = parametros[1].split(" ");
					locales.put(parametros[0], palabrasClaves);
				}
			}
			boolean resultadoActualizar = actualizar(locales);
			end = new DateTime();
			if (resultadoActualizar)
				resultado = new ResultadoProceso(start, end, this, user.getId(),
						"Los elementos se actualizaron correctamente", Resultado.OK);
			else
				resultado = new ResultadoProceso(start, end, this, user.getId(),
						"No se pudieron actualizar todos los locales", Resultado.ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			end = new DateTime();
			resultado = new ResultadoProceso(start, end, this, user.getId(), "No existe el archio " + filePath,
					Resultado.ERROR);
		}
		return resultado;
	}

	public boolean actualizar(Map<String, String[]> locales) {
		try {
			List<Boolean> resultados = new ArrayList<Boolean>();
			for (Entry<String, String[]> e : locales.entrySet()) {
				POI local = (LocalComercial) DB_POI.getInstance().getPOIbyNombre(e.getKey());
				if (local != null) {
					local.setEtiquetas(e.getValue());
					resultados.add(DB_POI.getInstance().actualizarPOI(local));
				} else {
					local = new LocalComercial();
					local.setNombre(e.getKey());
					local.setEtiquetas(e.getValue());
					resultados.add(DB_POI.getInstance().agregarPOI(local));
				}
			}
			if(!resultados.contains(false))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
