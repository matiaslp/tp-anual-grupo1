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
import autentification.funciones.FuncCambiarEstadoAuditoria;
import autentification.funciones.FuncReporteBusquedaPorUsuario;
import autentification.funciones.FuncReporteBusquedasPorFecha;
import autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import autentification.funciones.FuncCambiarEstadoMail;
import autentification.funciones.FuncCambiarEstadoNotificarBusquedaLarga;
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
		Acciones.put("cambiarEstadoMail", new FuncCambiarEstadoMail());
		Acciones.put("actualizacionLocalesComerciales", new FuncActualizacionLocalesComerciales());
		Acciones.put("agregarAcciones", new FuncAgregarAcciones());
		Acciones.put("bajaPOIs", new FuncBajaPOIs());
		Acciones.put("obtenerInfoPOI", new FuncObtenerInfoPOI());
		Acciones.put("busquedaPOI", new FuncBusquedaPOI());
		Acciones.put("procesoMultiple", new FuncMultiple());
		Acciones.put("notificarBusquedaLarga", new FuncCambiarEstadoNotificarBusquedaLarga());
		Acciones.put("auditoria", new FuncCambiarEstadoAuditoria());
	}

	
	public boolean agregarFuncionalidad(String funcionalidad, Usuario user) {
			if (user.getFuncionalidad(funcionalidad)!=null) {
				return false; // ya existe en el usuario
			} else {
				Accion accion = Acciones.get(funcionalidad);
				if(accion ==null){
					return false; //la accion no existe
				}else{
					ArrayList<Rol> roles = accion.getRoles();
					for(Rol rol : roles){
						if(rol.equals(user.getRol())){
							user.agregarFuncionalidad(funcionalidad,accion);
							return true;
						}
					}
				}
				return false; //no tiene permiso
			}
	}
	
	public boolean sacarFuncionalidad(String funcionalidad, Usuario user) {
			if (user.getFuncionalidades().remove(funcionalidad) != null) {
				return true;
			} else {
				return false; // No existe la funcionalidad
			}
	}

	public String iniciarSesion(String user, String pass) {

		// LA PASS YA DEBERIA LLEGAR HASHEADA AL ENTRAR A ESTA FUNCION

		for (Usuario usuario : DB_Usuarios.getInstance().getListaUsuarios()) {
			if (usuario.validarUsuarioYPass(user, pass)) {
				String token = null;
				try {
					token = generarToken(user, pass);
				} catch (NoSuchAlgorithmException e) {
					//No pudo hashear en SHA-256, no pudo iniciar sesion.
					e.printStackTrace();
					return null;
				}
				DB_Sesiones.getInstance().agregarTokenUser(token, user);
				return token;
			}
		}

		return null;
	}
	
	public void cerrarSesion(String user, String token) {
		
		DB_Sesiones.getInstance().removerTokenUser(token, user);
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

	public Boolean validarToken(String token) {

		if (DB_Sesiones.getInstance().validarToken(token) != null) {
			return true;
		}

		return false;
	}

	public Accion getAccion(String funcionalidad) {
		return Acciones.get(funcionalidad);
	}

}
