package test_miscellaneous;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;

public class TestAcciones {

	private Usuario user;
	private AuthAPI Autenticador;

	@Before
	public void init() {
		Autenticador = AuthAPI.getInstance();

		user = new Usuario();
		user.setID(1);
		user.setPassword("password");
		user.setUsername("usuario");
		user.setRol(Rol.ADMIN);
		user.setFuncionalidades(new HashMap<String, Accion>());

		user.getFuncionalidades().put("enviarMail", AuthAPI.Acciones.get("enviarMail"));

		Autenticador = AuthAPI.getInstance();

		Autenticador.getListaUsuarios().add(user);
	}

	@Test
	public void testAgregarUnaAccion() {
		Autenticador.agregarFuncionalidad("unaFuncionalidad", user);
		Assert.assertEquals(user.getFuncionalidades().size(), 2);
	}

	@Test
	public void testAgregarExistente() {
		Assert.assertFalse(Autenticador.agregarFuncionalidad("enviarMail", user));
		Assert.assertEquals(user.getFuncionalidades().size(), 1);
	}

	@Test
	public void testSacarFuncionalidad() {
		Autenticador.sacarFuncionalidad("enviarMail", user);
		Assert.assertEquals(user.getFuncionalidades().size(), 0);
	}

	@Test
	public void testChequeoFuncionalidad() {
		Assert.assertTrue(Autenticador.chequearFuncionalidad("enviarMail", user));
	}

}
