package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.json.JSONException;

import autentification.AuthAPI;
import autentification.Usuario;
import db.DB_HistorialBusquedas;
import db.DB_Usuarios;
import db.RegistroHistorico;
import autentification.funciones.FuncEnviarMail;
import helpers.LeerProperties;
import poi.POI;

public class Historico implements Busqueda {

	@Override
	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException {

		DateTime now = new DateTime();
		// new timer
		Timer timer = new Timer();
		ArrayList<POI> resultado = timer.buscar(url, texto, userID);

		FuncEnviarMail funcMail = ((FuncEnviarMail) AuthAPI.getInstance().getAcciones().get("enviarMail"));
		if (timer.getSeconds() > Integer
				.valueOf(LeerProperties.getInstance().prop.getProperty("segundosDeDemoraParaEmail"))) {
			for (Usuario usuario : DB_Usuarios.getInstance().getListaUsuarios()) {
				if (usuario.chequearFuncionalidad("enviarMail") && usuario.getCorreo() != null)
					try {
						funcMail.enviarMail(texto, usuario.getCorreo());
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		// Registrar busqueda
		// Se deja el id seteado en 0 hasta que se implemente hibernate
		RegistroHistorico registro = new RegistroHistorico(0, now, userID, texto, resultado.size(), timer.getSeconds());
		DB_HistorialBusquedas.getInstance().agregarHistorialBusqueda(registro);

		return resultado;
	}

}
