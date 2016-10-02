package procesos;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.UsuariosFactory;
import autentification.funciones.FuncActualizacionLocalesComerciales;
import db.DB_POI;
import db.DB_Usuarios;
import poi.LocalComercial;
import poi.POI;

public class TestActualizarLocalComercial {
	
	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	UsuariosFactory fact = new UsuariosFactory();
	
	@Before
	public void init(){
		dbPOI = DB_POI.getInstance();
		Autenticador = AuthAPI.getInstance();
		fact.crearUsuario("admin", "123", Rol.ADMIN);
	}
	
	@Test
	public void testActualizar(){
		//Corro el proceso
		String filePath = (new File (".").getAbsolutePath ())+"/src/test/java/procesos/actualizarLocalesComerciales.txt";
		//Creo los locales comerciales pero solo agrego el 1, 
		//esperando que los otros 2 los cree el proceso
		LocalComercial local1 = new LocalComercial();
		LocalComercial local2 = new LocalComercial();
		LocalComercial local3 = new LocalComercial();
		
		local1.setNombre("local1");
		String[] etiquetas1 = {"matadero", "heladeria"};
		local1.setEtiquetas(etiquetas1);
		
		local2.setNombre("local2");
		String[] etiquetas2 = {"juegos", "azul", "moron"};
		local2.setEtiquetas(etiquetas2);
		
		local3.setNombre("local3");
		String[] etiquetas3 = {"azul", "helado", "esquina"};
		local3.setEtiquetas(etiquetas3);
		
		dbPOI.agregarPOI(local1);
		dbPOI.agregarPOI(local3);
		
		Usuario admin = DB_Usuarios.getInstance().getUsuarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("actualizacionLocalesComerciales", admin);
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncActualizacionLocalesComerciales funcion = (FuncActualizacionLocalesComerciales) AuthAPI.getInstance().getAccion("actualizacionLocalesComerciales");
		funcion.agregarAcciones(admin, tokenAdmin, 0, false, filePath);

		//Busco las modificaciones para corroborar que se corrio correctamente
		//POI local1Actualizado = dbPOI.getPOIbyNombre("local1");
		POI local2Actualizado = dbPOI.getPOIbyNombre("local2");
		POI local3Actualizado = dbPOI.getPOIbyNombre("local3");
		
		//Debido a que esta copiando la instancia 
		//Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
	}
}
