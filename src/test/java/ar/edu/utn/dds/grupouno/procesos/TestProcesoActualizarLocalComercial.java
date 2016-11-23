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

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class TestProcesoActualizarLocalComercial {
	
	private int REINTENTOS_MAX = 5;
	
	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	Repositorio repositorio;
	String filePath;
	LocalComercial local1, local2, local3, 
	local1Actualizado, local2Actualizado, local3Actualizado;
	UsuariosFactory ufactory = new UsuariosFactory();
	String[] etiquetas = {"mataderos", "heladeria"};
	String[] etiquetas2 = {"juegos", "azul", "moron"};
	String[] etiquetas3 = {"azul", "helado", "esquina"};
	
	@Before
	public void init(){
		Autenticador = AuthAPI.getInstance();
		repositorio = Repositorio.getInstance();
		filePath = (new File (".").getAbsolutePath ()) + 
				"/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";
		initLocales();
	}
	
	private void initLocales(){
		//Creo un local para corroborar que se actualizan los locales que ya existen:
		local1 = new LocalComercial();
		local1.setNombre("local1");
		local1.setEtiquetas(etiquetas);
		repositorio.pois().agregarPOI(local1);
		repositorio.pois().getEm().detach(local1);
		
		
		//Creo los locales que espero obtener despues de ejecutar el proceso:
		local2 = new LocalComercial();
		local2.setNombre("local2");
		local2.setEtiquetas(etiquetas2);
//		repositorio.pois().agregarPOI(local2);
//		repositorio.pois().getEm().detach(local2);
		
				
		local3 = new LocalComercial();
		local3.setNombre("local3");
		local3.setEtiquetas(etiquetas3);
//		repositorio.pois().agregarPOI(local3);
//		repositorio.pois().getEm().detach(local3);
		
		unUsuarioAdmin = ufactory.crearUsuario("admin", "password","ADMIN");
		
		unUsuarioAdmin.setAuditoriaActivada(true);
		unUsuarioAdmin.setCorreo("uncorreo@correoloco.com");
		unUsuarioAdmin.setLog(true);
		unUsuarioAdmin.setMailHabilitado(true);
		unUsuarioAdmin.setNombre("Shaggy");
		unUsuarioAdmin.setNotificacionesActivadas(true);
		
		Repositorio.getInstance().usuarios().persistirUsuario(unUsuarioAdmin);
	}

//	@Test
//	public void testActualizar() throws InterruptedException{
//		//Ejecucion del proceso:
//		Usuario admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
//		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
//		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
//		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance().
//				getAccion("actualizacionLocalesComerciales");
//		funcion.actualizarLocales(admin, tokenAdmin, 0, false, filePath);
//		
//		Thread.sleep(240000);
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
//		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
//		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
//		
//		//Compruebo que el local 4 se actualizo correctamente:
//		Assert.assertTrue(local4Actualizado.compararEtiquetas(local4));
//	}
	
	@Test
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{
				
		ResultadoProceso resultadoProceso = new ResultadoProceso();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.getContext().put("ResultadoProceso", resultadoProceso);
		scheduler.getContext().put("Usuario", unUsuarioAdmin);
		scheduler.getContext().put("ejecutado", false);
		
		scheduler.start();
		
		JobKey key = new JobKey(ActualizacionLocalesComerciales.class.getSimpleName());
		
		// Crea una instancia del proceso y con la opcion requestRecovery(true) se fuerzan reintentos en caso de fallas
		JobDetail job = JobBuilder.newJob(ActualizacionLocalesComerciales.class).withIdentity(key).requestRecovery(true).build();
		
		// Cargo en el jobDataMap el path del archivo que uso de referencia.
		job.getJobDataMap().put("filePath", filePath);
		job.getJobDataMap().put("enviarMail", false);
		job.getJobDataMap().put("reintentosMax", REINTENTOS_MAX);
		job.getJobDataMap().put("reintentosCont", 0);
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
				
		// Creo instancia del jobListener y se lo agrego al scheduler
		ActualizacionLocalesComerciales procesoInicial = new ActualizacionLocalesComerciales();
		ProcesoListener procesoInicialListener = procesoInicial.getProcesoListener();
		scheduler.getListenerManager().addJobListener((JobListener)procesoInicialListener, KeyMatcher.keyEquals(key));
		
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		while(!scheduler.getContext().getBoolean("ejecutado")){
			Thread.sleep(1000);
		}
		
		scheduler.shutdown();
		
		//Busco las modificaciones para corroborar que se corrio correctamente
		local2Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local2").get(0);
		local3Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local3").get(0);
		local1Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local1").get(0);
		
		//Compruebo que el local 2 y 3 hayan sido creados:
		Assert.assertNotNull(local2Actualizado);
		Assert.assertNotNull(local3Actualizado);
		
		
		//Compruebo que los locales 2 y 3 se hayan creado correctamente:
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
		
		//Compruebo que el local 1 se actualizo correctamente:
		Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
	}
	
	@After
	public void outtro(){
		
		repositorio.remove(unUsuarioAdmin);
		repositorio.remove(local1Actualizado);
		repositorio.remove(local2Actualizado);
		repositorio.remove(local3Actualizado);
		
	}
}
