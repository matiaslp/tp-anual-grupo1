package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;

import org.joda.time.DateTime;
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
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncBajaPOIs;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.ProcesoHandler;
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
		Autenticador = AuthAPI.getInstance();
		Usuario user = fact.crearUsuario("admin", "123", "ADMIN");
		Repositorio.getInstance().usuarios().persistir(user);
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
		Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(admin);
		tokenAdmin=AuthAPI.getInstance().iniciarSesion("admin", "123");
		funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
	}

	@Test
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SchedulerException, InterruptedException{
			
		BajaPOIs proceso = new BajaPOIs();
		Scheduler scheduler = ProcesoHandler.ejecutarProceso(admin, proceso, filePath, false, REINTENTOS_MAX);
		
		// Para darle tiempo al planificador que se puedea inicializar y ejecutar los procesos
		while(!scheduler.getContext().getBoolean("ejecutado")){
			Thread.sleep(1000);
		}
		
		scheduler.shutdown();
		
		Assert.assertTrue(Repositorio.getInstance().pois().getPOIbyNombre("local1").size() == 0);
		Assert.assertTrue(Repositorio.getInstance().pois().getPOIbyNombre("banco1").size() == 0);
	}
	
	@After
	public void outtro(){
		
		Repositorio.getInstance().remove(local1);
		Repositorio.getInstance().remove(banco1);
		Repositorio.getInstance().remove(admin);
	}
}
