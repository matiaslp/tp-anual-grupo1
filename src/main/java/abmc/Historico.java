package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.json.JSONException;

import db.DB_HistorialBusquedas;
import db.RegistroHistorico;
import poi.POI;

public class Historico implements Busqueda {

	@Override
	public ArrayList<POI> buscar(String url, String texto, long userID) throws JSONException, MalformedURLException, IOException {
		
		DateTime now = new DateTime();		
		// new timer
		Timer timer = new Timer();
		ArrayList<POI> resultado = timer.buscar(url, texto, userID);
		
		// Registrar busqueda
		// Se deja el id seteado en 0 hasta que se implemente hibernate
		RegistroHistorico registro = new RegistroHistorico(0,now,userID,texto,resultado.size(),timer.getSeconds());
		DB_HistorialBusquedas.getInstance().agregarHistorialBusqueda(registro);
		
		return resultado;
	}

}
