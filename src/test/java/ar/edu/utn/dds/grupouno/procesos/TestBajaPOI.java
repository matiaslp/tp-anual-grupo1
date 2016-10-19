package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;

public class TestBajaPOI {
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	String filePath;
	LocalComercial local1;
	Banco banco1;
	DateTime fecha;
	Usuario admin;
	String tokenAdmin;
	FuncBajaPOIs funcion;
	

	
	@Before
	public void init() {
		DB_POI.getInstance();
		DB_POI.getListado().clear();
		Autenticador = AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", Rol.ADMIN);
		filePath = (new File (".").getAbsolutePath ()) + "/src/test/java/ar/edu/utn/dds/grupouno/procesos/bajaPois.json";
		local1 = new LocalComercial();
		banco1 = new Banco();
		
		fecha = new DateTime(2016,10,18,0,0);
		
		local1.setNombre("local1");
		String[] etiquetas1 = { "matadero", "heladeria" };
		local1.setEtiquetas(etiquetas1);
		local1.setFechaBaja(fecha);

		banco1.setNombre("banco1");
		banco1.setFechaBaja(fecha);

		DB_POI.getInstance().agregarPOI(local1);
		DB_POI.getInstance().agregarPOI(banco1);

		admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
		tokenAdmin=AuthAPI.getInstance().iniciarSesion("admin", "123");
		funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
	}

	@Test
	public void testBorrarFechaOk() {

		
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false, filePath);
		
		Assert.assertNull(DB_POI.getInstance().getPOIbyNombre("local1"));
		Assert.assertNull(DB_POI.getInstance().getPOIbyNombre("banco1"));
	}
	
	@Test
	public void testBorrarFechasDistintas(){
		DB_POI.getInstance().getPOIbyNombre("local1").setFechaBaja(new DateTime(1900,1,1,0,0));
		
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false, filePath);
		
		Assert.assertNotNull(DB_POI.getInstance().getPOIbyNombre("local1"));
		Assert.assertNull(DB_POI.getInstance().getPOIbyNombre("banco1"));
		
	}
}
