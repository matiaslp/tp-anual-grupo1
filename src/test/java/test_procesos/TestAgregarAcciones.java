package test_procesos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncReporteBusquedaPorUsuario;
import autentification.funciones.FuncReporteBusquedasPorFecha;
import autentification.funciones.FuncReporteCantidadResultadosPorTerminal;
import db.AgregarAccionesTransaction;
import db.DB_Usuarios;
import autentification.funciones.FuncEnviarMail;
import email.EnviarEmail;
import helpers.LeerProperties;
import poi.POI;
import procesos.AgregarAcciones;


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
		
		listadoAccionesQueEstanEnTerminal.add("busquedaPoi");
		listadoAccionesQueEstanEnTerminal.add("obtenerInfoPOI");
		
		listadoAccionesQueEstanEnAdmin.add("busquedaPoi");
		listadoAccionesQueEstanEnAdmin.add("obtenerInfoPOI");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedaPorUsuario");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedasPorFecha");
		listadoAccionesQueEstanEnAdmin.add("reportecantidadResultadosPorTerminal");
		listadoAccionesQueEstanEnAdmin.add("enviarMail");
		listadoAccionesQueEstanEnAdmin.add("actualizacionLocalesComerciales");
		listadoAccionesQueEstanEnAdmin.add("agregarAcciones");
		listadoAccionesQueEstanEnAdmin.add("bajaPOIs");
		listadoAccionesQueEstanEnAdmin.add("procesoMultiple");
		
		
		unUsuarioAdmin=new Usuario("admin", "123", Rol.ADMIN);
		
		unUsuarioTerminal=new Usuario("terminal", "123", Rol.TERMINAL);
		
		
		

	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnAdmin() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnAdmin) {

			System.out.println("T1 accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		
		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnAdmin,transaction);
		
		
		//comprobacion si tiene toda la lista a agregar
			encontradaTodas=true;
				for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
					encontrada=db_usuario. getUsarioByName("admin").chequearFuncionalidad(unafuncionabilidad);
					if(encontrada==false){encontradaTodas=false;}
					System.out.println(encontrada+" "+encontradaTodas+"  "+unafuncionabilidad);
					}
				Assert.assertTrue(encontradaTodas);
		
	}
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnAdmin() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnAdmin) {

			System.out.println("T2 accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnAdmin,transaction);
		
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
				encontrada=db_usuario. getUsarioByName("terminal").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
				System.out.println(encontrada+" "+encontradaTodas+"  "+unafuncionabilidad);
				}
			Assert.assertFalse(encontradaTodas);
		
	}
	
	
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnTerminal() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnTerminal) {

			System.out.println("T3 accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnTerminal,transaction);
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
				encontrada=db_usuario. getUsarioByName("terminal").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
				System.out.println(encontrada+" "+encontradaTodas+"  "+unafuncionabilidad);
				}
			Assert.assertTrue(encontradaTodas);
		
	}
	
	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnTerminal() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnTerminal) {

			System.out.println("T4 accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnTerminal,transaction);
		//comprobacion si tiene toda la lista a agregar
		encontradaTodas=true;
			for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
				encontrada=db_usuario. getUsarioByName("admin").chequearFuncionalidad(unafuncionabilidad);
				if(encontrada==false){encontradaTodas=false;}
				System.out.println(encontrada+" "+encontradaTodas+"  "+unafuncionabilidad);
				}
			Assert.assertTrue(encontradaTodas);
		
	}
}
