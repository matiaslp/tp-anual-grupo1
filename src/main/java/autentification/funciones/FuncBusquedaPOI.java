package autentification.funciones;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.json.JSONException;

import abmc.POI_ABMC;
import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import helpers.LeerProperties;
import poi.POI;

public class FuncBusquedaPOI extends Accion {

	public FuncBusquedaPOI() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "busquedaPOI";
	}

	public ArrayList<POI> busquedaPOI(Usuario user, String Token, String texto) {
		String url = LeerProperties.getInstance().prop.getProperty("Bacos");
		ArrayList<POI> resultado = null;
		if (validarsesion(user, Token)) {
			try {
				resultado = POI_ABMC.getInstance().buscar(url, texto, user.getID());
			} catch (JSONException | IOException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultado;
		} else
			return null;

	}
}
