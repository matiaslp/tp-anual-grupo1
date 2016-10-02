package procesos;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import autentification.funciones.FuncActualizacionLocalesComerciales;
import autentification.funciones.FuncBajaPOIs;
import db.DB_POI;
import db.DB_Usuarios;
import poi.Banco;
import poi.LocalComercial;
import poi.POI;

public class TestBajaPOI {
	
	DB_POI dbPOI;
	Usuario unUsuarioAdmin;
	AuthAPI Autenticador;
	
	@Before
	public void init(){
		dbPOI = DB_POI.getInstance();
		Autenticador = AuthAPI.getInstance();
		unUsuarioAdmin = new Usuario("admin", "123", Rol.ADMIN);
	}
	
	@Test
	public void testBorrar(){
		//Corro el proceso
		String filePath = (new File (".").getAbsolutePath ())+ "\\src\\test\\java\\procesos\\actualizarLocalesComerciales.txt";
		
		//Creo los locales comerciales pero solo agrego el 1, 
		//esperando que los otros 2 los cree el proceso
		LocalComercial local1 = new LocalComercial();
		Banco banco1 = new Banco();
		LocalComercial local3 = new LocalComercial();
		
		local1.setNombre("local1");
		String[] etiquetas1 = {"matadero", "heladeria"};
		local1.setEtiquetas(etiquetas1);
		
		banco1.setNombre("banco1");
				
		dbPOI.agregarPOI(local1);
		dbPOI.agregarPOI(banco1);
		
		Usuario admin = DB_Usuarios.getInstance().getUsarioByName("admin");
		AuthAPI.getInstance().agregarFuncionalidad("bajaPOIs", admin);
		String tokenAdmin = AuthAPI.getInstance().iniciarSesion("admin", "123");
		FuncBajaPOIs funcion = (FuncBajaPOIs) AuthAPI.getInstance().getAccion("bajaPOIs");
		funcion.darDeBajaPOI(admin, tokenAdmin, 0, false);
		
		//Busco las modificaciones para corroborar que se corrio correctamente
		//POI local1Actualizado = dbPOI.getPOIbyNombre("local1");
		POI local2Actualizado = dbPOI.getPOIbyNombre("local2");
		POI local3Actualizado = dbPOI.getPOIbyNombre("local3");
		
		//Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
	}
}
