package test_procesos;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncBusquedaPorUsuario;
import autentification.funciones.FuncBusquedasPorFecha;
import autentification.funciones.FuncCantidadResultadosPorTerminal;
import autentification.funciones.FuncEnviarMail;
import email.EnviarEmail;
import helpers.LeerProperties;
import procesos.AgregarAcciones;

public class TestAgregarAcciones {
	boolean agregado;

	private String listaAcciones[];

	@Before
	public void init() {
		AuthAPI unAuthAPI = AuthAPI.getInstance();
		unAuthAPI.crearUsuario("a", "123", Rol.ADMIN);
		unAuthAPI.crearUsuario("b", "123", Rol.TERMINAL);
		listaAcciones[0] = "busquedaPorUsuario";

	}

	@Test
	public void agregarAccionesAUsuarioTerminal() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("a", listaAcciones);
		Assert.assertTrue(agregado);
	}

}
