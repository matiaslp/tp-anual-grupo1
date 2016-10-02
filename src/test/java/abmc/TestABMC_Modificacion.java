package abmc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.POI_ABMC;
import abmc.POI_DTO;
import db.DB_POI;
import poi.Rubro;
import poi.TiposPOI;

public class TestABMC_Modificacion {
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
		instancia = DB_POI.getInstance();

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
		instancia.agregarPOI(poiDTOBanco.converttoPOI());
		instancia.agregarPOI(poiDTOCGP.converttoPOI());
		instancia.agregarPOI(poiDTOComercial.converttoPOI());
		instancia.agregarPOI(poiDTOColectivo.converttoPOI());
	}

	@Test
	public void modificacionBanco() {

		poiDTOBanco.setNombre("unBancoModificado");
		poiDTOBanco.setId(1);
		boolean respuesta = poi_abmc.modificar(poiDTOBanco);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionCGP() {
		poiDTOCGP.setNombre("unCGPModificado");
		poiDTOCGP.setId(2);
		boolean respuesta = poi_abmc.modificar(poiDTOCGP);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionLocalComercial() {
		poiDTOComercial.setNombre("unLocalComercialModificado");
		poiDTOComercial.setId(3);
		boolean respuesta = poi_abmc.modificar(poiDTOComercial);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionParadaColectivo() {
		poiDTOColectivo.setNombre("unaParadaColectivoModificado");
		poiDTOColectivo.setId(4);
		boolean respuesta = poi_abmc.modificar(poiDTOColectivo);
		Assert.assertTrue(respuesta);
	}

	// Test modificacion POI inexistente
	@Test
	public void modificacionPOIInexistente() {
		poiDTOColectivo.setNombre("unaParadaColectivoModificado");
		poiDTOColectivo.setId(100);
		boolean respuesta = poi_abmc.modificar(poiDTOColectivo);
		Assert.assertFalse(respuesta);
	}

	// Test modificacion de todos los atributos de un POI (de Banco a Colectivo)
	@Test
	public void modificacionPOITodosLosAtributos() {
		poiDTOColectivo.setNombre("unaParadaColectivoModificado");
		poiDTOColectivo.setId(1);
		boolean respuesta = poi_abmc.modificar(poiDTOColectivo);
		Assert.assertTrue(respuesta);
	}
}
