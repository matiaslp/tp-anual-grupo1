package autentification;

import java.util.ArrayList;
import java.util.Map;

import javax.mail.MessagingException;

import db.RegistroHistorico;
import poi.POI;

public abstract class Accion {

	protected static ArrayList<Rol> Roles;
	protected String nombreFuncion;

	protected boolean validarsesion(Usuario user, String Token) {
		return AuthAPI.getInstance().validarToken(Token) && user.chequearFuncionalidad(nombreFuncion);
	}

}
