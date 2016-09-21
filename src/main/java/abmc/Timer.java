package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONException;

import poi.POI;

class Timer implements Busqueda {

	long seconds;


	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException, MessagingException {

		// start timer
		DateTime start = new DateTime();
		ArrayList<POI> resultado = Consulta.getInstance().buscar(url, texto, userID);
		// stop timer
		DateTime now = new DateTime();
		Period period = new Period(start, now);
		seconds = period.getSeconds();
		return resultado;
	}

	public long getSeconds() {
		return seconds;
	}

}
