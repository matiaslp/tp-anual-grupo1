package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.json.JSONException;

import db.DB_HistorialBusquedas;
import db.RegistroHistorico;
import email.EnviarEmail;
import helpers.LeerProperties;
import poi.POI;

//Esta clase funciona se encuentra detras de un Facade
//Es recomendable consultar a la clase POI_ABMC.

class Historico implements Busqueda {

	private static Historico instance = null;

	public static Historico getInstance() {
		if (instance == null)
			instance = new Historico();
		return instance;
	}

	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException, MessagingException {

		DateTime now = new DateTime();
		int segundos = Integer.valueOf(LeerProperties.getInstance().prop.getProperty("segundosDeDemoraParaEmail"));
		// Se inicia el Timer
		Timer timer = new Timer();
		ArrayList<POI> resultado = timer.buscar(url, texto, userID);

		// Se evalua si el Timer tardo mas de los segundos estipulados por
		// archivo de configuracion
		if (timer.getSeconds() > segundos)
			// Se envia Email a todos los usuarios que tenga la funcion de
			// recibir emails habilitada
			EnviarEmail.MandarCorreoXSegundosUsuarios(texto, segundos);

		// Registrar busqueda
		// Se deja el id seteado en 0 hasta que se implemente hibernate
		RegistroHistorico registro = new RegistroHistorico(0, now, userID, texto, resultado.size(), timer.getSeconds());
		DB_HistorialBusquedas.getInstance().agregarHistorialBusqueda(registro);

		return resultado;
	}

}
