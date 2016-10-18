package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;

public class FuncBusquedaPOI extends Accion {

	public FuncBusquedaPOI() {
		this.setRoles(new ArrayList<Rol>());
		// Agregar Roles para esta funcionalidad
		this.Roles.add(Rol.ADMIN);
		this.Roles.add(Rol.TERMINAL);
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
