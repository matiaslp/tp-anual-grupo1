package test_miscellaneous;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.UsuariosFactory;
import db.DB_Usuarios;

public class TestLogin {
	private DB_Usuarios DBU;
	private AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();

	@Before
	public void init() {
		DBU = DB_Usuarios.getInstance();
		Autenticador = AuthAPI.getInstance();

		
		fact.crearUsuario("adminTestLogin", "password", Rol.ADMIN);
		
		fact.crearUsuario("terminal", "password", Rol.TERMINAL);


	}

	@Test
	public void probarHasherLongitud() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(DBU.getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertEquals(64, hash.length());
	}

	@Test
	public void probarHasherIgualdad() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(DBU.getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertTrue(
				hash.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".toUpperCase()));
	}

	@Test
	public void probarTokenLongitud() throws NoSuchAlgorithmException {
		String token = Autenticador.generarToken(DBU.getUsuarioByName("adminTestLogin").getUsername(), DBU.getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertEquals(64, token.length());
	}

	@Test
	public void probarRandomToken() throws NoSuchAlgorithmException, InterruptedException {
		String token1 = Autenticador.generarToken(DBU.getUsuarioByName("adminTestLogin").getUsername(), DBU.getUsuarioByName("adminTestLogin").getPassword());
		TimeUnit.SECONDS.sleep(3); // espero a que cambie la hora
		String token2 = Autenticador.generarToken(DBU.getUsuarioByName("adminTestLogin").getUsername(), DBU.getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertFalse(token1.equals(token2));
	}

	@Test
	public void testInicioDeSesionCorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion(DBU.getUsuarioByName("adminTestLogin").getUsername(), DBU.getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertFalse(token == null);// esto con equals rompe
	}

	@Test
	public void testInicioDeSesionIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("unUsuario", "unaPass");
		Assert.assertTrue(token == null); // idem
	}

	@Test
	public void testvalidarTokenCorrecto() throws NoSuchAlgorithmException {

		String token = Autenticador.iniciarSesion(DBU.getUsuarioByName("adminTestLogin").getUsername(), DBU.getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertTrue(Autenticador.validarToken(token));
	}

	@Test
	public void testvalidarTokenIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("random", "contrasenia");
		Assert.assertFalse(Autenticador.validarToken(token));
	}

	@Test
	public void testCrearUsuario() {
		fact.crearUsuario("username", "password", Rol.TERMINAL);
		Assert.assertTrue(DBU.getUsuarioByName("username") != null && DBU.getUsuarioByName("username").getPassword().equals("password")
				&& DBU.getUsuarioByName("username").getRol().equals(Rol.TERMINAL));
	}

	@Test
	public void testAgregarUsuarioFalso() {
		Assert.assertFalse(DBU.agregarUsuarioALista(DBU.getUsuarioByName("adminTestLogin")));
	}

	@Test
	public void testAgregarUsuarioTrue() {
		Long tamanio = (long) DBU.getListaUsuarios().size();
		fact.crearUsuario("nuevo", "password", Rol.ADMIN);
		Assert.assertTrue(DBU.getListaUsuarios().size() == (tamanio+1));
	}

	@Test
	public void testagregarFuncionalidadTerminalSinPermiso() {
		Assert.assertFalse(Autenticador.agregarFuncionalidad("cambiarEstadoMail", DBU.getUsuarioByName("terminal")));
	}
	
	@Test
	public void testAgregarFuncionalidadExistente(){
		Assert.assertFalse(Autenticador.agregarFuncionalidad("busquedaPOI", DBU.getUsuarioByName("terminal")));
	}
	
	@Test
	public void sacarFuncionalidadtest(){
		Autenticador.sacarFuncionalidad("busquedaPOI", DBU.getUsuarioByName("terminal"));
		Assert.assertTrue(DBU.getUsuarioByName("terminal").getFuncionalidad("busquedaPOI")==null);
	}
	
	@Test
	public void testAgregarFuncionalidadConPermiso(){
		Autenticador.sacarFuncionalidad("busquedaPOI", DBU.getUsuarioByName("terminal"));
		Autenticador.agregarFuncionalidad("busquedaPOI", DBU.getUsuarioByName("terminal"));
		Assert.assertTrue(DBU.getUsuarioByName("terminal").getFuncionalidad("busquedaPOI")!=null);
	}

}
