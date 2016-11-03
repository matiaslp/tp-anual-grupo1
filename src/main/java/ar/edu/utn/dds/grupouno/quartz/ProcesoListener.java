package ar.edu.utn.dds.grupouno.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;

public abstract class ProcesoListener implements JobListener {
	public String getName() {
		return getClass().getName();
	}

	// Las subclases concretas que hereden de esta clase abstracta deben implementar este método
	protected abstract void rollback();

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("Antes de ejecutar el proceso: " + context.getJobDetail().getKey().getName());

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	// Método invocado por Quartz luego de ejecutar el Job
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobName = context.getJobDetail().getKey().getName();

		// Se valida si hubo una excepciòn
		if (jobException == null) {
			System.out.println("Proceso : " + jobName + " ejecutado con normalidad");

			try {
				// Se invoca el método que inicia la carga y ejecución del
				// siguiente proceso
				ejecutarProcesoAnidado(context);
			} catch (SchedulerException e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println(
					"Hubo una excepción en el proceso: " + jobName + " La excepción lanzada fue: " + jobException);
			// Si hubo un error durante la ejecución del proceso, se deberá
			// deshacer la acción
			// La implementación del método rollback queda delegada a la clase
			// concreta que extiende esta clase
			rollback();
		}
	}

	public void ejecutarProcesoAnidado(JobExecutionContext context) throws SchedulerException {

		// Este método realiza acciones similares al
		// ejecutaEjemploProcesosAnidadosConRollback() de la clase Consola
		Scheduler scheduler = context.getScheduler();

		String nombreProcesoActual = getClass().getName().replace("Listener", "");
		
		try {
			// Obtiene la clase del proceso actual
			Class actualProceso = getClass().getClassLoader().loadClass(nombreProcesoActual);
			
			//Obtiene la clase del siguiente proceso encadenado
			Class siguienteProceso = ((ProcesoPoi)actualProceso.newInstance()).getSiguienteProceso();

			// Chequea si hay definido un siguiente proceso
			if (siguienteProceso != null) {
				
				// Obtiene el listener del próximo proceso
				ProcesoListener siguienteListener = ((ProcesoPoi) siguienteProceso.newInstance()).getProcesoListener();

				// Define un identificador
				JobKey jobKey = new JobKey(siguienteProceso.getSimpleName());

				// Se crea una instancia del próximo proceso a ejecutar
				JobDetail nextJob = JobBuilder.newJob(siguienteProceso).withIdentity(jobKey).requestRecovery(true).build();

				// Se crea un nuevo trigger que ejecutará el nuevo proceso
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey + "trigger").startNow().build();

				// Se agrega el listener del nuevo proceso al planificador
				scheduler.getListenerManager().addJobListener((JobListener) siguienteListener, KeyMatcher.keyEquals(jobKey));

				// Se suma al planificador el nuevo proceso junto con el trigger
				scheduler.scheduleJob(nextJob, trigger);
			}

		} catch (ClassNotFoundException cnf) {
			System.out.println(cnf.getMessage());
		} catch (InstantiationException ie) {
			System.out.println(ie.getMessage());
		} catch (IllegalAccessException iae) {
			System.out.println(iae.getMessage());
		}

	}
}
