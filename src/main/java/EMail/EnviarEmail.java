package EMail;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class EnviarEmail {
	// Enviar correo
	// * 
	// * @Direcci�n de correo electr�nico, correo Param
	// * @Param title t�tulo Correo
	// * @Param Content mensajes de texto
	// * @param nickName
	// *  El apodo de visualizaci�n
	
	public void sendMail(String email, String title, String content, String nickName) {
		 String correoEnvia = "SistemasDDSGrupo1@gmail.com";
		  String claveCorreo = "nhxnogsxyobwwbzl";
		  String correoRecibe=email;
		try {
			// El correo sesi�n
			Properties props = new Properties();
			// Informaci�n del servidor de correo de almacenamiento
			props.put("mail.smtp.host", correoEnvia);
			// Al mismo tiempo, a trav�s de la verificaci�n
			props.put("mail.smtp.auth", "true");
			// Seg�n la naturaleza de la construcci�n de un nuevo correo sesi�n
			Session mailSession = Session.getInstance(props);
			// Nueva sesi�n un mensaje por correo de objetos
			MimeMessage message = new MimeMessage(mailSession);
			// Configuraci�n de correo
			String nick = javax.mail.internet.MimeUtility.encodeText(nickName);
			// Preferencias de la direcci�n del remitente
			message.setFrom(new InternetAddress(nick + "<" + correoRecibe + ">"));
			// Preferencias de los beneficiarios, y fijar su tipo de recepci�n para to
			InternetAddress to = new InternetAddress(email);
			message.setRecipient(Message.RecipientType.TO, to);
			// Preferencias de la partida
			message.setSubject(title);
			// Establecer el contenido de la Carta,
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			// Contenido
			mbp1.setText(content);
			mp.addBodyPart(mbp1);
			// Preferencias del Anexo
			message.setContent(mp, "text/html;charset=UTF-8");
			// Est� escrito
			message.setSentDate(new Date());
			// El almacenamiento electr�nico de la informaci�n
			message.saveChanges();
			// Enviar correo
			Transport transport = mailSession.getTransport("smtp");
			// A modo de registro SMTP buz�n, el primer par�metro es enviar un correo con la direcci�n de correo del servidor SMTP. El segundo par�metro para el nombre de usuario y contrase�a para el tercer par�metro
			transport.connect(correoEnvia, correoRecibe, claveCorreo);
			// Enviar mensajes de correo electr�nico, de los cuales el segundo par�metro es todo lo que se ha establecido en virtud de la buena direcci�n del destinatario
			transport.sendMessage(message, message.getAllRecipients());
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
