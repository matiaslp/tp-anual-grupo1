package autentification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class AuthAPI {

	Map<String, String> diccionarioTokenUser = new HashMap<String, String>();

	// ESTA LISTA DE USUARIOS DEBERIA SER LA BASE DE DATOS
	public ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

	public String iniciarSesion(String user, String pass) throws NoSuchAlgorithmException {

		// LA PASS YA DEBERIA LLEGAR HASHEADA AL ENTRAR A ESTA FUNCION,
		// preguntarme si no captan el por que

		for (Usuario usuario : listaUsuarios) {
			if (usuario.getUsername().equals(user) && usuario.getPassword().equals(pass)) {
				String token = generarToken(user, pass);
				diccionarioTokenUser.put(token, user);
				return token;
			}
		}

		return null;
	}

	public String hashear(String string) throws NoSuchAlgorithmException {
		// Esta funcion en una de esas quizas va en las comunes
		String userPass = string;
		MessageDigest hasher = MessageDigest.getInstance("SHA-256");
		hasher.update(userPass.getBytes());

		return DatatypeConverter.printHexBinary(hasher.digest());
	}

	public String generarToken(String user, String pass) throws NoSuchAlgorithmException {
		Date fecha = new Date();
		fecha.getTime();

		String stringGenerador = user + pass + fecha.toString();

		return hashear(stringGenerador);
	}

	public Boolean validarToken(String Token) {

		if (diccionarioTokenUser.get(Token) != null) {
			return true;
		}

		return false;
	}

}
