package ar.edu.utn.dds.grupouno.procesos;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

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
		Repositorio.getInstance().usuarios().getListaUsuarios().clear();
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

		fact.crearUsuario("admin", "123", "ADMIN");
		fact.crearUsuario("terminal", "123", "TERMINAL");
		fact.crearUsuario("adminPrueba", "123", "ADMIN");
		fact.crearUsuario("terminal1", "123", "TERMINAL");
	}

	@Test
	public void agregarAccionesAUsuarioAdminlistadoAccionesQueEstanEnAdmin() throws MessagingException {

		agregado = AgregarAcciones.AgregarAccionesAUsuario("admin", listadoAccionesQueEstanEnAdmin, transaction);
		// comprobacion si tiene toda la lista a agregar
		encontradaTodas = true;
		for (String unafuncionabilidad : listadoAccionesQueEstanEnAdmin) {
			encontrada = Repositorio.getInstance().usuarios().getUsuarioByName("admin").getFuncionalidad(unafuncionabilidad)!=null;
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
			encontrada = Repositorio.getInstance().usuarios().getUsuarioByName("terminal").getFuncionalidad(unafuncionabilidad)!=null;
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
			encontrada = Repositorio.getInstance().usuarios().getUsuarioByName("terminal").getFuncionalidad(unafuncionabilidad)!=null;
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
			encontrada = Repositorio.getInstance().usuarios().getUsuarioByName("admin").getFuncionalidad(unafuncionabilidad)!=null;
			if (encontrada == false) {
				encontradaTodas = false;
			}

		}
		Assert.assertTrue(encontradaTodas);

	}	
}
