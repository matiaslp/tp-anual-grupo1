package ar.edu.utn.dds.grupouno.autentification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class AuthAPI {

	private static AuthAPI instance = null;

	public static AuthAPI getInstance() {
		if (instance == null) {
			instance = new AuthAPI();
		}
		return instance;
	}

	private List<Accion> acciones;
	private List<Rol> roles;

	public List<Accion> getAcciones() {
		acciones = Repositorio.getInstance().usuarios().getListadoAcciones();
		return acciones;

	}

	public List<Rol> getRoles() {
		roles = Repositorio.getInstance().usuarios().getListadoRoles();
		return roles;
	}

	public AuthAPI() {

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

		ArrayList<Accion> listAcciones = Repositorio.getInstance().usuarios().getListadoAcciones();
		acciones = new ArrayList<Accion>();
		if (listAcciones == null || listAcciones.size() == 0) {
			Accion funcReporteBusquedaPorUsuario, funcReporteBusquedasPorFecha,
					funcReporteCantidadResultadosPorTerminal, funcCambiarEstadoMail,
					funcActualizacionLocalesComerciales, funcAgregarAcciones, funcBajaPOIs, funcObtenerInfoPOI,
					funcBusquedaPOI, funcMultiple, funcCambiarEstadoNotificarBusquedaLarga, funcCambiarEstadoAuditoria,
					funcCambiarEstadoGenerarLog;
			funcReporteBusquedaPorUsuario = new FuncReporteBusquedaPorUsuario(getRol("ADMIN"));
			funcReporteBusquedasPorFecha = new FuncReporteBusquedasPorFecha(getRol("ADMIN"));
			funcReporteCantidadResultadosPorTerminal = new FuncReporteCantidadResultadosPorTerminal(getRol("ADMIN"));
			funcCambiarEstadoMail = new FuncCambiarEstadoMail(getRol("ADMIN"));
			funcActualizacionLocalesComerciales = new FuncActualizacionLocalesComerciales(getRol("ADMIN"));
			funcAgregarAcciones = new FuncAgregarAcciones(getRol("ADMIN"));
			funcBajaPOIs = new FuncBajaPOIs(getRol("ADMIN"));
			funcObtenerInfoPOI = new FuncObtenerInfoPOI(getRol("ADMIN"), getRol("TERMINAL"));
			funcBusquedaPOI = new FuncBusquedaPOI(getRol("ADMIN"), getRol("TERMINAL"));
			funcMultiple = new FuncMultiple(getRol("ADMIN"));
			funcCambiarEstadoNotificarBusquedaLarga = new FuncCambiarEstadoNotificarBusquedaLarga(getRol("TERMINAL"));
			funcCambiarEstadoAuditoria = new FuncCambiarEstadoAuditoria(getRol("TERMINAL"));
			funcCambiarEstadoGenerarLog = new FuncCambiarEstadoGenerarLog(getRol("ADMIN"), getRol("TERMINAL"));

			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcReporteBusquedaPorUsuario);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcReporteBusquedasPorFecha);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcReporteCantidadResultadosPorTerminal);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcCambiarEstadoMail);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcActualizacionLocalesComerciales);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcAgregarAcciones);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcBajaPOIs);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcObtenerInfoPOI);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcBusquedaPOI);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcMultiple);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcCambiarEstadoNotificarBusquedaLarga);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcCambiarEstadoAuditoria);
			Repositorio.getInstance().usuarios().persistirAccion((Accion) funcCambiarEstadoGenerarLog);

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
					Set<Rol> rols = accion.getRoles();
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
				Repositorio.getInstance().sesiones().agregarSesion(token, user);
				return token;
			}
		}

		return null;
	}

	public void cerrarSesion(String user, String token) {

		Repositorio.getInstance().sesiones().removerSesion(token, user);
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

		if (token != null && Repositorio.getInstance().sesiones().validarToken(token) != null) {
			return true;
		}

		return false;
	}

}
