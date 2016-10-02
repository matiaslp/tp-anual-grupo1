package procesos;

import java.io.File;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.UsuariosFactory;
import autentification.funciones.FuncBajaPOIs;
import db.DB_POI;
import db.DB_Usuarios;
import poi.Banco;
import poi.LocalComercial;

public class TestBajaPOI {

	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();

	@Before
	public void init() {
		dbPOI = DB_POI.getInstance();
		Autenticador = AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", Rol.ADMIN);
	}

	@Test
	public void testBorrar() {
		// Corro el proceso
		// String filePath = (new File (".").getAbsolutePath ())+
		// "\\src\\test\\java\\procesos\\bajaPois.json";
		String filePath = (new File (".").getAbsolutePath ()) + "\\src\\test\\java\\procesos\\bajaPois.json";
		// Creo los locales comerciales pero solo agrego el 1, esperando que los otros 2 los cree el proceso
		LocalComercial local1 = new LocalComercial();
		Banco banco1 = new Banco();
		
		local1.setNombre("local1");
		String[] etiquetas1 = { "matadero", "heladeria" };
		local1.setEtiquetas(etiquetas1);
		local1.setFechaBaja(new DateTime());

		banco1.setNombre("banco1");
		banco1.setFechaBaja(new DateTime());

		dbPOI.agregarPOI(local1);
		dbPOI.agregarPOI(banco1);

		Usuario admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncBajaPOIs funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false, filePath);

		Assert.assertNull(dbPOI.getPOIbyNombre("local1"));
		Assert.assertNull(dbPOI.getPOIbyNombre("banco1"));
	}
}
