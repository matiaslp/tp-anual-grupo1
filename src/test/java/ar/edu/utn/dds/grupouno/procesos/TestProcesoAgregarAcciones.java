package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;

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
		adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(adminPrueba);
		adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
		
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos las funcionalidades busquedaPOI obtenerInfoPOI
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
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{
				
		ResultadoProceso resultadoProceso = new ResultadoProceso();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.getContext().put("ResultadoProceso", resultadoProceso);
		scheduler.getContext().put("Usuario", admin);
		scheduler.getContext().put("ejecutado", false);
		
		scheduler.start();
		
		JobKey key = new JobKey(ActualizacionLocalesComerciales.class.getSimpleName());
		
		// Crea una instancia del proceso y con la opcion requestRecovery(true) se fuerzan reintentos en caso de fallas
		JobDetail job = JobBuilder.newJob(AgregarAcciones.class).withIdentity(key).requestRecovery(true).build();
		
		// Cargo en el jobDataMap el path del archivo que uso de referencia.
		job.getJobDataMap().put("filePath", filePath);
		job.getJobDataMap().put("enviarMail", false);
		job.getJobDataMap().put("reintentosMax", REINTENTOS_MAX);
		job.getJobDataMap().put("reintentosCont", 0);
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
				
		// Creo instancia del jobListener y se lo agrego al scheduler
		AgregarAcciones procesoInicial = new AgregarAcciones();
		ProcesoListener procesoInicialListener = procesoInicial.getProcesoListener();
		scheduler.getListenerManager().addJobListener((JobListener)procesoInicialListener, KeyMatcher.keyEquals(key));
		
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		while(!scheduler.getContext().getBoolean("ejecutado")){
			Thread.sleep(1000);
			//wait(1000L);
		}
		scheduler.shutdown();
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
	}
	
	
	@After
	public void outtro(){
		
		Repositorio.getInstance().remove(admin);
		Repositorio.getInstance().remove(adminPrueba);
		Repositorio.getInstance().remove(unUsuarioTerminal1);
		
	}
	
}
