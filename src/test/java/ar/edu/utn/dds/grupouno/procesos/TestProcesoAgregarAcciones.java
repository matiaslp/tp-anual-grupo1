package ar.edu.utn.dds.grupouno.procesos;

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.funciones.FuncAgregarAcciones;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestProcesoAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	UsuariosFactory fact = new UsuariosFactory();
	Usuario admin, adminPrueba, unUsuarioTerminal1;
	String tokenAdmin;

	AgregarAccionesTransaction transaction;

	@Before
	public void init() {
		Repositorio.getInstance().usuarios().getListaUsuarios().clear();
		AuthAPI.getInstance();
		
		fact.crearUsuario("admin", "123", "ADMIN");
		fact.crearUsuario("adminPrueba", "123", "ADMIN");
		fact.crearUsuario("terminal1", "123", "TERMINAL");
		
		// creamos usuario admin y le agregamos la funcionalidad agregarAcciones
		admin = Repositorio.getInstance().usuarios().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		
		// creamos usuario adminPrueba y le sacamos las funcionalidad cambiarEstadoMail actualizacionLocalesComerciales
		adminPrueba = Repositorio.getInstance().usuarios().getUsuarioByName("adminPrueba");
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
		
		
		// creamos usuario unUsuarioTerminal1 y le sacamos las funcionalidades busquedaPOI obtenerInfoPOI
		unUsuarioTerminal1 = Repositorio.getInstance().usuarios().getUsuarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
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
	
}
