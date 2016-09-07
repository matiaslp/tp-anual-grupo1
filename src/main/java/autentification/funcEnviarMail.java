package autentification;

import javax.mail.MessagingException;

import email.EnviarEmail;

public class funcEnviarMail extends Accion {
	EnviarEmail correo = new EnviarEmail();
	
	@Override
	public boolean enviarMail(String nombreDeBusqueda,Long segundos) throws MessagingException{
		if(correo.mandarCorreoXSegundos(nombreDeBusqueda, segundos)){
			return true;
		}else{
			return false;
		}
	}

}
