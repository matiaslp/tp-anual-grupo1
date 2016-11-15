package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.mail.MessagingException;
import javax.persistence.Entity;

import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;
@Entity
public class FuncBusquedaPOI extends Accion {

	public FuncBusquedaPOI(Rol rol, Rol rol2) {
		this.setRoles(new HashSet<Rol>());
		// Agregar Roles para esta funcionalidad
		this.Roles.add(rol);
		this.Roles.add(rol2);
		nombre = "busquedaPOI";
	}
	public FuncBusquedaPOI(){
		
	}

	public ArrayList<POI> busquedaPOI(Usuario user, String Token, String texto) {
		String url = LeerProperties.getInstance().prop.getProperty("Bacos");
		ArrayList<POI> resultado = null;
		if (validarsesion(user, Token)) {
			try {
				resultado = POI_ABMC.getInstance().buscar(url, texto, user.getId());
			} catch (JSONException | IOException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultado;
		} else
			return null;

	}
}
