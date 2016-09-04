package test_miscellaneous;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.DB_HistorialBusquedas;
import db.RegistroHistorico;

import java.util.Map;

public class TestReportes {


	
	@Before
	public void init(){
		
		new DB_HistorialBusquedas();
		DateTime time = new DateTime(2016,1,1,1,1);
		RegistroHistorico registro = new RegistroHistorico(1,time,10,"busqueda1",10,5);
		DB_HistorialBusquedas.agregarHistorialBusqueda(registro);
		
		time = new DateTime(2016,2,2,2,2);
		registro = new RegistroHistorico(2,time,10,"busqueda2",20,10);
		DB_HistorialBusquedas.agregarHistorialBusqueda(registro);
		
		time = new DateTime(2016,3,3,3,3);
		registro = new RegistroHistorico(3,time,10,"busqueda3",30,15);
		DB_HistorialBusquedas.agregarHistorialBusqueda(registro);
		
		time = new DateTime(2016,4,4,4,4);
		registro = new RegistroHistorico(4,time,10,"busqueda4",40,20);
		DB_HistorialBusquedas.agregarHistorialBusqueda(registro);
		
		time = new DateTime(2016,4,4,4,4);
		registro = new RegistroHistorico(5,time,1,"busqueda5",400,20);
		DB_HistorialBusquedas.agregarHistorialBusqueda(registro);
		
	}
	
	
	@Test
	public void testreporteCantidadResultadosPorTerminal(){
		Map<Long,Long> resultado = DB_HistorialBusquedas.reporteCantidadResultadosPorTerminal(10);
//		System.out.printf("Usuario:10\nIdBusqueda  cantidadResultados \n");
//		for (Map.Entry<Long, Long> registro : resultado.entrySet())
//			System.out.printf("%s \t\t  %s \n", 
//		registro.getKey().toString(),registro.getValue().toString());
		Assert.assertTrue(resultado.size() == 4);
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
