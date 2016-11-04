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
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

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
		Repositorio.getInstance().pois();
	//	DB_POI.getListado().clear();
		Autenticador = AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", "ADMIN");
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

		Repositorio.getInstance().pois().agregarPOI(local1);
		Repositorio.getInstance().pois().agregarPOI(banco1);

		admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
		tokenAdmin=AuthAPI.getInstance().iniciarSesion("admin", "123");
		funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
	}

	@Test
	public void testBorrarFechaOk() {

		
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false, filePath);
		
		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("local1"));
		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("banco1"));
	}
	
	@Test
	public void testBorrarFechasDistintas(){
		Repositorio.getInstance().pois().getPOIbyNombre("local1").get(0).setFechaBaja(new DateTime(1900,1,1,0,0));
		
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false, filePath);
		
		Assert.assertNotNull(Repositorio.getInstance().pois().getPOIbyNombre("local1"));
		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("banco1"));
		
	}
}
