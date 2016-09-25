package test_poi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.POI;
import poi.ParadaColectivo;
import poi.Rubro;

public class TestCalculoDeCercania {

	public POI POIreferencia;
	public POI cgp;
	public Rubro rubro;
	public POI local;
	public POI parada;
	public POI banco;

	@Before
	public void init() {
		// Definimos un POI de referencia contra el cual medir distancias
		POIreferencia = new CGP("POIreferencia", -34.594269, -58.430131);
		rubro = new Rubro("rubro");

	}
	// --------------------------------------------------------------------------------------------------------
	// CGP
	// --------------------------------------------------------------------------------------------------------

	@Test
	public void testcercaniaCGPFalso() {
		
		// LAs comunas deben ser distintas para que esten lejos los poi
		POIreferencia.setComuna(1);
		// Localizado a ~5 cuadras
		cgp = new CGP("cgp", -34.590785, -58.435354);
		cgp.setComuna(20);
		Assert.assertFalse(cgp.esCercano(POIreferencia));
	}

	@Test
	public void testcercaniaCGPVerdadero() {
		// LAs comunas deben ser distintas para que esten cerca los poi
		POIreferencia.setComuna(1);
		// Localizado a ~5 cuadras
		cgp = new CGP("cgp", -34.590785, -58.435354);
		cgp.setComuna(1);
		Assert.assertTrue(cgp.esCercano(POIreferencia));
	}

	// --------------------------------------------------------------------------------------------------------
	// LOCAL COMERCIAL
	// --------------------------------------------------------------------------------------------------------

	@Test
	public void testcercaniaLocalFalso() {
		// Definimos el limite de cercania en 2 cuadras
		rubro.setCercania(200);
		// Localizado a ~5 cuadras
		local = new LocalComercial("Local", -34.590785, -58.435354, rubro);
		Assert.assertFalse(local.esCercano(POIreferencia));
	}

	@Test
	public void testcercaniaLocalVerdadero() {
		// Definimos el limite de cercania en 10 cuadras
		rubro.setCercania(1000);
		// Localizado a ~5 cuadras
		// -34.594269, -58.430131
		// local = new LocalComercial("Local",-34.590785, -58.435354,rubro);
		local = new LocalComercial("Local", -34.590785, -58.432254, rubro);
		Assert.assertTrue(local.esCercano(POIreferencia));
	}
	// --------------------------------------------------------------------------------------------------------
	// PARADA DE COLECTIVO
	// --------------------------------------------------------------------------------------------------------

	@Test
	public void testcercaniaParadaFalso() {
		// El limite de cercania es de 1 cuadra
		// Localizado a ~5 cuadras
		parada = new ParadaColectivo("Parada", -34.590785, -58.435354);
		Assert.assertFalse(parada.esCercano(POIreferencia));
	}

	@Test
	public void testcercaniaParadaVerdadero() {
		// El limite de cercania es de 1 cuadra
		// Localizado a ~1/2 cuadra
		parada = new ParadaColectivo("Parada", -34.593945, -58.430648);
		Assert.assertTrue(parada.esCercano(POIreferencia));
	}

	// --------------------------------------------------------------------------------------------------------
	// BANCO o CASO GENERAL
	// --------------------------------------------------------------------------------------------------------

	@Test
	public void testcercaniaBancoFalso() {
		// El limite de cercania es de 7 cuadras
		// Localizado a ~5 cuadras
		banco = new Banco("Banco", -34.589586, -58.437212);
		Assert.assertFalse(banco.esCercano(POIreferencia));
	}

	@Test
	public void testcercaniaBancoVerdadero() {
		// El limite de cercania es de 5 cuadras
		// Localizado a ~1/2 cuadra
		// banco = new Banco("Banco",-34.590785, -58.435354); original
		banco = new Banco("Banco", -34.592985, -58.430154);
		Assert.assertTrue(banco.esCercano(POIreferencia));
	}

}
