package test_abmc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.POI_ABMC;
import abmc.POI_DTO;
import db.DB_Server;
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
	DB_Server unServer;
	DB_Server instancia;

	@Before
	public void init() {

		poi_abmc = new POI_ABMC();
		unServer = new DB_Server();
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

		// Se crean 4 POIs (uno por cada tipo)
		DB_Server.agregarPOI(poiDTOBanco.converttoPOI());
		DB_Server.agregarPOI(poiDTOCGP.converttoPOI());
		DB_Server.agregarPOI(poiDTOComercial.converttoPOI());
		DB_Server.agregarPOI(poiDTOColectivo.converttoPOI());
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
		DB_Server.agregarPOI(poiDTOColectivo.converttoPOI());
		poi_abmc.delete(5);
		boolean respuesta = poi_abmc.delete(5);
		Assert.assertFalse(respuesta);

	}
}
