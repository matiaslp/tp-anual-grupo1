package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONException;

import poi.POI;

public class Timer implements Busqueda {

	long seconds;
	@Override
	public ArrayList<POI> buscar(String url, String texto,long userID) throws JSONException, MalformedURLException, IOException {
		
		//start timer
		DateTime start = new DateTime();
		ArrayList<POI> resultado = POI_ABMC.getInstance().buscar(url, texto, userID);
		//stop timer
		DateTime now = new DateTime();
		Period period = new Period(start,now);
		seconds = period.getSeconds();
		return resultado;
	}
	public long getSeconds() {
		return seconds;
	}
	

}
