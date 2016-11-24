package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.Proceso;
import ar.edu.utn.dds.grupouno.quartz.ProcesoHandler;

public class TestProcesoMultiple {

	DB_POI dbPOI;
	UsuariosFactory fact = new UsuariosFactory();
//	Repositorio repositorio;
	String filePathActualizarLocalesComerciales;
	LocalComercial local1, local2, local3, local4, local1Actualizado, local2Actualizado, local3Actualizado;
	UsuariosFactory ufactory = new UsuariosFactory();
	String[] etiquetas = { "mataderos", "heladeria" };
	String[] etiquetas2 = { "juegos", "azul", "moron" };
	String[] etiquetas3 = { "azul", "helado", "esquina" };
	
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	Usuario admin, adminPrueba, unUsuarioTerminal1;
	String tokenAdmin;
	String filePathAgregarAcciones = (new File (".").getAbsolutePath ()) + 
			"/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregar";
	String filePathRollback = (new File (".").getAbsolutePath ()) + 
			"/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregarRollback";
	int REINTENTOS_MAX = 0;
	
	AgregarAccionesTransaction transaction;
	
	String filePathBajaPoi;
	Banco banco1;
	DateTime fecha;
	
	

	@Before
	public void init() {
		//---------------------------------------------------------------------
		// Init Proceso Locales Comerciales
		//---------------------------------------------------------------------
		
		filePathActualizarLocalesComerciales = (new File(".").getAbsolutePath())
				+ "/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";
		initLocales();
		
		//---------------------------------------------------------------------
		// Init Proceso Agregar Acciones	
		//---------------------------------------------------------------------
		
		Usuario user1 = fact.crearUsuario("admin", "123", "ADMIN");
		Usuario user2 = fact.crearUsuario("adminPrueba", "123", "ADMIN");
		Usuario user3 = fact.crearUsuario("terminal1", "123", "TERMINAL");
		Repositorio.getInstance().usuarios().persistir(user1);
		Repositorio.getInstance().usuarios().persistir(user2);
		Repositorio.getInstance().usuarios().persistir(user3);
		
		// creamos usuario admin y le agregamos la funcionalidad agregarAcciones
		admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(admin);
		
		// creamos usuario adminPrueba y le sacamos las funcionalidad cambiarEstadoMail actualizacionLocalesComerciales
		// Cuando se crean usuarios s eles asignan algunas funcionalidades base acorde a su Rol por lo que deben ser removidas para
		// este test
		adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(adminPrueba);
		adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
		
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos las funcionalidades busquedaPOI obtenerInfoPOI
		// Cuando se crean usuarios s eles asignan algunas funcionalidades base acorde a su Rol por lo que deben ser removidas para
		// este test
		unUsuarioTerminal1 = Repositorio.getInstance().usuarios().getUsuarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(unUsuarioTerminal1);
		unUsuarioTerminal1 = Repositorio.getInstance().usuarios().getUsuarioByName("terminal1");
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("obtenerInfoPOI")!=null);
		
		
		// iniciamos sesion con usuario admin
		tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		
		//---------------------------------------------------------------------
		// Init Proceso Baja Pois
		//---------------------------------------------------------------------
		
		filePathBajaPoi = (new File (".").getAbsolutePath ()) + "/src/test/java/ar/edu/utn/dds/grupouno/procesos/bajaPois.json";
		local4 = new LocalComercial();
		banco1 = new Banco();
		
		fecha = new DateTime(2016,10,18,0,0);
		
		local4.setNombre("local1");
		String[] etiquetas1 = { "matadero", "heladeria" };
		local4.setEtiquetas(etiquetas1);
		local4.setFechaBaja(fecha);

		banco1.setNombre("banco1");
		banco1.setFechaBaja(fecha);

		Repositorio.getInstance().pois().agregarPOI(local4);
		Repositorio.getInstance().pois().agregarPOI(banco1);
	}
	
	private void initLocales() {
		// Creo un local para corroborar que se actualizan los locales que ya
		// existen:
		local1 = new LocalComercial();
		local1.setNombre("local1");
		local1.setEtiquetas(etiquetas);
		Repositorio.getInstance().pois().agregarPOI(local1);
		Repositorio.getInstance().pois().getEm().detach(local1);

		// Creo los locales que espero obtener despues de ejecutar el proceso:
		local2 = new LocalComercial();
		local2.setNombre("local2");
		local2.setEtiquetas(etiquetas2);

		local3 = new LocalComercial();
		local3.setNombre("local3");
		local3.setEtiquetas(etiquetas3);

	}
	
	@Test
	public void testProcesoMultiple() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{

		//---------------------------------------------------------------------
		// Proceso Actualizar Locales Comerciales
		//---------------------------------------------------------------------
		
		ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales();
		AgregarAcciones proceso2 = new AgregarAcciones();
		proceso.addSiguienteProceso(proceso2, filePathAgregarAcciones, REINTENTOS_MAX, false);
		BajaPOIs proceso3 = new BajaPOIs();
		proceso2.addSiguienteProceso(proceso3, filePathBajaPoi, REINTENTOS_MAX, false);
		
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(admin, proceso, filePathActualizarLocalesComerciales, false, 0);

		// Para darle tiempo al planificador que se puedea inicializar y
		// ejecutar los procesos
		while (!scheduler.getContext().getBoolean("ejecutado")) {
			Thread.sleep(1000);
		}

		scheduler.shutdown();

		// Busco las modificaciones para corroborar que se corrio correctamente
		local2Actualizado = (LocalComercial) Repositorio.getInstance().pois().getPOIbyNombre("local2").get(0);
		local3Actualizado = (LocalComercial) Repositorio.getInstance().pois().getPOIbyNombre("local3").get(0);
		local1Actualizado = (LocalComercial) Repositorio.getInstance().pois().getPOIbyNombre("local1").get(0);

		// Compruebo que el local 2 y 3 hayan sido creados:
		Assert.assertNotNull(local2Actualizado);
		Assert.assertNotNull(local3Actualizado);

		// Compruebo que los locales 2 y 3 se hayan creado correctamente:
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));

		// Compruebo que el local 1 se actualizo correctamente:
		Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
		

		//---------------------------------------------------------------------
		// Proceso Actualizar Agregar Acciones
		//---------------------------------------------------------------------
		
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);

		//---------------------------------------------------------------------
		// Proceso Actualizar Baja Pois
		//---------------------------------------------------------------------
		
		Assert.assertTrue(Repositorio.getInstance().pois().getPOIbyNombre("local1").size() == 0);
		Assert.assertTrue(Repositorio.getInstance().pois().getPOIbyNombre("banco1").size() == 0);

	}
	
//	@Test
//	public void procesoMultipleTest() {
//
//		// usuario admin y sus funcionalidades
//		Usuario admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
//		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
//		AuthAPI.getInstance().agregarFuncionalidad("procesoMultiple", admin);
//		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
//		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
//		
//		
//		//---------------------------
//		//Init AgregarAcciones
//		//---------------------------
//		// creamos un usuario adminPrueba
//		Usuario adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
//		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
//		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
//		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
//		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
//		
//		// creamos usuario unUsuarioTerminal1 y le sacamos funcionalidades
//		Usuario unUsuarioTerminal1 = Repositorio.getInstance().usuarios().getUsuarioByName("terminal1");
//		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
//		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
//		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
//		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("obtenerInfoPOI")!=null);
//
//		//---------------------------
//		//Init BajaPOI
//		//---------------------------
//		LocalComercial local1 = new LocalComercial();
//		Banco banco1 = new Banco();
//		
//		local1.setNombre("local1");
//		String[] etiquetas1 = { "mataderos", "heladeria" };
//		local1.setEtiquetas(etiquetas1);
//		local1.setFechaBaja(new DateTime(2016,10,18,0,0));
//
//		banco1.setNombre("banco1");
//		banco1.setFechaBaja(new DateTime(2016,10,18,0,0));
//
//		Repositorio.getInstance().pois().agregarPOI(local1);
//		Repositorio.getInstance().pois().agregarPOI(banco1);
//		
//		//---------------------------
//		//Init ActualizacionLocalesComerciales
//		//---------------------------
//		//Creo los locales comerciales pero solo agrego el 3 y 4, 
//		//esperando que los otros 2 los cree el proceso
//		LocalComercial local2 = new LocalComercial();
//		LocalComercial local3 = new LocalComercial();
//		LocalComercial local4 = new LocalComercial();
//		
//		local2.setNombre("local2");
//		String[] etiquetas2 = {"juegos", "azul", "moron"};
//		local2.setEtiquetas(etiquetas2);
//				
//		local3.setNombre("local3");
//		String[] etiquetas3 = {"azul", "helado", "esquina"};
//		local3.setEtiquetas(etiquetas3);
//				
//		local4.setNombre("local4");
//		String[] etiquetas4 = {"mataderos", "heladeria"};
//		local4.setEtiquetas(etiquetas4);
//				
//		Repositorio.getInstance().pois().agregarPOI(local3);
//		Repositorio.getInstance().pois().agregarPOI(local4);
//				
//		// iniciamos sesion con usuario admin
//		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
//
//		ArrayList<Proceso> listProc = new ArrayList<Proceso>();
//
//		// agregamos un proceso de AgregarAcciones
//		FuncAgregarAcciones funcion = ((FuncAgregarAcciones) admin.getFuncionalidad("agregarAcciones"));
//		Proceso proc1 = funcion.prepAgregarAcciones(admin, tokenAdmin, 0, false,
//				(new File(".").getAbsolutePath()) + "/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregar");
//		listProc.add(proc1);
//
//		// agregamos un proceso bajaPOIs
//		FuncBajaPOIs funcion2 = ((FuncBajaPOIs) admin.getFuncionalidad("bajaPOIs"));
//		Proceso proc2 = funcion2.prepDarDeBajaPOI(admin, tokenAdmin, 0, false, 
//				(new File (".").getAbsolutePath ()) + "/src/test/java/ar/edu/utn/dds/grupouno/procesos/bajaPois.json");
//		listProc.add(proc2);
//
//		// agregamos un proceso actualizacionLocalesComerciales
//		FuncActualizacionLocalesComerciales funcion3 = ((FuncActualizacionLocalesComerciales) admin.getFuncionalidad("actualizacionLocalesComerciales"));
//		Proceso proc3 = funcion3.prepAgregarAcciones(admin, tokenAdmin, 0, false, 
//				(new File (".").getAbsolutePath ())+"/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt");
//		listProc.add(proc3);
//
//		// Ejecutamos el proceso multiple
//		FuncMultiple funcion1 = ((FuncMultiple) admin.getFuncionalidad("procesoMultiple"));
//		funcion1.procesoMultiple(admin, tokenAdmin, 0, false, listProc);
//				
//		// Validaciones Proceso AgregarAcciones
//		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
//		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
//		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
//
//		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
//		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
//
//		// Validaciones Proceso BajaPOIs
//		// Se valida que los elementos ya no existan en la lista
//		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("local1"));
//		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("banco1"));
//		
//		// validaciones Proceso ActualizarLocalesComerciales
//		// Se valida que los elementos se agreguen o actualizen
//		POI local2Actualizado = Repositorio.getInstance().pois().getPOIbyNombre("local2").get(0);
//		POI local3Actualizado = Repositorio.getInstance().pois().getPOIbyNombre("local3").get(0);
//		
//		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
//		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
//	}
	
}
