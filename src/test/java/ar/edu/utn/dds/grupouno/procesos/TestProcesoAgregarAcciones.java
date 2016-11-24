package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import com.sun.org.apache.xpath.internal.axes.WalkingIteratorSorted;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.ProcesoHandler;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class TestProcesoAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	UsuariosFactory fact = new UsuariosFactory();
	Usuario admin, adminPrueba, unUsuarioTerminal1;
	String tokenAdmin;
	String filePath = (new File (".").getAbsolutePath ()) + 
			"/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregar";
	int REINTENTOS_MAX = 5;
	
	AgregarAccionesTransaction transaction;

	@Before
	public void init() {
		AuthAPI.getInstance();
		
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

	}

	
//	@Test
//	public void agregarAccionesProcesoTest() {
//		
//		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
//		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
//		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregar");
//		
//		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
//		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
//		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
//
//		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
//		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
//
//	}
//	
//	@Test
//	public void agregarAccionesProcessUndo() {
//		
//		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
//		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
//		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/ar/edu/utn/dds/grupouno/procesos/accionesAAgregar");
//		
//		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
//		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
//		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
//
//		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
//		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
//		
//		funcion.agregarAccionesUndo(admin, tokenAdmin, 0, false);
//		
//		// Se valida que el usuario adminPrueba no tiene las funcionalidades agregadas
//		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
//		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
//
//		// Se valida que el usuario unUsuarioTerminal1 no tiene la funcionalidad agregada
//		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
//		
//	}
	@Test
	public void testProcesoAgregarAcciones() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{
						
		AgregarAcciones proceso = new AgregarAcciones();
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(admin, proceso, filePath, false, REINTENTOS_MAX);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		while(!scheduler.getContext().getBoolean("ejecutado")){
			Thread.sleep(1000);
		}
		scheduler.shutdown();
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
	}
	
	@Test
	public void testEjecucionMultiple() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			SchedulerException, InterruptedException {
		AgregarAcciones proceso = new AgregarAcciones();
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(admin, proceso, "", false, REINTENTOS_MAX);

		// Para darle tiempo al planificador que se puedea inicializar y
		// ejecutar los procesos
		while (!scheduler.getContext().getBoolean("ejecutado")) {
			Thread.sleep(1000);
		}
		
		int reintentosRealizados = scheduler.getContext().getInt("reintentosCont");
		
		Assert.assertTrue(reintentosRealizados == REINTENTOS_MAX);

		scheduler.shutdown();
		
	}
	
	
	@After
	public void outtro(){
		
		Repositorio.getInstance().remove(admin);
		Repositorio.getInstance().remove(adminPrueba);
		Repositorio.getInstance().remove(unUsuarioTerminal1);
		ArrayList<ResultadoProceso> lstRes = Repositorio.getInstance().resultadosProcesos().getListado();
		for ( ResultadoProceso resultado : lstRes){
			Repositorio.getInstance().remove(resultado);
		}
		
	}
	
}
