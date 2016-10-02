package procesos;

import java.util.ArrayList;

import org.junit.Before;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncAgregarAcciones;
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
		// iniciamos sesion con usuario admin
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");

		ArrayList<Proceso> listProc = new ArrayList<Proceso>();
		if (admin.chequearFuncionalidad("procesoMultiple")) {
			
			// agregamos un proceso de agregarAcciones
			if (admin.chequearFuncionalidad("agregarAcciones")) {
				FuncAgregarAcciones funcion = ((FuncAgregarAcciones) AuthAPI.getInstance()
						.getAccion("agregarAcciones"));
				Proceso proc1 = funcion.prepAgregarAcciones(admin, tokenAdmin, 0, false, "");
				listProc.add(proc1);
			}
			
			// agregamos otro proceso
			
			//agregamos otro proceso
			
			
			FuncMultiple funcion = ((FuncMultiple) AuthAPI.getInstance()
					.getAccion("procesoMultiple"));
			funcion.procesoMultiple(admin, tokenAdmin, 0, false, listProc);
		}
		
		
		
	}
	
	
	
	
}
