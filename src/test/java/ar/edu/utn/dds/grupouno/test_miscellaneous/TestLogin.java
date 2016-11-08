package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestLogin {
	private Repositorio DBU;
	private AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	Usuario admintest, term;

	@Before
	public void init() {
		DBU = Repositorio.getInstance();
		Autenticador = AuthAPI.getInstance();

		admintest = fact.crearUsuario("adminTestLogin", "password", "ADMIN");
		DBU.getEm().clear();
		term = fact.crearUsuario("terminal", "password", "TERMINAL");
		DBU.usuarios().persistirUsuario(admintest);
		DBU.usuarios().persistirUsuario(term);


	}

	@Test
	public void probarHasherLongitud() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertEquals(64, hash.length());
	}

	@Test
	public void probarHasherIgualdad() throws NoSuchAlgorithmException {
		String hash = Autenticador.hashear(DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertTrue(
				hash.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".toUpperCase()));
	}

	@Test
	public void probarTokenLongitud() throws NoSuchAlgorithmException {
		String token = Autenticador.generarToken(DBU.usuarios().getUsuarioByName("adminTestLogin").getUsername(), DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertEquals(64, token.length());
	}

	@Test
	public void probarRandomToken() throws NoSuchAlgorithmException, InterruptedException {
		String token1 = Autenticador.generarToken(DBU.usuarios().getUsuarioByName("adminTestLogin").getUsername(), DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());
		TimeUnit.SECONDS.sleep(3); // espero a que cambie la hora
		String token2 = Autenticador.generarToken(DBU.usuarios().getUsuarioByName("adminTestLogin").getUsername(), DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());

		Assert.assertFalse(token1.equals(token2));
	}

	@Test
	public void testInicioDeSesionCorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion(DBU.usuarios().getUsuarioByName("adminTestLogin").getUsername(), DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertFalse(token == null);// esto con equals rompe
	}

	@Test
	public void testInicioDeSesionIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("unUsuario", "unaPass");
		Assert.assertTrue(token == null); // idem
	}

	@Test
	public void testvalidarTokenCorrecto() throws NoSuchAlgorithmException {

		String token = Autenticador.iniciarSesion(DBU.usuarios().getUsuarioByName("adminTestLogin").getUsername(), DBU.usuarios().getUsuarioByName("adminTestLogin").getPassword());
		Assert.assertTrue(Autenticador.validarToken(token));
	}

	@Test
	public void testvalidarTokenIncorrecto() throws NoSuchAlgorithmException {
		String token = Autenticador.iniciarSesion("random", "contrasenia");
		Assert.assertFalse(Autenticador.validarToken(token));
	}

	@Test
	public void testCrearUsuario() {
		fact.crearUsuario("username", "password", "TERMINAL");
		Assert.assertTrue(DBU.usuarios().getUsuarioByName("username") != null && DBU.usuarios().getUsuarioByName("username").getPassword().equals("password")
				&& DBU.usuarios().getUsuarioByName("username").getRol().equals("TERMINAL"));
	}

	@Test
	public void testAgregarUsuarioTrue() {
		Long tamanio = (long) DBU.usuarios().getListaUsuarios().size();
		fact.crearUsuario("nuevo", "password", "ADMIN");
		Assert.assertTrue(DBU.usuarios().getListaUsuarios().size() == (tamanio+1));
	}

	@Test
	public void testagregarFuncionalidadTerminalSinPermiso() {
		Assert.assertFalse(Autenticador.agregarFuncionalidad("cambiarEstadoMail", DBU.usuarios().getUsuarioByName("terminal")));
	}
	
	@Test
	public void testAgregarFuncionalidadExistente(){
		Assert.assertFalse(Autenticador.agregarFuncionalidad("busquedaPOI", DBU.usuarios().getUsuarioByName("terminal")));
	}
	
	@Test
	public void sacarFuncionalidadtest(){
		Autenticador.sacarFuncionalidad("busquedaPOI", DBU.usuarios().getUsuarioByName("terminal"));
		Assert.assertTrue(DBU.usuarios().getUsuarioByName("terminal").getFuncionalidad("busquedaPOI")==null);
	
	}
	
	@Test
	public void testAgregarFuncionalidadConPermiso(){
		Autenticador.sacarFuncionalidad("busquedaPOI", DBU.usuarios().getUsuarioByName("terminal"));
		Autenticador.agregarFuncionalidad("busquedaPOI", DBU.usuarios().getUsuarioByName("terminal"));
		Assert.assertTrue(DBU.usuarios().getUsuarioByName("terminal").getFuncionalidad("busquedaPOI")!=null);
	}

}
