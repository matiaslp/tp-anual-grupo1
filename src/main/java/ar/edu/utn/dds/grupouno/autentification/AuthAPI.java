package ar.edu.utn.dds.grupouno.autentification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncAgregarAcciones;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBusquedaPOI;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncCambiarEstadoAuditoria;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncCambiarEstadoGenerarLog;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncCambiarEstadoMail;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncCambiarEstadoNotificarBusquedaLarga;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncMultiple;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncObtenerInfoPOI;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncReporteBusquedaPorUsuario;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncReporteBusquedasPorFecha;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import ar.edu.utn.dds.grupouno.db.DB_Sesiones;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class AuthAPI {

	private static AuthAPI instance = null;

	public static AuthAPI getInstance() {
		if (instance == null)
			instance = new AuthAPI();
		return instance;
	}

	private List<Accion> Acciones;

	public List<Accion> getAcciones(){
		return Acciones;
	}

	public AuthAPI() {
		Acciones = new ArrayList<Accion>();
		Acciones.add(new FuncReporteBusquedaPorUsuario());
		Acciones.add(new FuncReporteBusquedasPorFecha());
		Acciones.add(new FuncReporteCantidadResultadosPorTerminal());
		Acciones.add(new FuncCambiarEstadoMail());
		Acciones.add(new FuncActualizacionLocalesComerciales());
		Acciones.add(new FuncAgregarAcciones());
		Acciones.add(new FuncBajaPOIs());
		Acciones.add(new FuncObtenerInfoPOI());
		Acciones.add(new FuncBusquedaPOI());
		Acciones.add(new FuncMultiple());
		Acciones.add(new FuncCambiarEstadoNotificarBusquedaLarga());
		Acciones.add(new FuncCambiarEstadoAuditoria());
		Acciones.add(new FuncCambiarEstadoGenerarLog());
	}
	
	public Accion getAccion(String nombre) {
		
		for (Accion accion : this.getAcciones()) {
			if (accion.getNombreFuncion().equals(nombre))
				return accion;
		}
		return null;
	}


	public boolean agregarFuncionalidad(String funcionalidad, Usuario user) {
		if (user.getFuncionalidad(funcionalidad)!=null) {
			return false; // ya existe en el usuario
		} else {
			for(Accion accion : this.getAcciones())
				if(accion.getNombreFuncion().equals(funcionalidad)){
					List<Rol> roles = accion.getRoles();
					for(Rol rol : roles){
						if(rol.equals(user.getRol())){
							user.agregarFuncionalidad(accion);
							return true;
						}
					}
				}

			return false; //no tiene permiso
		}
	}

	public boolean sacarFuncionalidad(String funcionalidad, Usuario user) {
		if(user != null){
		return user.getFuncionalidades().remove(user.getFuncionalidad(funcionalidad));}
		return false;
	}

	public String iniciarSesion(String user, String pass) {

		// LA PASS YA DEBERIA LLEGAR HASHEADA AL ENTRAR A ESTA FUNCION

		for (Usuario usuario : Repositorio.getInstance().usuarios().getListaUsuarios()) {
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

}
