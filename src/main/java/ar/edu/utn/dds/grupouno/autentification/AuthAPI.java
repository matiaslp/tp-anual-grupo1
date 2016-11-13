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

	private List<Accion> acciones;
	private List<Rol> roles;

	public List<Accion> getAcciones() {
		return acciones;
	}
	
	public List<Rol> getRoles() {
		return roles;
	}

	public AuthAPI() {

		ArrayList<Accion> listAcciones = Repositorio.getInstance().usuarios().getListadoAcciones();
		acciones = new ArrayList<Accion>();
		if (listAcciones == null || listAcciones.size() == 0) {
			Accion funcReporteBusquedaPorUsuario, funcReporteBusquedasPorFecha,
					funcReporteCantidadResultadosPorTerminal, funcCambiarEstadoMail,
					funcActualizacionLocalesComerciales, funcAgregarAcciones, funcBajaPOIs, funcObtenerInfoPOI,
					funcBusquedaPOI, funcMultiple, funcCambiarEstadoNotificarBusquedaLarga, funcCambiarEstadoAuditoria,
					funcCambiarEstadoGenerarLog;
			funcReporteBusquedaPorUsuario = new FuncReporteBusquedaPorUsuario();
			funcReporteBusquedasPorFecha = new FuncReporteBusquedasPorFecha();
			funcReporteCantidadResultadosPorTerminal = new FuncReporteCantidadResultadosPorTerminal();
			funcCambiarEstadoMail = new FuncCambiarEstadoMail();
			funcActualizacionLocalesComerciales = new FuncActualizacionLocalesComerciales();
			funcAgregarAcciones = new FuncAgregarAcciones();
			funcBajaPOIs = new FuncBajaPOIs();
			funcObtenerInfoPOI = new FuncObtenerInfoPOI();
			funcBusquedaPOI = new FuncBusquedaPOI();
			funcMultiple = new FuncMultiple();
			funcCambiarEstadoNotificarBusquedaLarga = new FuncCambiarEstadoNotificarBusquedaLarga();
			funcCambiarEstadoAuditoria = new FuncCambiarEstadoAuditoria();
			funcCambiarEstadoGenerarLog = new FuncCambiarEstadoGenerarLog();

			Repositorio.getInstance().persistir((Accion)funcReporteBusquedaPorUsuario);
			Repositorio.getInstance().persistir((Accion)funcReporteBusquedasPorFecha);
			Repositorio.getInstance().persistir((Accion)funcReporteCantidadResultadosPorTerminal);
			Repositorio.getInstance().persistir((Accion)funcCambiarEstadoMail);
			Repositorio.getInstance().persistir((Accion)funcActualizacionLocalesComerciales);
			Repositorio.getInstance().persistir((Accion)funcAgregarAcciones);
			Repositorio.getInstance().persistir((Accion)funcBajaPOIs);
			Repositorio.getInstance().persistir((Accion)funcObtenerInfoPOI);
			Repositorio.getInstance().persistir((Accion)funcBusquedaPOI);
			Repositorio.getInstance().persistir((Accion)funcMultiple);
			Repositorio.getInstance().persistir((Accion)funcCambiarEstadoNotificarBusquedaLarga);
			Repositorio.getInstance().persistir((Accion)funcCambiarEstadoAuditoria);
			Repositorio.getInstance().persistir((Accion)funcCambiarEstadoGenerarLog);

			acciones.add(funcReporteBusquedaPorUsuario);
			acciones.add(funcReporteBusquedasPorFecha);
			acciones.add(funcReporteCantidadResultadosPorTerminal);
			acciones.add(funcCambiarEstadoMail);
			acciones.add(funcActualizacionLocalesComerciales);
			acciones.add(funcAgregarAcciones);
			acciones.add(funcBajaPOIs);
			acciones.add(funcObtenerInfoPOI);
			acciones.add(funcBusquedaPOI);
			acciones.add(funcMultiple);
			acciones.add(funcCambiarEstadoNotificarBusquedaLarga);
			acciones.add(funcCambiarEstadoAuditoria);
			acciones.add(funcCambiarEstadoGenerarLog);
		} else {
			for (Accion accion : listAcciones)
				acciones.add(accion);
		}
		
		ArrayList<Rol> listRoles = Repositorio.getInstance().usuarios().getListadoRoles();
		roles = new ArrayList<Rol>();
		if (listRoles == null || listRoles.size() == 0) {
			Rol terminal = new Rol();
			terminal.setValue("TERMINAL");
			Repositorio.getInstance().persistir(terminal);
			Rol admin = new Rol();
			admin.setValue("ADMIN");
			Repositorio.getInstance().persistir(admin);
			roles.add(terminal);
			roles.add(admin);
		} else {
			for (Rol rol : listRoles)
				roles.add(rol);
		}
	}

	public Accion getAccion(String nombre) {

		for (Accion accion : this.getAcciones()) {
			if (accion.getNombreFuncion().equals(nombre))
				return accion;
		}
		return null;
	}
	
	public Rol getRol(String nombre) {

		for (Rol rol : this.getRoles()) {
			if (rol.getValue().equals(nombre))
				return rol;
		}
		return null;
	}

	public boolean agregarFuncionalidad(String funcionalidad, Usuario user) {
		if (user.getFuncionalidad(funcionalidad) != null) {
			return false; // ya existe en el usuario
		} else {
			for (Accion accion : this.getAcciones())
				if (accion.getNombreFuncion().equals(funcionalidad)) {
					List<Rol> rols = accion.getRoles();
					for (Rol rol : rols) {
						if (rol.getValue().equals(user.getRol().getValue())) {
							user.agregarFuncionalidad(accion);
							return true;
						}
					}
				}

			return false; // no tiene permiso
		}
	}

	public boolean sacarFuncionalidad(String funcionalidad, Usuario user) {
		if (user != null) {
			return user.getFuncionalidades().remove(user.getFuncionalidad(funcionalidad));
		}
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
					// No pudo hashear en SHA-256, no pudo iniciar sesion.
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
