package email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import helpers.LeerProperties;

public abstract class EnviarEmail {

	public static boolean mandarCorreoXSegundos(String nombreDeBusqueda, double segundos, String correoRecibe) throws MessagingException {

		String correoEnvia = LeerProperties.getInstance().prop.getProperty("email");
		String claveCorreo = LeerProperties.getInstance().prop.getProperty("emailPassword");

		String texto = "Busqueda " + nombreDeBusqueda + " demoro mas de " + segundos + " segundos.";
		String titulo = "Demora de busqueda";

		boolean enviado = mandarCorreo(texto, titulo, correoRecibe, correoEnvia, claveCorreo);
		return enviado;
	}

	public static boolean mandarCorreo(String texto, String titulo, String correoRecibe, String correoEnvia,
			String claveCorreo) throws MessagingException {

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(correoEnvia, claveCorreo);
			}
		};

		try {

			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", LeerProperties.getInstance().prop.getProperty("emailServer"));
			properties.put("mail.smtp.port", LeerProperties.getInstance().prop.getProperty("SMTP_Port"));

			// Contenido del mensaje
			String content = texto;

			// Establecer las direcciones a las que ser� enviado el mensaje
			MimeBodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content, "UTF-8");

			// Agrupar las partes
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(contentPart);

			// Obtener la sesi�n para enviar correos electr�nicos
			Session session = Session.getDefaultInstance(properties, authenticator);

			// Crear el mensaje a enviar
			MimeMessage message = new MimeMessage(session);
			message.setSubject(titulo, "UTF-8");
			message.setFrom(new InternetAddress(correoRecibe));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoRecibe));
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(correoRecibe));
			message.setContent(mp);

			// Enviar el correo electr�nico
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
