package autentification.funciones;

import javax.mail.MessagingException;

import autentification.Accion;
import email.EnviarEmail;
import helpers.LeerProperties;

public class funcEnviarMail extends Accion {
		
	@Override
	public boolean enviarMail(String nombreDeBusqueda, String correo) throws MessagingException{
		if(EnviarEmail.mandarCorreoXSegundos(nombreDeBusqueda, Integer.valueOf(LeerProperties.getInstance().prop.getProperty("segundosDeDemoraParaEmail")), correo)){
			return true;
		}else{
			return false;
		}
	}

}
