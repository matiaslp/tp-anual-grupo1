package ar.edu.utn.dds.grupouno.test_poi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.db.poi.CGP;
import ar.edu.utn.dds.grupouno.db.poi.POI;

public class TestDistanciaXEntrePOIs {
	public POI poiUno;
	public POI poiDos;

	@Before
	public void init() {
		poiUno = new CGP("poiUno", -34.601186, -58.383225);
		poiDos = new CGP("poiDos", -34.602405, -58.385875);
	}

	/*
	 * Dado que todos los hijos de POI utilizan la misma definicion para el
	 * metodo "estaXMetrosDePOI()" solo se realizo test unitario con una sola
	 * clase
	 */
	@Test
	public void testeodistanciavenora3() {
		Assert.assertTrue(poiUno.estaXMetrosDePOI(2000000, poiDos));
	}
}
