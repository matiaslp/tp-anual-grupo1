package autentification;

import javax.mail.MessagingException;

import email.EnviarEmail;

public class funcEnviarMail extends Accion {
		
	@Override
	public boolean enviarMail(String nombreDeBusqueda,Long segundos) throws MessagingException{
		if(EnviarEmail.mandarCorreoXSegundos(nombreDeBusqueda, segundos)){
			return true;
		}else{
			return false;
		}
	}

}
