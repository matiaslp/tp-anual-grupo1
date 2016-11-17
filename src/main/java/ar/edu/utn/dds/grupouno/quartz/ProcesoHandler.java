package ar.edu.utn.dds.grupouno.quartz;

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

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;

public class ProcesoHandler {
	
	public static void ejecutarProceso(Usuario usuario, Proceso proceso) throws SchedulerException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		ResultadoProceso resultadoProceso = new ResultadoProceso();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.getContext().put("ResultadoProceso", resultadoProceso);
		scheduler.getContext().put("Usuario", usuario);
		
		scheduler.start();
		
		JobKey key = new JobKey(proceso.getClass().getSimpleName());
		
		// Crea una instancia del proceso y con la opcion requestRecovery(true) se fuerzan reintentos en caso de fallas
		JobDetail job = JobBuilder.newJob(proceso.getClass()).withIdentity(key).requestRecovery(true).build();
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger").startNow().build();
		
		// creo nueva instancia del listener.
		ProcesoListener procesoInicialListener = proceso.getProcesoListener();
		
		scheduler.getListenerManager().addJobListener((JobListener)procesoInicialListener, KeyMatcher.keyEquals(key));
		
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(job, trigger);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		Thread.sleep(1000);
		
		scheduler.shutdown();
	}
}
