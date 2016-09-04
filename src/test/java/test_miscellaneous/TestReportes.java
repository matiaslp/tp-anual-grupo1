package test_miscellaneous;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.DB_HistorialBusquedas;
import db.RegistroHistorico;

public class TestReportes {

	private DB_HistorialBusquedas historial;
	private DateTimeFormatter fmt = DateTimeFormat.shortDate();
	
	@Before
	public void init() {

		historial = DB_HistorialBusquedas.getInstance();
		
		new DB_HistorialBusquedas();
		DateTime time = new DateTime(2016,1,1,1,1);
		
		time = new DateTime(2016, 1, 1, 1, 1);
		RegistroHistorico registro = new RegistroHistorico(1, time, 10, "busqueda1", 10, 5);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 2, 2, 2, 2);
		registro = new RegistroHistorico(2, time, 10, "busqueda2", 20, 10);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 3, 3, 3, 3);
		registro = new RegistroHistorico(3, time, 10, "busqueda3", 30, 15);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro = new RegistroHistorico(4, time, 10, "busqueda4", 40, 20);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro = new RegistroHistorico(5, time, 1, "busqueda5", 400, 20);
		historial.agregarHistorialBusqueda(registro);

	}

	@Test
	public void testreporteCantidadResultadosPorTerminal() {
		Map<Long, Long> resultado = historial.reporteCantidadResultadosPorTerminal(10);
		// System.out.printf("Usuario:10\nIdBusqueda cantidadResultados \n");
		// for (Map.Entry<Long, Long> registro : resultado.entrySet())
		// System.out.printf("%s \t\t %s \n",
		// registro.getKey().toString(),registro.getValue().toString());
		Assert.assertTrue(resultado.size() == 4);
	}
	
	@Test
	public void testReporteBusquedaPorFecha(){
		Map<String, Long> resultado = historial.reporteBusquedasPorFecha();
		Assert.assertTrue(resultado.get("04/04/16") == 440);
		Assert.assertTrue(resultado.get("03/03/16") == 30);
		Assert.assertTrue(resultado.get("02/02/16") == 20);
		Assert.assertTrue(resultado.get("01/01/16") == 10);
		
	}

	@Test
	public void testReporteCantidadResultadosPorUsuario() {
		Map<Long, Long> resultado = DB_HistorialBusquedas.reporteBusquedaPorUsuario();
		
//		System.out.printf("\nIdUsuario  cantidadResultados \n");
//		for (Map.Entry<Long, Long> registro : resultado.entrySet())
//			System.out.printf("%s \t\t  %s \n", 
//		registro.getKey().toString(),registro.getValue().toString());
		
		Assert.assertTrue(resultado.size() == 2);
	}
}
