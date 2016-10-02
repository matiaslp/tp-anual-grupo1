package procesos;

import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncActualizacionLocalesComerciales;
import autentification.funciones.FuncAgregarAcciones;
import autentification.funciones.FuncBajaPOIs;
import autentification.funciones.FuncMultiple;
import db.AgregarAccionesTransaction;
import db.DB_Usuarios;

public class TestProcesoMultiple {

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
	
	
	void procesoMultipleTest() {

		// usuario admin y sus funcionalidades
		Usuario admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("agregarAcciones", admin);
		AuthAPI.getInstance().agregarFuncionalidad("procesoMultiple", admin);
		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
		
		// creamos un usuario adminPrueba
		Usuario adminPrueba = DB_Usuarios.getInstance().getUsuarioByName("adminPrueba");
		AuthAPI.getInstance().sacarFuncionalidad("cambiarEstadoMail",adminPrueba);
		AuthAPI.getInstance().sacarFuncionalidad("actualizacionLocalesComerciales",adminPrueba);
		Assert.assertFalse(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertFalse(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);
		
		// creamos usuario unUsuarioTerminal1 y le sacamos funcionalidades
		Usuario unUsuarioTerminal1 = DB_Usuarios.getInstance().getUsuarioByName("terminal1");
		AuthAPI.getInstance().sacarFuncionalidad("busquedaPOI",unUsuarioTerminal1);
		AuthAPI.getInstance().sacarFuncionalidad("obtenerInfoPOI",unUsuarioTerminal1);
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);
		Assert.assertFalse(unUsuarioTerminal1.getFuncionalidad("obtenerInfoPOI")!=null);
		
		
		
		
		
		//---------------------------
		// TODO inicializar entorno para proceso BajaPOIs
		//--------------------
		
		
		//---------------------------
		// TODO inicializar entorno para proceso ActualizacionLocalesComerciales
		//--------------------
		
		
		// iniciamos sesion con usuario admin
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");

		ArrayList<Proceso> listProc = new ArrayList<Proceso>();

		// agregamos un proceso de AgregarAcciones
		FuncAgregarAcciones funcion = ((FuncAgregarAcciones) admin.getFuncionalidad("agregarAcciones"));
		Proceso proc1 = funcion.prepAgregarAcciones(admin, tokenAdmin, 0, false,
				(new File(".").getAbsolutePath()) + "/src/test/java/procesos/accionesAAgregar");
		listProc.add(proc1);

		// agregamos un proceso bajaPOIs
		FuncBajaPOIs funcion2 = ((FuncBajaPOIs) admin.getFuncionalidad("bajaPOIs"));
		Proceso proc2 = funcion2.prepDarDeBajaPOI(admin, tokenAdmin, 0, false);
		listProc.add(proc2);

		// agregamos un proceso actualizacionLocalesComerciales
		FuncActualizacionLocalesComerciales funcion3 = ((FuncActualizacionLocalesComerciales) admin.getFuncionalidad("actualizacionLocalesComerciales"));
		Proceso proc3 = funcion3.prepAgregarAcciones(admin, tokenAdmin, 0, false, ""); // TODO agregar filepath
		listProc.add(proc3);

		// Ejecutamos el proceso multiple
		FuncMultiple funcion1 = ((FuncMultiple) admin.getFuncionalidad("procesoMultiple"));
		funcion1.procesoMultiple(admin, tokenAdmin, 0, false, listProc);
		
		
		
		// Validaciones Proceso AgregarAcciones
		// Se valida que el usuario adminPrueba tiene las funcionalidades agregadas
		Assert.assertTrue(adminPrueba.getFuncionalidad("cambiarEstadoMail")!=null);
		Assert.assertTrue(adminPrueba.getFuncionalidad("actualizacionLocalesComerciales")!=null);

		// Se valida que el usuario unUsuarioTerminal1 tiene la funcionalidad agregada
		Assert.assertTrue(unUsuarioTerminal1.getFuncionalidad("busquedaPOI")!=null);

		
		
		
	}
	
}
