package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.RegistroHistoricoMorphia;
import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;
import ar.edu.utn.dds.grupouno.repositorio.RepoMongo;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;


public class TestReportes {

	private DB_HistorialBusquedas historial;
	Usuario terminal;
	UsuariosFactory fact;

	ArrayList<POI> listaDePOIs;
	LocalComercial local1;
	Banco banco1;
	RegistroHistorico registro1, registro2, registro3, registro4, registro5;

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

		DateTime time = new DateTime(2016, 1, 1, 1, 1);
		registro1 = new RegistroHistorico(time, 10, "busqueda1", 10, 5, listaDePOIs);
		historial.agregarHistorialBusqueda(registro1);

		time = new DateTime(2016, 2, 2, 2, 2);
		registro2 = new RegistroHistorico(time, 10, "busqueda2", 20, 10, listaDePOIs);
		historial.agregarHistorialBusqueda(registro2);

		time = new DateTime(2016, 3, 3, 3, 3);
		registro3 = new RegistroHistorico(time, 10, "busqueda3", 30, 15, listaDePOIs);
		historial.agregarHistorialBusqueda(registro3);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro4 = new RegistroHistorico(time, 10, "busqueda4", 40, 20, listaDePOIs);
		historial.agregarHistorialBusqueda(registro4);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro5 = new RegistroHistorico(time, 1, "busqueda5", 400, 20, listaDePOIs);
		historial.agregarHistorialBusqueda(registro5);

	}

	@Test
	public void testreporteCantidadResultadosPorTerminal() {
		ArrayList<Object[]> resultado = historial.reporteCantidadResultadosPorTerminal((long) 10);
		
		Assert.assertTrue(resultado.size() == 4);
	}

	@Test
	public void testReporteBusquedaPorFecha() {
		ArrayList<Object[]> resultado = historial.reporteBusquedasPorFecha();

		Map<String, Integer> resumen = new HashMap<String, Integer>();
		for (Object obj[] : resultado) {
			resumen.put(((Date) obj[0]).toString(), ((Integer) obj[1]));
		}

		Locale lenguaje = Locale.getDefault();
		if (lenguaje.equals(Locale.US) || lenguaje.equals(Locale.ENGLISH) || lenguaje.equals(Locale.UK)) {
			// Computadoras en ingles
			Assert.assertTrue(resumen.get("2016-04-04") == 2);
			Assert.assertTrue(resumen.get("2016-03-03") == 1);
			Assert.assertTrue(resumen.get("2016-02-02") == 1);
			Assert.assertTrue(resumen.get("2016-01-01") == 1);
		} else {
			// Computadoras en espa�ol
			// Assert.assertTrue(resumen.get("04/04/16") == 440);
			// Assert.assertTrue(resumen.get("03/03/16") == 30);
			// Assert.assertTrue(resumen.get("02/02/16") == 20);
			// Assert.assertTrue(resumen.get("01/01/16") == 10);

		}

	}

	@Test
	public void testReporteCantidadResultadosPorUsuario() {
		ArrayList<Object[]> resultado = historial.reporteBusquedaPorUsuario();

		Assert.assertTrue(resultado.size() == 2);
	}
	
	@Test
	public void testBusquedaHistorial2Fechas(){
		ArrayList<Object[]> resultado = historial.historialBusquedaEntreFechas(10,
				Date.from(MetodosComunes.convertJodatoJava(registro1.getTime()).toInstant()),
				Date.from(MetodosComunes.convertJodatoJava(registro4.getTime()).toInstant()));
		
		Assert.assertTrue(resultado.size() == 2);
	}
	
	@Test
	public void testBusquedaHistorialFinalInicial(){
		ArrayList<Object[]> resultado = historial.historialBusquedaEntreFechas(10,
				Date.from(MetodosComunes.convertJodatoJava(registro1.getTime()).toInstant()),
				null);
		
		Assert.assertTrue(resultado.size() == 3);
	}
	@Test
	public void testBusquedaHistorialFechaFinal(){
		ArrayList<Object[]> resultado = historial.historialBusquedaEntreFechas(10,null,
				Date.from(MetodosComunes.convertJodatoJava(registro4.getTime()).toInstant()));
		
		Assert.assertTrue(resultado.size() == 3);
	}
	@Test
	public void testBusquedaHistorialNull(){
		ArrayList<Object[]> resultado = historial.historialBusquedaEntreFechas(10,null,null);
		
		Assert.assertTrue(resultado.size() == 4);
	}

	@After
	public void outtro() {
		RepoMongo.getInstance().getDatastore()
		.delete(RepoMongo.getInstance().getDatastore()
				.createQuery(RegistroHistoricoMorphia.class));
//		Repositorio.getInstance().remove(registro1);
//		Repositorio.getInstance().remove(registro2);
//		Repositorio.getInstance().remove(registro3);
//		Repositorio.getInstance().remove(registro4);
//		Repositorio.getInstance().remove(registro5);
		Repositorio.getInstance().remove(local1);
		Repositorio.getInstance().remove(banco1);

	}
}
