package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;

public class TestActualizarLocalComercial {
	
	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	
	@Before
	public void init(){
		dbPOI = DB_POI.getInstance();
		Autenticador = AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", Rol.ADMIN);
	}
	
	@Test
	public void testActualizar(){
		
		String filePath = (new File (".").getAbsolutePath ())+"/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";

		//Creo un local para corroborar que se actualizan los locales que ya existen:
		LocalComercial local1 = new LocalComercial();
		local1.setNombre("local1");
		String[] etiquetas = {"mataderos", "heladeria"};
		local1.setEtiquetas(etiquetas);
		dbPOI.agregarPOI(local1);
		
		//Creo los locales que espero obtener despues de ejecutar el proceso:
		LocalComercial localEsperado2 = new LocalComercial();
		localEsperado2.setNombre("local2");
		String[] etiquetas2 = {"juegos", "azul", "moron"};
		localEsperado2.setEtiquetas(etiquetas2);
		
		LocalComercial localEsperado3 = new LocalComercial();
		localEsperado3.setNombre("local3");
		String[] etiquetas3 = {"azul", "helado", "esquina"};
		localEsperado3.setEtiquetas(etiquetas3);
		
		LocalComercial localEsperado4 = new LocalComercial();
		localEsperado4.setNombre("local4");
		String[] etiquetas4 = {"pizzeria", "munro", "violeta"};
		localEsperado4.setEtiquetas(etiquetas4);
		
		//Ejecucion del proceso:
		Usuario admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance().getAccion("actualizacionLocalesComerciales");
		funcion.actualizarLocales(admin, tokenAdmin, 0, false, filePath);

		//Busco las modificaciones para corroborar que se corrio correctamente
		POI local2Actualizado = dbPOI.getPOIbyNombre("local2");
		POI local3Actualizado = dbPOI.getPOIbyNombre("local3");
		POI local4Actualizado = dbPOI.getPOIbyNombre("local4");
		
		//Compruebo que el local 2 y 3 hayan sido creados:
		Assert.assertNotNull(local2Actualizado);
		Assert.assertNotNull(local3Actualizado);
		
		//Compruebo que los locales 2 y 3 se hayan creado correctamente:
		Assert.assertTrue(local2Actualizado.compararEtiquetas(localEsperado2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(localEsperado3));
		
		//Compruebo que el local 4 se actualizo correctamente:
		Assert.assertTrue(local4Actualizado.compararEtiquetas(localEsperado4));
	}
}
