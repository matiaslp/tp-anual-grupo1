package procesos;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Rol;
import autentification.Usuario;
import db.DB_POI;
import poi.LocalComercial;
import poi.POI;

public class Test_ActualizarLocalComercial {
	
	DB_POI dbPOI;
	Usuario usuario;
	
	@Before
	public void init(){
		dbPOI = DB_POI.getInstance();
		usuario = new Usuario("aa", "bb", Rol.ADMIN);
	}
	
	@Test
	public void testActualizar(){
		//Corro el proceso
		//URL url = getClass().getResource("actualizarLocalesComerciales.txt");
		String filePath = (new File (".").getAbsolutePath ())+ "\\src\\test\\java\\procesos\\actualizarLocalesComerciales.txt";
		
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
		ActualizacionLocalesComerciales alc = new ActualizacionLocalesComerciales(0, false, false, filePath, usuario);	
		alc.procesarArchivo(filePath);
		//Busco las modificaciones para corroborar que se corrio correctamente
		//POI local1Actualizado = dbPOI.getPOIbyNombre("local1");
		POI local2Actualizado = dbPOI.getPOIbyNombre("local2");
		POI local3Actualizado = dbPOI.getPOIbyNombre("local3");
		
		//Assert.assertFalse(local1Actualizado.compararEtiquetas(local1));
		Assert.assertTrue(local2Actualizado.compararEtiquetas(local2));
		Assert.assertTrue(local3Actualizado.compararEtiquetas(local3));
	}
}
