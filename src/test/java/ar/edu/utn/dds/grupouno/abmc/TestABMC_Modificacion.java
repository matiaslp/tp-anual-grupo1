package ar.edu.utn.dds.grupouno.abmc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.Rubro;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class TestABMC_Modificacion {
	POI_ABMC abmc = new POI_ABMC();
	POI_ABMC poi_abmc;
	POI_DTO poiDTOBanco, poiDTOCGP, poiDTOComercial, poiDTOColectivo, poiDTONoPersistido;
	POI banco, cgp, local, parada;
	Rubro rubro;
	DB_POI unServer;
	DB_POI instancia;

	@Before
	public void init() {

		poi_abmc = new POI_ABMC();
		instancia = Repositorio.getInstance().pois();

		poiDTOBanco = new POI_DTO();
		poiDTOBanco.setTipo(TiposPOI.BANCO);
		poiDTOBanco.setNombre("unBanco");
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
		banco = poiDTOBanco.converttoPOI();
		cgp = poiDTOCGP.converttoPOI();
		local = poiDTOComercial.converttoPOI();
		parada = poiDTOColectivo.converttoPOI();
		instancia.agregarPOI(banco);
		instancia.agregarPOI(cgp);
		instancia.agregarPOI(local);
		instancia.agregarPOI(parada);

		poiDTONoPersistido = new POI_DTO();
		poiDTONoPersistido.setTipo(TiposPOI.BANCO);
		poiDTONoPersistido.setNombre("noPersistido");
		poiDTONoPersistido.setLatitud(-34.5664823);
		poiDTONoPersistido.setLongitud(-34.5664823);
	}

	@Test
	public void modificacionBanco() {
		POI poi = instancia.getPOIbyNombre("unBanco").get(0);
		poi.setNombre("unBancoModificado");
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionCGP() {
		POI poi = instancia.getPOIbyNombre("unCGP").get(0);
		poi.setNombre("unCGPModificado");
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionLocalComercial() {
		POI poi = instancia.getPOIbyNombre("unLocalComercial").get(0);
		poi.setNombre("unLocalComercialModificado");
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void modificacionParadaColectivo() {
		POI poi = instancia.getPOIbyNombre("unaParadaDeColectivo").get(0);
		poi.setNombre("unaParadaColectivoModificado");
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertTrue(respuesta);
	}

	// Test modificacion POI inexistente
	@Test
	public void modificacionPOIInexistente() {
		POI poi = null;
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertFalse(respuesta);
	}

	// Test modificacion POI no persistido
	@Test
	public void modificacionPOINoPersistido() {
		POI poi = poiDTONoPersistido.converttoPOI();
		poi.setNombre("unaParadaColectivoModificado");
		boolean respuesta = poi_abmc.modificar(poi);
		Assert.assertFalse(respuesta);
	}

	@After
	public void outtro() {

		instancia.remove(banco);
		instancia.remove(cgp);
		instancia.remove(local);
		instancia.remove(parada);

	}
}
