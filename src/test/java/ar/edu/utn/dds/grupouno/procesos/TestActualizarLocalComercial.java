package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncActualizacionLocalesComerciales;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestActualizarLocalComercial {
	
	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	Repositorio repositorio;
	String filePath;
	LocalComercial local1, local2, local3, local4;
	
	@Before
	public void init(){
		Autenticador = AuthAPI.getInstance();
		repositorio = Repositorio.getInstance();
		repositorio.usuarios().persistirUsuario(fact.crearUsuario("admin", "123", "ADMIN"));
		filePath = (new File (".").getAbsolutePath ()) + 
				"/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";
		initLocales();
	}
	
	private void initLocales(){
		//Creo un local para corroborar que se actualizan los locales que ya existen:
		local1 = new LocalComercial();
		local1.setNombre("local1");
		String[] etiquetas = {"mataderos", "heladeria"};
		local1.setEtiquetas(etiquetas);
		repositorio.pois().agregarPOI(local1);
				
		//Creo los locales que espero obtener despues de ejecutar el proceso:
		local2 = new LocalComercial();
		local2.setNombre("local2");
		String[] etiquetas2 = {"juegos", "azul", "moron"};
		local2.setEtiquetas(etiquetas2);
				
		local3 = new LocalComercial();
		local3.setNombre("local3");
		String[] etiquetas3 = {"azul", "helado", "esquina"};
		local3.setEtiquetas(etiquetas3);
				
		local4 = new LocalComercial();
		local4.setNombre("local4");
		String[] etiquetas4 = {"pizzeria", "munro", "violeta"};
		local4.setEtiquetas(etiquetas4);
	}

	@Test
	public void testActualizar(){
		//Ejecucion del proceso:
		Usuario admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance().
				getAccion("actualizacionLocalesComerciales");
		funcion.actualizarLocales(admin, tokenAdmin, 0, false, filePath);
		
		//Busco las modificaciones para corroborar que se corrio correctamente
		POI local2Actualizado = repositorio.pois().getPOIbyNombre("local2").get(0);
		POI local3Actualizado = repositorio.pois().getPOIbyNombre("local3").get(0);
		POI local4Actualizado = repositorio.pois().getPOIbyNombre("local4").get(0);
		
		//Compruebo que el local 2 y 3 hayan sido creados:
		Assert.assertNotNull(local2Actualizado);
		Assert.assertNotNull(local3Actualizado);
		
		//Compruebo que los locales 2 y 3 se hayan creado correctamente:
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
		
		//Compruebo que el local 4 se actualizo correctamente:
		Assert.assertTrue(local4Actualizado.compararEtiquetas(local4));
	}
	
		
	
//	@Test
//	public void testActualizar(){
//		
//		String filePath = (new File (".").getAbsolutePath ())+"/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";
//
//		repositorio.usuarios().actualizarUsuario(); //ESTO DEBERIA SER UN UPDATE GENERAL
//		//FALLA POR LA PARTE DE PROCESOS
//		
//		//Busco las modificaciones para corroborar que se corrio correctamente
//		POI local2Actualizado = repositorio.pois().getPOIbyNombre("local2").get(0);
//		POI local3Actualizado = repositorio.pois().getPOIbyNombre("local3").get(0);
//		POI local4Actualizado = repositorio.pois().getPOIbyNombre("local4").get(0);
//		
//		//Compruebo que el local 2 y 3 hayan sido creados:
//		Assert.assertNotNull(local2Actualizado);
//		Assert.assertNotNull(local3Actualizado);
//		
//		//Compruebo que los locales 2 y 3 se hayan creado correctamente:
//		Assert.assertTrue(local2Actualizado.compararEtiquetas(localEsperado2));
//		Assert.assertTrue(local3Actualizado.compararEtiquetas(localEsperado3));
//		
//		//Compruebo que el local 4 se actualizo correctamente:
//		Assert.assertTrue(local4Actualizado.compararEtiquetas(localEsperado4));
//	}
}
