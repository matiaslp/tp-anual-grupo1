package ar.edu.utn.dds.grupouno.procesos;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.procesos.AgregarAcciones;

public class TestAgregarAcciones {
	AuthAPI unAuthAPI;
	boolean encontrada;
	boolean encontradaTodas;
	boolean agregado;
	Usuario unUsuarioAdmin;
	Usuario unUsuarioTerminal;
	Usuario unUsuarioAdmin2;
	Usuario unUsuarioTerminal2;
	UsuariosFactory fact = new UsuariosFactory();

	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	private ArrayList<String> listadoAccionesQueEstanEnTerminal;

	AgregarAccionesTransaction transaction;

	@Before
	public void init() {
		listadoAccionesQueEstanEnAdmin = new ArrayList<String>();
		listadoAccionesQueEstanEnTerminal = new ArrayList<String>();
		DB_Usuarios.getInstance().getListaUsuarios().clear();
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

		fact.crearUsuario("admin", "123", Rol.ADMIN);
		fact.crearUsuario("terminal", "123", Rol.TERMINAL);
		fact.crearUsuario("adminPrueba", "123", Rol.ADMIN);
		fact.crearUsuario("terminal1", "123", Rol.TERMINAL);
	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnAdmin() throws MessagingException {

		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnAdmin, transaction);
		// comprobacion si tiene toda la lista a agregar
		encontradaTodas = true;
		for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
			encontrada = DB_Usuarios.getInstance().getUsuarioByName("admin").getFuncionalidad(unafuncionabilidad)!=null;
			if (encontrada == false) {
				encontradaTodas = false;
			}
		}
		Assert.assertTrue(encontradaTodas);

	}

	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnAdmin() throws MessagingException {

		AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnAdmin, transaction);

		// comprobacion si tiene toda la lista a agregar
		encontradaTodas = true;
		for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
			encontrada = DB_Usuarios.getInstance().getUsuarioByName("terminal").getFuncionalidad(unafuncionabilidad)!=null;
			if (encontrada == false) {
				encontradaTodas = false;
			}

		}
		Assert.assertFalse(encontradaTodas);

	}

	@Test
	public void agregarAccionesAUsuarioTerminallistadoAccionesQueEstanEnTerminal() throws MessagingException {

		agregado = AgregarAcciones.AgregarAccionesAUsuario("terminal", listadoAccionesQueEstanEnTerminal, transaction);
		// comprobacion si tiene toda la lista a agregar
		encontradaTodas = true;
		for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
			encontrada = DB_Usuarios.getInstance().getUsuarioByName("terminal").getFuncionalidad(unafuncionabilidad)!=null;
			if (encontrada == false) {
				encontradaTodas = false;
			}

		}
		Assert.assertTrue(encontradaTodas);

	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnTerminal() throws MessagingException {

		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnTerminal, transaction);
		// comprobacion si tiene toda la lista a agregar
		encontradaTodas = true;
		for (String unafuncionabilidad : listadoAccionesQueEstanEnTerminal) {
			encontrada = DB_Usuarios.getInstance().getUsuarioByName("admin").getFuncionalidad(unafuncionabilidad)!=null;
			if (encontrada == false) {
				encontradaTodas = false;
			}

		}
		Assert.assertTrue(encontradaTodas);

	}	
}
