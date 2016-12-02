package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestReportes {

	private DB_HistorialBusquedas historial;
	Usuario terminal;
	UsuariosFactory fact;

	ArrayList<POI> listaDePOIs;
	LocalComercial local1;
	Banco banco1;
	RegistroHistorico registro1,registro2,registro3,registro4,registro5;

	@Before
	public void init() {
		listaDePOIs = new ArrayList<POI>();
		local1 = new LocalComercial();
		banco1 = new Banco();
		
		
		local1.setNombre("local1");
		banco1.setNombre("banco1");

		
		
		listaDePOIs.add(local1);
		listaDePOIs.add(banco1);
		
		Repositorio.getInstance().pois().agregarPOI(local1);
		Repositorio.getInstance().pois().agregarPOI(banco1);
		
		historial = Repositorio.getInstance().resultadosRegistrosHistoricos();
		
		fact = new UsuariosFactory();
		fact.crearUsuario("terminal", "123", "TERMINAL");
		terminal = Repositorio.getInstance().usuarios().getUsuarioByName("terminal");

		
		DateTime time = new DateTime(2016, 1, 1, 1, 1);
		registro1 = new RegistroHistorico( time, 10, "busqueda1", 10, 5,listaDePOIs);
		historial.agregarHistorialBusqueda(registro1);

		time = new DateTime(2016, 2, 2, 2, 2);
		registro2 = new RegistroHistorico(time, 10, "busqueda2", 20, 10,listaDePOIs);
		historial.agregarHistorialBusqueda(registro2);

		time = new DateTime(2016, 3, 3, 3, 3);
		registro3 = new RegistroHistorico(time, 10, "busqueda3", 30, 15,listaDePOIs);
		historial.agregarHistorialBusqueda(registro3);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro4 = new RegistroHistorico(time, 10, "busqueda4", 40, 20,listaDePOIs);
		historial.agregarHistorialBusqueda(registro4);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro5 = new RegistroHistorico(time, 1, "busqueda5", 400, 20,listaDePOIs);
		historial.agregarHistorialBusqueda(registro5);

	}

	@Test
	public void testreporteCantidadResultadosPorTerminal() {
		ArrayList<Object[]> resultado = historial.reporteCantidadResultadosPorTerminal((long) 10);
		// System.out.printf("Usuario:10\nIdBusqueda cantidadResultados \n");
		// for (Map.Entry<Long, Long> registro : resultado.entrySet())
		// System.out.printf("%s \t\t %s \n",
		// registro.getKey().toString(),registro.getValue().toString());
		Assert.assertTrue(resultado.size() == 4);
	}

	@Test
	public void testReporteBusquedaPorFecha() {
		ArrayList<Object[]> resultado = historial.reporteBusquedasPorFecha();
		
		Map<String, Long> resumen = new HashMap<String, Long>();
		for (Object obj[] : resultado){
			resumen.put(((Date)obj[0]).toString(),((Long)obj[1]));
		//	System.out.printf("%s %d \n", ((Date)obj[0]).toString(), ((Long)obj[1]));
		}
		
		Locale lenguaje = Locale.getDefault();
		if(lenguaje.equals(Locale.US)||lenguaje.equals(Locale.ENGLISH)||lenguaje.equals(Locale.UK)){
			// Computadoras en ingles
			Assert.assertTrue(resumen.get("2016-04-04") == 2);
			Assert.assertTrue(resumen.get("2016-03-03") == 1);
			Assert.assertTrue(resumen.get("2016-02-02") == 1);
			Assert.assertTrue(resumen.get("2016-01-01") == 1);
		}else{
			// Computadoras en espa�ol
//			Assert.assertTrue(resumen.get("04/04/16") == 440);
//			Assert.assertTrue(resumen.get("03/03/16") == 30);
//			Assert.assertTrue(resumen.get("02/02/16") == 20);
//			Assert.assertTrue(resumen.get("01/01/16") == 10);

		}



	}

	@Test
	public void testReporteCantidadResultadosPorUsuario() {
		ArrayList<Object[]> resultado = historial.reporteBusquedaPorUsuario();

		
//		Map<Long, Long> resumen = new HashMap<Long, Long>();
//		for (Object obj[] : resultado){
//			resumen.put(((Long)obj[0]),((Long)obj[1]));
//		}
//		 System.out.printf("\nIdUsuario cantidadResultados \n");
//		 for (Map.Entry<Long, Long> registro : resumen.entrySet())
//		 System.out.printf("%s \t\t %s \n",
//		 registro.getKey().toString(),registro.getValue().toString());

		Assert.assertTrue(resultado.size() == 2);
	}
	
	@After
	public void outtro() {
		
		Repositorio.getInstance().remove(registro1);
		Repositorio.getInstance().remove(registro2);
		Repositorio.getInstance().remove(registro3);
		Repositorio.getInstance().remove(registro4);
		Repositorio.getInstance().remove(registro5);
		Repositorio.getInstance().remove(local1);
		Repositorio.getInstance().remove(banco1);
		
	}
}
