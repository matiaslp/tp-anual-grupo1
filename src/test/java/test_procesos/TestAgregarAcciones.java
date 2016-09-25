package test_procesos;


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
import procesos.AgregarAcciones;


public class TestAgregarAcciones {
	DB_Usuarios db_usuario;
	AuthAPI unAuthAPI;
	
	boolean agregado;
	Usuario unUsuarioAdmin;
	Usuario unUsuarioTerminal;
	
	private String listaAcciones1[];
	private String listaAcciones2[];
	private String listaAcciones3[];
	
	
	@Before
	public void init() {
		db_usuario=db.DB_Usuarios.getInstance();
		unAuthAPI = AuthAPI.getInstance();
		unUsuarioAdmin=new Usuario("a", "123", Rol.ADMIN);
		unUsuarioTerminal=new Usuario("a", "123", Rol.TERMINAL);
		
		
		//PONER LAS SIGUIENTES LISTAS DE ACCIONES
		listaAcciones1[0] = "busquedaPorUsuario";
		

	}

	@Test
	public void agregarAccionesAUsuarioTerminal() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		agregado = AgregarAcciones.AgregarAccionesAUsuario("a", listaAcciones1);
		Assert.assertTrue(agregado);
	}

}
