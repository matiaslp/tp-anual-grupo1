package autentification;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import autentification.funciones.FuncActualizacionLocalesComerciales;
import autentification.funciones.FuncAgregarAcciones;
import autentification.funciones.FuncBajaPOIs;
import autentification.funciones.FuncBusquedaPOI;
import autentification.funciones.FuncReporteBusquedaPorUsuario;
import autentification.funciones.FuncReporteBusquedasPorFecha;
import autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import autentification.funciones.FuncEnviarMail;
import autentification.funciones.FuncMultiple;
import autentification.funciones.FuncObtenerInfoPOI;
import db.DB_Sesiones;
import db.DB_Usuarios;

public class AuthAPI {

	private static AuthAPI instance = null;

	public static AuthAPI getInstance() {
		if (instance == null)
			instance = new AuthAPI();
		return instance;
	}

	public static Map<String, Accion> Acciones;

	public AuthAPI() {
		Acciones = new HashMap<String, Accion>();
		Acciones.put("reporteBusquedaPorUsuario", new FuncReporteBusquedaPorUsuario());
		Acciones.put("reporteBusquedasPorFecha", new FuncReporteBusquedasPorFecha());
		Acciones.put("reportecantidadResultadosPorTerminal", new FuncReporteCantidadResultadosPorTerminal());
		Acciones.put("enviarMail", new FuncEnviarMail());
		Acciones.put("actualizacionLocalesComerciales", new FuncActualizacionLocalesComerciales());
		Acciones.put("agregarAcciones", new FuncAgregarAcciones());
		Acciones.put("bajaPOIs", new FuncBajaPOIs());
		Acciones.put("obtenerInfoPOI", new FuncObtenerInfoPOI());
		Acciones.put("busquedaPOI", new FuncBusquedaPOI());
		Acciones.put("procesoMultiple", new FuncMultiple());
	}

	public boolean buscarUsuarioEnLista(String username) {
		// ESTO NO DEBERIA IR EN DB_USUARIO?
		for (Usuario unUsuario : listaUsuarios) {
			if (unUsuario.getUsername() == username) {
				return true;
			}
		}
		return false;

	}

	public Usuario consegirUsuarioDeLista(String username) {
		// IDEM ANTERIOR
		Usuario usuarioNoEncontrado = null;
		for (Usuario unUsuario : listaUsuarios) {
			if (unUsuario.getUsername() == username) {
				return unUsuario;
			}
		}
		return usuarioNoEncontrado;

	}

	public static boolean agregarFuncionalidad(String funcionalidad, Usuario user) {
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

	public String iniciarSesion(String user, String pass) throws NoSuchAlgorithmException {

		// LA PASS YA DEBERIA LLEGAR HASHEADA AL ENTRAR A ESTA FUNCION,
		// preguntarme si no captan el por que

		for (Usuario usuario : DB_Usuarios.getInstance().getListaUsuarios()) {
			if (usuario.validarUsuarioYPass(user, pass)) {
				String token = generarToken(user, pass);
				DB_Sesiones.getInstance().getDiccionarioTokenUser().put(token, user);
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

		if (DB_Sesiones.getInstance().getDiccionarioTokenUser().get(Token) != null) {
			return true;
		}

		return false;
	}

	public Map<String, Accion> getAcciones() {
		return Acciones;
	}

}
