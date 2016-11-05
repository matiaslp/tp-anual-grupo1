package ar.edu.utn.dds.grupouno.email;

import java.util.ArrayList;
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

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;

public abstract class EnviarEmail {
	
	public static boolean mandarCorreoProcesoError(Usuario user, ArrayList<ResultadoProceso> listaResultados) {
		
		String correoEnvia = LeerProperties.getInstance().prop.getProperty("email");
		String claveCorreo = LeerProperties.getInstance().prop.getProperty("emailPassword");

		String texto = "";
		
		for (ResultadoProceso resultado : listaResultados) {
			String res = "";
			String clase = resultado.getProc().getClass().toString();
			if (resultado.getResultado().equals(Resultado.ERROR))
				res = "con errores";
			else if (resultado.getResultado().equals(Resultado.OK))
				res = "satisfactoria";
			texto = texto +  " Proceso " + clase + " ejecucion " + res + "\n" +
			"Inicio de ejecucion: " + resultado.getInicioEjecucion().toString() + "\n" +
			"Fin de ejecucion: " + resultado.getFinEjecucion().toString() + "\n" +
			"Ejecutado por usuario: " + resultado.getUserID() + "\n" +
			resultado.getMensajeError() + "\n\n";
		}
		
		
		String titulo = "Errores Ejecucion de Proceso";

		boolean enviado = false;
		try {
			enviado = mandarCorreo(texto, titulo, user.getCorreo(), correoEnvia, claveCorreo);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enviado;
		
	}
	

	public static boolean mandarCorreoXSegundos(String nombreDeBusqueda, double segundos, String correoRecibe)
			throws MessagingException {

		String correoEnvia = LeerProperties.getInstance().prop.getProperty("email");
		String claveCorreo = LeerProperties.getInstance().prop.getProperty("emailPassword");

		String texto = "Busqueda " + nombreDeBusqueda + " demoro mas de " + segundos + " segundos.";
		String titulo = "Demora de busqueda";

		boolean enviado = mandarCorreo(texto, titulo, correoRecibe, correoEnvia, claveCorreo);
		return enviado;
	}

	public static boolean mandarCorreo(String texto, String titulo, String correoRecibe, final String correoEnvia,
			final String claveCorreo) throws MessagingException {

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

	// Se envia email a todos los usuarios que tenga la funcionalidad de
	// recibirEmails activada
	public static void MandarCorreoXSegundosUsuarios(String texto, int segundos) throws MessagingException {
		for (Usuario usuario : Repositorio.getInstance().usuarios().getListaUsuarios())
			if (usuario.isMailHabilitado() && usuario.getCorreo() != null)
				EnviarEmail.mandarCorreoXSegundos(texto, segundos, usuario.getCorreo());
	}

}
