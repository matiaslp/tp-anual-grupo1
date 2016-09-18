package test_procesos;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Accion;
import autentification.Rol;
import autentification.Usuario;
import email.EnviarEmail;
import helpers.LeerProperties;
import procesos.AgregarAcciones;

public class TestAgregarAcciones {
	boolean agregado;
	@SuppressWarnings("unused")
	private Map<String, Accion> acciones;
	private Usuario unUsuarioTerminal;
	private Usuario unUsuarioAdministrador;
	private Accion primeraAccion;
	private Accion segundaAccion;
	private Accion terceraAccion;
	@Before
	public void init() {
		//CREO LOS DOS TIPOS DE USUARIOS QUE HAY
		unUsuarioTerminal= new Usuario();
		unUsuarioTerminal.setRol(Rol.TERMINAL);
		unUsuarioAdministrador= new Usuario();
		unUsuarioAdministrador.setRol(Rol.ADMIN);
		//CREO LISTA DE ACCIONES
		acciones = new HashMap<String, Accion>();
		//CREO ACCIONES
		primeraAccion=new Accion();
		segundaAccion=new Accion();
		terceraAccion=new Accion();
		//AGGREGO ACCIONES A LA LISTA
		acciones.put("primera", primeraAccion);
		acciones.put("segunda", segundaAccion);
		acciones.put("tercera", terceraAccion);
	}

	@Test
	public void agregarAccionesAUsuarioTerminal() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesA(unUsuarioTerminal,acciones);
		Assert.assertTrue(agregado);
	}
	@Test
	public void agregarAccionesAUsuarioAdministrador() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesA(unUsuarioAdministrador,acciones);
		Assert.assertTrue(agregado);
	}
}
