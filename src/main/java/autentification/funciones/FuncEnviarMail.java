package autentification.funciones;

import java.util.ArrayList;

import javax.mail.MessagingException;

import autentification.Accion;
import autentification.Rol;
import email.EnviarEmail;
import helpers.LeerProperties;

public class FuncEnviarMail extends Accion {
	
	public FuncEnviarMail(){	
		Roles = new ArrayList<Rol>();
		//Agregar Roles para esta funcionalidad
		Roles.add(Rol.ADMIN);
	}

	@Override
	public boolean enviarMail(String nombreDeBusqueda, String correo) throws MessagingException {
		if (EnviarEmail.mandarCorreoXSegundos(nombreDeBusqueda,
				Integer.valueOf(LeerProperties.getInstance().prop.getProperty("segundosDeDemoraParaEmail")), correo)) {
			return true;
		} else {
			return false;
		}
	}

}
