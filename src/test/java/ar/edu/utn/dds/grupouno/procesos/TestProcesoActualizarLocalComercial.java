package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.quartz.ProcesoHandler;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class TestProcesoActualizarLocalComercial {

	private int REINTENTOS_MAX = 5;

	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	Repositorio repositorio;
	String filePath;
	LocalComercial local1, local2, local3, local1Actualizado, local2Actualizado, local3Actualizado;
	UsuariosFactory ufactory = new UsuariosFactory();
	String[] etiquetas = { "mataderos", "heladeria" };
	String[] etiquetas2 = { "juegos", "azul", "moron" };
	String[] etiquetas3 = { "azul", "helado", "esquina" };

	@Before
	public void init() {
		Autenticador = AuthAPI.getInstance();
		repositorio = Repositorio.getInstance();
		filePath = (new File(".").getAbsolutePath())
				+ "/src/test/java/ar/edu/utn/dds/grupouno/procesos/actualizarLocalesComerciales.txt";
		initLocales();
	}

	private void initLocales() {
		// Creo un local para corroborar que se actualizan los locales que ya
		// existen:
		local1 = new LocalComercial();
		local1.setNombre("local1");
		local1.setEtiquetas(etiquetas);
		repositorio.pois().agregarPOI(local1);
		repositorio.pois().getEm().detach(local1);

		// Creo los locales que espero obtener despues de ejecutar el proceso:
		local2 = new LocalComercial();
		local2.setNombre("local2");
		local2.setEtiquetas(etiquetas2);

		local3 = new LocalComercial();
		local3.setNombre("local3");
		local3.setEtiquetas(etiquetas3);

		unUsuarioAdmin = ufactory.crearUsuario("admin", "password", "ADMIN");

		unUsuarioAdmin.setAuditoriaActivada(true);
		unUsuarioAdmin.setCorreo("uncorreo@correoloco.com");
		unUsuarioAdmin.setLog(true);
		unUsuarioAdmin.setMailHabilitado(true);
		unUsuarioAdmin.setNombre("Shaggy");
		unUsuarioAdmin.setNotificacionesActivadas(true);

		Repositorio.getInstance().usuarios().persistirUsuario(unUsuarioAdmin);
	}

	@Test
	public void testProcesoActualizarLocalesComerciales() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			SchedulerException, InterruptedException {

		ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales();
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(unUsuarioAdmin, proceso, filePath, false, REINTENTOS_MAX,null);

		// Para darle tiempo al planificador que se puedea inicializar y
		// ejecutar los procesos
		while (!scheduler.getContext().getBoolean("ejecutado")) {
			Thread.sleep(1000);
		}

		scheduler.shutdown();

		// Busco las modificaciones para corroborar que se corrio correctamente
		local2Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local2").get(0);
		local3Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local3").get(0);
		local1Actualizado = (LocalComercial) repositorio.pois().getPOIbyNombre("local1").get(0);

		// Compruebo que el local 2 y 3 hayan sido creados:
		Assert.assertNotNull(local2Actualizado);
		Assert.assertNotNull(local3Actualizado);

		// Compruebo que los locales 2 y 3 se hayan creado correctamente:
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));

		// Compruebo que el local 1 se actualizo correctamente:
		Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
	}
	
	@Test
	public void testEjecucionMultiple() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			SchedulerException, InterruptedException {
		ActualizacionLocalesComerciales proceso = new ActualizacionLocalesComerciales();
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(unUsuarioAdmin, proceso,"", false, REINTENTOS_MAX,null);

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
	public void outtro() {

		repositorio.usuarios().deleteUsuario(unUsuarioAdmin.getId());
		
		ArrayList<POI> lista = repositorio.pois().getListado();
		for(POI nodo : lista){
			repositorio.remove(nodo);
		}
		ArrayList<ResultadoProceso> lstRes = repositorio.resultadosProcesos().getListado();
		for ( ResultadoProceso resultado : lstRes){
			repositorio.remove(resultado);
		}
		

	}
}
