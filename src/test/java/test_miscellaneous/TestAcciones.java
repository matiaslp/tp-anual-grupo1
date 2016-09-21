package test_miscellaneous;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import db.DB_Usuarios;

public class TestAcciones {

	private Usuario user;
	private AuthAPI Autenticador;
	private DB_Usuarios DBU;

	@Before
	public void init() {
		Autenticador = AuthAPI.getInstance();
		DBU= DB_Usuarios.getInstance();

		user = new Usuario("usuario","password",Rol.ADMIN);

		user.getFuncionalidades().put("enviarMail", AuthAPI.Acciones.get("enviarMail"));

		Autenticador = AuthAPI.getInstance();

		DBU.agregarUsuarioALista(user);
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
		Assert.assertTrue(user.chequearFuncionalidad("enviarMail"));
	}

}
