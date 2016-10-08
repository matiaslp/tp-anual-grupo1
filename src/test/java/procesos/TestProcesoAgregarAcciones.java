package procesos;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.UsuariosFactory;
import autentification.funciones.FuncAgregarAcciones;
import db.AgregarAccionesTransaction;
import db.DB_Usuarios;

public class TestProcesoAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	UsuariosFactory fact = new UsuariosFactory();
	Usuario admin, adminPrueba, unUsuarioTerminal1;
	String tokenAdmin;

	AgregarAccionesTransaction transaction;

	@Before
	public void init() {
		DB_Usuarios.getInstance();
		AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", Rol.ADMIN);
		fact.crearUsuario("adminPrueba", "123", Rol.ADMIN);
		fact.crearUsuario("terminal1", "123", Rol.TERMINAL);
		
		// creamos usuario admin y le agregamos la funcionalidad agregarAcciones
		Usuario admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		
		
		// creamos usuario adminPrueba y le sacamos las funcionalidad cambiarEstadoMail actualizacionLocalesComerciales
		Usuario adminPrueba = DB_Usuarios.getInstance().getUsuarioByName("adminPrueba");
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos las funcionalidades busquedaPOI obtenerInfoPOI
		Usuario unUsuarioTerminal1 = DB_Usuarios.getInstance().getUsuarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("obtenerInfoPOI")!=null);
		

	}

	
	@Test
	public void agregarAccionesProcesoTest() {
		
		// iniciamos sesion con usuario admin
		tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		
		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/procesos/accionesAAgregar");
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);

	}
	
	@Test
	public void agregarAccionesProcessUndo() {
		
		// iniciamos sesion con usuario admin
		tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		
		// usuario admin realiza la accion de ejecutar el proceso agregarAcciones
		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, (new File (".").getAbsolutePath ())+"/src/test/java/procesos/accionesAAgregar");
		
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
		
		funcion.agregarAccionesUndo(admin, tokenAdmin, 0, false);
		
		// Se valida que el usuario adminPrueba no tiene las funcionalidades agregadas
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 no tiene la funcionalidad agregada
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
		
	}
	
	@After
	public void ending() {
		
		// Eliminamos los usuarios
		DB_Usuarios.getInstance().deleteUsuario(admin.getID());
		DB_Usuarios.getInstance().deleteUsuario(adminPrueba.getID());
		DB_Usuarios.getInstance().deleteUsuario(unUsuarioTerminal1.getID());
	}
	
	
	
}
