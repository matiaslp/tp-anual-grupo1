package procesos;


import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncAgregarAcciones;
import db.AgregarAccionesTransaction;
import db.DB_Usuarios;


public class TestAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	boolean encontrada;
	boolean encontradaTodas;
	boolean agregado;
	Usuario unUsuarioAdmin;
	Usuario unUsuarioTerminal;
	
	
	
//	private String listaAcciones1[];
//	private String listaAcciones2[];
//	private String listaAcciones3[];
	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	private ArrayList<String> listadoAccionesQueEstanEnTerminal;
	
	AgregarAccionesTransaction transaction;
	
	@Before
	public void init() {
		listadoAccionesQueEstanEnAdmin= new ArrayList<String>();
		listadoAccionesQueEstanEnTerminal= new ArrayList<String>();
		
		db_usuario=db.DB_Usuarios.getInstance();
		
		unAuthAPI = AuthAPI.getInstance();
		
		transaction= new AgregarAccionesTransaction(0);
		
		
			
		//PONER LAS SIGUIENTES LISTAS DE ACCIONES
		
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
		
		
		unUsuarioAdmin=new Usuario("admin", "123", Rol.ADMIN);
		
		unUsuarioTerminal=new Usuario("terminal", "123", Rol.TERMINAL);
		
		
		

	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnAdmin() throws MessagingException {
			
		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnAdmin,transaction);
		
		
		//comprobacion si tiene toda la lista a agregar
			encontradaTodas=true;
				for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
					encontrada=db_usuario. getUsarioByName("admin").chequearFuncionalidad(unafuncionabilidad);
					if(encontrada==false){encontradaTodas=false;}
					
				
					}
				Assert.assertTrue(encontradaTodas);
		
	}
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnAdmin() throws MessagingException {
				
		AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnAdmin,transaction);
		
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
				encontrada=db_usuario. getUsarioByName("terminal").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
			
				}
			Assert.assertFalse(encontradaTodas);
		
	}
	
	
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnTerminal() throws MessagingException {
				
	
		agregado = AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnTerminal,transaction);
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
				encontrada=db_usuario. getUsarioByName("terminal").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
				
				}
			Assert.assertTrue(encontradaTodas);
		
	}
	
	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnTerminal() throws MessagingException {
	
		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnTerminal,transaction);
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
				encontrada=db_usuario. getUsarioByName("admin").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
				
				}
			Assert.assertTrue(encontradaTodas);
		
	}
	
	
	@Test
	public void agregarAccionesProcesoTest() {
		Usuario adminPrueba = new Usuario("adminPrueba", "123", Rol.ADMIN);
		Usuario admin = DB_Usuarios.getInstance().getUsarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertFalse(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));
		
		
		
		Usuario unUsuarioTerminal1 = new Usuario("terminal1", "123", Rol.TERMINAL);
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		Assert.assertFalse(unUsuarioTerminal1.chequearFuncionalidad("obtenerInfoPOI"));
		
		
		
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncAgregarAcciones funcion = (FuncAgregarAcciones) AuthAPI.getInstance().getAccion("agregarAcciones");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, false, (new File (".").getAbsolutePath ())+"/src/test/java/accionesAAgregar", adminPrueba);
		
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("cambiarEstadoMail"));
		Assert.assertTrue(adminPrueba.chequearFuncionalidad("actualizacionLocalesComerciales"));
		
		Assert.assertTrue(unUsuarioTerminal1.chequearFuncionalidad("busquedaPOI"));
		
	}
}
