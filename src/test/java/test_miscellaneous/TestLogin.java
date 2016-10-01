package test_miscellaneous;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import db.DB_Usuarios;

public class TestLogin {
	private Usuario terminal;
	private Usuario prueba;
	private DB_Usuarios DBU;
	private AuthAPI Autenticador;

	@Before
	public void init() {
		DBU = DB_Usuarios.getInstance();
		Autenticador = AuthAPI.getInstance();

		prueba = new Usuario("usuario", "password", Rol.ADMIN);
		prueba.getFuncionalidades().put("enviarMail", AuthAPI.Acciones.get("enviarMail"));

		DBU.agregarUsuarioALista(prueba);

		terminal = new Usuario("terminal", "password", Rol.TERMINAL);

	}

	@Test
	public void probarHasherLongitud() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(prueba.getPassword());
		Assert.assertEquals(64, hash.length());
	}

	@Test
	public void probarHasherIgualdad() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(prueba.getPassword());

		Assert.assertTrue(
				hash.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".toUpperCase()));
	}

	@Test
	public void probarTokenLongitud() throws NoSuchAlgorithmException {
		String token = Autenticador.generarToken(prueba.getUsername(), prueba.getPassword());

		Assert.assertEquals(64, token.length());
	}

	@Test
	public void probarRandomToken() throws NoSuchAlgorithmException, InterruptedException {
		String token1 = Autenticador.generarToken(prueba.getUsername(), prueba.getPassword());
		TimeUnit.SECONDS.sleep(3); // espero a que cambie la hora
		String token2 = Autenticador.generarToken(prueba.getUsername(), prueba.getPassword());

		Assert.assertFalse(token1.equals(token2));
	}

	@Test
	public void testInicioDeSesionCorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion(prueba.getUsername(), prueba.getPassword());
		Assert.assertFalse(token == null);// esto con equals rompe
	}

	@Test
	public void testInicioDeSesionIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("unUsuario", "unaPass");
		Assert.assertTrue(token == null); // idem
	}

	@Test
	public void testvalidarTokenCorrecto() throws NoSuchAlgorithmException {

		String token = Autenticador.iniciarSesion(prueba.getUsername(), prueba.getPassword());
		Assert.assertTrue(Autenticador.validarToken(token));
	}

	@Test
	public void testvalidarTokenIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("random", "contrasenia");
		Assert.assertFalse(Autenticador.validarToken(token));
	}

	@Test
	public void testCrearUsuario() {
		Usuario test = new Usuario("username", "password", Rol.TERMINAL);
		Assert.assertTrue(test.getUsername().equals("username") && test.getPassword().equals("password")
				&& test.getRol().equals(Rol.TERMINAL));
	}

	@Test
	public void testAgregarUsuarioFalso() {
		Assert.assertFalse(DBU.agregarUsuarioALista(prueba));
	}

	@Test
	public void testAgregarUsuarioTrue() {
		DBU.agregarUsuarioALista(new Usuario("nuevo", "password", Rol.ADMIN));
		Assert.assertTrue(DBU.getListaUsuarios().size() > 1);
	}

	@Test
	public void testagregarFuncionalidadTerminalSinPermiso() {
		Assert.assertFalse(Autenticador.agregarFuncionalidad("enviarMail", terminal));
	}
	
	@Test
	public void testAgregarFuncionalidadExistente(){
		Assert.assertFalse(Autenticador.agregarFuncionalidad("busquedaPOI", terminal));
	}
	
	@Test
	public void sacarFuncionalidadtest(){
		Autenticador.sacarFuncionalidad("busquedaPOI", terminal);
		Assert.assertTrue(terminal.getFuncionalidad("busquedaPOI")==null);
	}
	
	@Test
	public void testAgregarFuncionalidadConPermiso(){
		Autenticador.sacarFuncionalidad("busquedaPOI", terminal);
		Autenticador.agregarFuncionalidad("busquedaPOI", terminal);
		Assert.assertTrue(terminal.getFuncionalidad("busquedaPOI")!=null);
	}

}
