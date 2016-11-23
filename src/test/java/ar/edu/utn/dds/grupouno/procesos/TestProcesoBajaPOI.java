package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;

import org.joda.time.DateTime;
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
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class TestProcesoBajaPOI {
	private static int REINTENTOS_MAX = 5;
	
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
		// DB_POI.getListado().clear();
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
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{
		
		ResultadoProceso resultadoProceso = new ResultadoProceso();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.getContext().put("ResultadoProceso", resultadoProceso);
		scheduler.getContext().put("Usuario", admin);
		scheduler.getContext().put("ejecutado", false);
		
		scheduler.start();
		
		JobKey key = new JobKey(ActualizacionLocalesComerciales.class.getSimpleName());
		
		// Crea una instancia del proceso y con la opcion requestRecovery(true) se fuerzan reintentos en caso de fallas
		JobDetail job = JobBuilder.newJob(BajaPOIs.class).withIdentity(key).requestRecovery(true).build();
		
		// Cargo en el jobDataMap el path del archivo que uso de referencia.
		job.getJobDataMap().put("filePath", filePath);
		job.getJobDataMap().put("enviarMail", false);
		job.getJobDataMap().put("reintentosMax", REINTENTOS_MAX);
		job.getJobDataMap().put("reintentosCont", 0);
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
		
		// Creo instancia del jobListener y se lo agrego al scheduler
		BajaPOIs procesoInicial = new BajaPOIs();
		ProcesoListener procesoInicialListener = procesoInicial.getProcesoListener();
		scheduler.getListenerManager().addJobListener((JobListener)procesoInicialListener, KeyMatcher.keyEquals(key));
		
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		while(!scheduler.getContext().getBoolean("ejecutado")){
			Thread.sleep(1000);
		}
		
		scheduler.shutdown();
		
		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("local1"));
		Assert.assertNull(Repositorio.getInstance().pois().getPOIbyNombre("banco1"));
	}
}
