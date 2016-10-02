package procesos;

import java.io.File;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncAgregarAcciones;
import db.AgregarAccionesTransaction;
import db.DB_Usuarios;

public class TestProcesoAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	boolean encontrada;
	boolean encontradaTodas;
	boolean agregado;
	Usuario unUsuarioAdmin;
	Usuario unUsuarioTerminal;
	Usuario unUsuarioAdmin2;
	Usuario unUsuarioTerminal2;

	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	private ArrayList<String> listadoAccionesQueEstanEnTerminal;

	AgregarAccionesTransaction transaction;

	@Before
	public void init() {
		listadoAccionesQueEstanEnAdmin = new ArrayList<String>();
		listadoAccionesQueEstanEnTerminal = new ArrayList<String>();
		db_usuario = db.DB_Usuarios.getInstance();
		unAuthAPI = AuthAPI.getInstance();
		transaction = new AgregarAccionesTransaction(0);

		// PONER LAS SIGUIENTES LISTAS DE ACCIONES

		listadoAccionesQueEstanEnTerminal.add("busquedaPOI");
		listadoAccionesQueEstanEnTerminal.add("obtenerInfoPOI");

		listadoAccionesQueEstanEnAdmin.add("busquedaPOI");
		listadoAccionesQueEstanEnAdmin.add("obtenerInfoPOI");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedaPorUsuario");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedasPorFecha");
		listadoAccionesQueEstanEnAdmin.add("reportecantidadResultadosPorTerminal");
		listadoAccionesQueEstanEnAdmin.add("cambiarEstadoMail");
		listadoAccionesQueEstanEnAdmin.add("actualizacionLocalesComerciales");
		listadoAccionesQueEstanEnAdmin.add("agregarAcciones");
		listadoAccionesQueEstanEnAdmin.add("bajaPOIs");
		listadoAccionesQueEstanEnAdmin.add("procesoMultiple");

		unUsuarioAdmin = new Usuario("admin", "123", Rol.ADMIN);
		unUsuarioTerminal = new Usuario("terminal", "123", Rol.TERMINAL);
		unUsuarioAdmin2 = new Usuario("adminPrueba", "123", Rol.ADMIN);
		unUsuarioTerminal2 = new Usuario("terminal1", "123", Rol.TERMINAL);
	}

	
	@Test
	public void agregarAccionesProcesoTest() {
		// creamos usuarios admin y adminPrueba y les agregamos funcionalidades
		Usuario admin = DB_Usuarios.getInstance().getUsarioByName("admin");
		Usuario adminPrueba = DB_Usuarios.getInstance().getUsarioByName("adminPrueba");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos funcionalidades
		Usuario unUsuarioTerminal1 = DB_Usuarios.getInstance().getUsarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("obtenerInfoPOI"));
		
		// iniciamos sesion con usuario admin
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		
		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/procesos/accionesAAgregar");
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));

	}
	
	@Test
	public void agregarAccionesProcessUndo() {
		// creamos usuarios admin y adminPrueba y les agregamos funcionalidades
		Usuario adminPrueba = DB_Usuarios.getInstance().getUsarioByName("adminPrueba");
		Usuario admin = DB_Usuarios.getInstance().getUsarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos funcionalidades
		Usuario unUsuarioTerminal1 = DB_Usuarios.getInstance().getUsarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("obtenerInfoPOI"));
		
		// iniciamos sesion con usuario admin
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		
		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/procesos/accionesAAgregar");
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		
		funcion.agregarAccionesUndo(admin, tokenAdmin, 0, false);
		
		// Se valida que el usuario adminPrueba no tiene las funcionalidades agregadas
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));

		// Se valida que el usuario unUsuarioTerminal1 no tiene la funcionalidad agregada
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		
	}
	
	
	
}
