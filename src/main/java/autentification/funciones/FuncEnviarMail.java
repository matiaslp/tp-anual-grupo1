package autentification.funciones;

import java.util.ArrayList;

import javax.mail.MessagingException;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import email.EnviarEmail;
import helpers.LeerProperties;

public class FuncEnviarMail extends Accion {

	public FuncEnviarMail() {
		Roles = new ArrayList<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
		nombreFuncion = "enviarMail";
	}

	public boolean enviarMail(Usuario user, String Token, String nombreDeBusqueda, String correo)
			throws MessagingException {
		if (validarsesion(user, Token))
			return EnviarEmail.mandarCorreoXSegundos(nombreDeBusqueda,
					Integer.valueOf(LeerProperties.getInstance().prop.getProperty("segundosDeDemoraParaEmail")),
					correo);
		else
			return false;
	}

}
