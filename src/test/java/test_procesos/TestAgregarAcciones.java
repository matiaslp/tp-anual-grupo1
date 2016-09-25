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
import db.DB_Usuarios;
import autentification.funciones.FuncEnviarMail;
import email.EnviarEmail;
import helpers.LeerProperties;
import poi.POI;
import procesos.AgregarAcciones;


public class TestAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	
	boolean agregado;
	Usuario unUsuarioAdmin;
	Usuario unUsuarioTerminal;
	
//	private String listaAcciones1[];
//	private String listaAcciones2[];
//	private String listaAcciones3[];
	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	private ArrayList<String> listadoAccionesQueNoEstanEnTerminal;
	
	@Before
	public void init() {
		listadoAccionesQueEstanEnAdmin= new ArrayList<String>();
		listadoAccionesQueNoEstanEnTerminal= new ArrayList<String>();
		
		db_usuario=db.DB_Usuarios.getInstance();
		
		unAuthAPI = AuthAPI.getInstance();
		
			
		//PONER LAS SIGUIENTES LISTAS DE ACCIONES
		
		listadoAccionesQueEstanEnAdmin.add("busquedaPoi");
		listadoAccionesQueEstanEnAdmin.add("obtenerInfoPOI");
		
		listadoAccionesQueNoEstanEnTerminal.add("reporteBusquedaPorUsuario");
		listadoAccionesQueNoEstanEnTerminal.add("reporteBusquedasPorFecha");
		listadoAccionesQueNoEstanEnTerminal.add("reportecantidadResultadosPorTerminal");
		listadoAccionesQueNoEstanEnTerminal.add("enviarMail");
		listadoAccionesQueNoEstanEnTerminal.add("actualizacionLocalesComerciales");
		listadoAccionesQueNoEstanEnTerminal.add("agregarAcciones");
		listadoAccionesQueNoEstanEnTerminal.add("bajaPOIs");
		listadoAccionesQueNoEstanEnTerminal.add("procesoMultiple");
		
		
		unUsuarioAdmin=new Usuario("admin", "123", Rol.ADMIN);
		
		unUsuarioTerminal=new Usuario("terminal", "123", Rol.TERMINAL);
		
		
		

	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnAdmin() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnAdmin) {

			System.out.println("accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnAdmin);
		System.out.println(agregado);
		Assert.assertNull(agregado);
		
	}
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnAdmin() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueEstanEnAdmin) {

			System.out.println("accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnAdmin);
		System.out.println(agregado);
		Assert.assertFalse(agregado);
		
	}
	
	
	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueNoEstanEnTerminal() throws MessagingException {
		
		
		for (String unaAccion : listadoAccionesQueNoEstanEnTerminal) {

			System.out.println("accion en lista "+unaAccion);
			}
		
		for (Usuario unUsuario : db_usuario.getListaUsuarios()) {

			System.out.println("usuario: "+unUsuario.getUsername()+" pasword: "+unUsuario.getPassword());
			}
		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueNoEstanEnTerminal);
		System.out.println(agregado);
		Assert.assertTrue(agregado);
		
	}
}
