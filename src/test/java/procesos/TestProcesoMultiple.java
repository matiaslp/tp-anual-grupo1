package procesos;

import java.io.File;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.UsuariosFactory;
import autentification.funciones.FuncActualizacionLocalesComerciales;
import autentification.funciones.FuncAgregarAcciones;
import autentification.funciones.FuncBajaPOIs;
import autentification.funciones.FuncMultiple;
import db.AgregarAccionesTransaction;
import db.DB_POI;
import db.DB_Usuarios;
import poi.Banco;
import poi.LocalComercial;
import poi.POI;

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
	UsuariosFactory fact = new UsuariosFactory();

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

		fact.crearUsuario("admin", "123", Rol.ADMIN);
		fact.crearUsuario("terminal", "123", Rol.TERMINAL);
		fact.crearUsuario("adminPrueba", "123", Rol.ADMIN);
		fact.crearUsuario("terminal1", "123", Rol.TERMINAL);
	}
	
	@Test
	public void procesoMultipleTest() {

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

		//Init BajaPOI
		//---------------------------
		LocalComercial local1 = new LocalComercial();
		Banco banco1 = new Banco();
		
		local1.setNombre("local1");
		String[] etiquetas1 = { "matadero", "heladeria" };
		local1.setEtiquetas(etiquetas1);
		local1.setFechaBaja(new DateTime());

		banco1.setNombre("banco1");
		banco1.setFechaBaja(new DateTime());

		DB_POI.getInstance().agregarPOI(local1);
		DB_POI.getInstance().agregarPOI(banco1);
		//--------------------
		
		//Init ActualizacionLocalesComerciales
		//---------------------------
		//Creo los locales comerciales pero solo agrego el 3 y 4, 
		//esperando que los otros 2 los cree el proceso
		LocalComercial local2 = new LocalComercial();
		LocalComercial local3 = new LocalComercial();
		LocalComercial local4 = new LocalComercial();
		
		local2.setNombre("local2");
		String[] etiquetas2 = {"juegos", "azul", "moron"};
		local2.setEtiquetas(etiquetas2);
				
		local3.setNombre("local3");
		String[] etiquetas3 = {"azul", "helado", "esquina"};
		local3.setEtiquetas(etiquetas3);
				
		local4.setNombre("local4");
		String[] etiquetas4 = {"matadero", "heladeria"};
		local4.setEtiquetas(etiquetas4);
				
		DB_POI.getInstance().agregarPOI(local3);
		DB_POI.getInstance().agregarPOI(local4);
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
		Proceso proc2 = funcion2.prepDarDeBajaPOI(admin, tokenAdmin, 0, false, 
				(new File (".").getAbsolutePath ()) + "/src/test/java/procesos/bajaPois.json");
		listProc.add(proc2);

		// agregamos un proceso actualizacionLocalesComerciales
		FuncActualizacionLocalesComerciales funcion3 = ((FuncActualizacionLocalesComerciales) admin.getFuncionalidad("actualizacionLocalesComerciales"));
		Proceso proc3 = funcion3.prepAgregarAcciones(admin, tokenAdmin, 0, false, 
				(new File (".").getAbsolutePath ())+"/src/test/java/procesos/actualizarLocalesComerciales.txt");
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

		// Se valida que los elementos ya no existan en la lista
		Assert.assertNull(DB_POI.getInstance().getPOIbyNombre("local1"));
		Assert.assertNull(DB_POI.getInstance().getPOIbyNombre("banco1"));
		
		// Se valida que los elementos se agreguen o actualizen
		POI local2Actualizado = DB_POI.getInstance().getPOIbyNombre("local2");
		POI local3Actualizado = DB_POI.getInstance().getPOIbyNombre("local3");
		
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
	}
	
}
