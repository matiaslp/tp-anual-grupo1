package autentification;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import autentification.funciones.FuncBusquedaPorUsuario;
import autentification.funciones.FuncBusquedasPorFecha;
import autentification.funciones.FuncCantidadResultadosPorTerminal;
import autentification.funciones.FuncEnviarMail;

public class AuthAPI {

	private static AuthAPI instance = null;

	public static AuthAPI getInstance() {
		if (instance == null)
			instance = new AuthAPI();
		return instance;
	}

	Map<String, String> diccionarioTokenUser = new HashMap<String, String>();
	public static Map<String, Accion> Acciones;
	// ESTA LISTA DE USUARIOS DEBERIA SER LA BASE DE DATOS
	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

	public AuthAPI() {
		diccionarioTokenUser = new HashMap<String, String>();
		Acciones = new HashMap<String, Accion>();
		// ESTA LISTA DE USUARIOS DEBERIA SER LA BASE DE DATOS
		listaUsuarios = new ArrayList<Usuario>();
		Acciones.put("busquedaPorUsuario", new FuncBusquedaPorUsuario());
		Acciones.put("busquedasPorFecha", new FuncBusquedasPorFecha());
		Acciones.put("cantidadResultadosPorTerminal", new FuncCantidadResultadosPorTerminal());
		Acciones.put("enviarMail", new FuncEnviarMail());
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public Usuario crearUsuario(String username, String password, Rol rol) {
		Usuario user = new Usuario();
		user.setID(listaUsuarios.size() + 1);
		user.setPassword(password);
		user.setUsername(username);
		user.setRol(rol);
		if (rol.getNombre().equals("admin")) {
			user.setFuncionalidades(new HashMap<String, Accion>());
		}
		return user;
	}

	public boolean agregarUsuarioALista(Usuario user) {
		for (Usuario usuario : listaUsuarios) {
			if (user.getUsername().equals(usuario.getUsername()) || user.getID() == usuario.getID()) {
				return false;
			}
		}
		listaUsuarios.add(user);
		return true;
	}

	public boolean agregarFuncionalidad(String funcionalidad, Usuario user) {
		if (user.getRol().equals(Rol.ADMIN)) {
			if (user.getFuncionalidades().get(funcionalidad) != null) {
				return false; // ya existe
			} else {
				if (user.getFuncionalidades().put(funcionalidad, Acciones.get(funcionalidad)) != null) {
					return true;
				} else {
					return false; // la funcionalidad no existe.
				}
			}
		} else {
			return false; // El usuario no es admin
		}
	}

	public boolean sacarFuncionalidad(String funcionalidad, Usuario user) {
		if (user.getRol().getNombre().equals("admin")) {
			if (user.getFuncionalidades().remove(funcionalidad) != null) {
				return true;
			} else {
				return false; // No existe la funcionalidad
			}
		} else {
			return false; // El usuario no es admin
		}
	}

	public boolean chequearFuncionalidad(String funcionalidad, Usuario user) {
		if (user.getFuncionalidades().get(funcionalidad) != null) {
			return true;
		} else {
			return false;
		}
	}

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

	public Map<String, Accion> getAcciones() {
		return Acciones;
	}

}
