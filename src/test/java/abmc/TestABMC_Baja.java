package abmc;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.POI_ABMC;
import abmc.POI_DTO;
import db.DB_POI;
import poi.Rubro;
import poi.TiposPOI;

public class TestABMC_Baja {
	POI_ABMC abmc = new POI_ABMC();
	POI_ABMC poi_abmc;
	POI_DTO poiDTOBanco;
	POI_DTO poiDTOCGP;
	POI_DTO poiDTOComercial;
	POI_DTO poiDTOColectivo;
	Rubro rubro;
	DB_POI unServer;
	DB_POI instancia;

	@Before
	public void init() {

		poi_abmc = new POI_ABMC();
		unServer = new DB_POI();
		instancia = unServer.getInstance();

		poiDTOBanco = new POI_DTO();
		poiDTOBanco.setTipo(TiposPOI.BANCO);
		poiDTOBanco.setNombre("unBancoJorge!");
		poiDTOBanco.setLatitud(-34.5664823);
		poiDTOBanco.setLongitud(-34.5664823);

		poiDTOCGP = new POI_DTO();
		poiDTOCGP.setTipo(TiposPOI.CGP);
		poiDTOCGP.setNombre("unCGP");
		poiDTOCGP.setLatitud(-34.5664823);
		poiDTOCGP.setLongitud(-34.5664823);
		poiDTOCGP.setRubro(rubro = new Rubro("unRubro"));

		poiDTOComercial = new POI_DTO();
		poiDTOComercial.setTipo(TiposPOI.LOCAL_COMERCIAL);
		poiDTOComercial.setNombre("unLocalComercial");
		poiDTOComercial.setLatitud(-34.5664823);
		poiDTOComercial.setLongitud(-34.5664823);

		poiDTOColectivo = new POI_DTO();
		poiDTOColectivo.setTipo(TiposPOI.PARADA_COLECTIVO);
		poiDTOColectivo.setNombre("unaParadaDeColectivo");
		poiDTOColectivo.setLatitud(-34.5664823);
		poiDTOColectivo.setLongitud(-34.5664823);
		
		DateTimeFormatter formatoFecha = DateTimeFormat.forPattern("dd, MMMM, yyyy");
		DateTime hoy = new DateTime();

		// Se crean 4 POIs (uno por cada tipo)
		DB_POI.agregarPOI(poiDTOBanco.converttoPOI());
		DB_POI.agregarPOI(poiDTOCGP.converttoPOI());
		DB_POI.agregarPOI(poiDTOComercial.converttoPOI());
		DB_POI.agregarPOI(poiDTOColectivo.converttoPOI());
	}

	// Se realizan 4 test de borrado, uno por cada POI
	@Test
	public void bajaBanco() {
		boolean respuesta = poi_abmc.delete(1);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void bajaCGP() {
		boolean respuesta = poi_abmc.delete(2);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void bajaLocalComercial() {
		boolean respuesta = poi_abmc.delete(3);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void bajaParadaColectivo() {
		boolean respuesta = poi_abmc.delete(4);
		Assert.assertTrue(respuesta);
	}

	// Comprobamos que no se puede borrar un POI al ingresar un ID inexistente
	@Test
	public void borrarInexistente() {
		boolean respuesta = poi_abmc.delete(10);
		Assert.assertFalse(respuesta);
	}

	// Comprobamos que efectivamente se elimina el POI
	@Test
	public void comprobarInexistencia() {
		DB_POI.agregarPOI(poiDTOColectivo.converttoPOI());
		poi_abmc.delete(5);
		boolean respuesta = poi_abmc.delete(5);
		Assert.assertFalse(respuesta);
	}
	
	@Test
	public void darDeBajaUnPOI() {
		DateTime hoy = new DateTime();
		DB_POI.getListado().get(1).darDeBaja(hoy);
		Assert.assertTrue(DB_POI.getListado().get(1).dadoDeBaja());
	}
	
	@Test
	public void darDeBajaPoiDadoDeBaja() {
		DateTime hoy = new DateTime();
		DB_POI.getListado().get(1).darDeBaja(hoy);
		Assert.assertFalse(DB_POI.getListado().get(1).darDeBaja(hoy));
		
	}
}
