package ar.edu.utn.dds.grupouno.abmc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.abmc.poi.Rubro;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;


public class TestABMC_Alta {
	private Repositorio repositorio;
	POI_ABMC abmc = new POI_ABMC();
	POI_ABMC poi_abmc;
	POI_DTO poiDTOBanco, poiDTOCGP, poiDTOComercial, poiDTOColectivo;
	POI banco, cgp, local, parada;
	Rubro rubro;
	DB_POI instancia;

	@Before
	public void init() {
		repositorio = Repositorio.getInstance();
		poi_abmc = new POI_ABMC();
		instancia = repositorio.pois();

		poiDTOBanco = new POI_DTO();
		poiDTOBanco.setTipo(TiposPOI.BANCO);
		poiDTOBanco.setNombre("unBanco");
		poiDTOBanco.setLatitud(-34.5664823);
		poiDTOBanco.setLongitud(-34.5664823);
		banco = poiDTOBanco.converttoPOI();

		poiDTOCGP = new POI_DTO();
		poiDTOCGP.setTipo(TiposPOI.CGP);
		poiDTOCGP.setNombre("unCGP");
		poiDTOCGP.setLatitud(-34.5664823);
		poiDTOCGP.setLongitud(-34.5664823);
		poiDTOCGP.setRubro(rubro = new Rubro("unRubro"));
		cgp = poiDTOCGP.converttoPOI();

		poiDTOComercial = new POI_DTO();
		poiDTOComercial.setTipo(TiposPOI.LOCAL_COMERCIAL);
		poiDTOComercial.setNombre("unLocalComercial");
		poiDTOComercial.setLatitud(-34.5664823);
		poiDTOComercial.setLongitud(-34.5664823);
		local = poiDTOComercial.converttoPOI();

		poiDTOColectivo = new POI_DTO();
		poiDTOColectivo.setTipo(TiposPOI.PARADA_COLECTIVO);
		poiDTOColectivo.setNombre("unaParadaDeColectivo");
		poiDTOColectivo.setLatitud(-34.5664823);
		poiDTOColectivo.setLongitud(-34.5664823);
		parada = poiDTOColectivo.converttoPOI();
		((ParadaColectivo)parada).setLinea(114);
	}

	@Test
	public void altaBanco() {
		boolean respuesta = instancia.agregarPOI(banco);
		Assert.assertTrue(respuesta);
		Assert.assertTrue(instancia.getPOIbyNombre(poiDTOBanco.getNombre()).size() >= 1);

	}

	@Test
	public void altaCGP() {
		boolean respuesta = instancia.agregarPOI(cgp);
		Assert.assertTrue(respuesta);
		Assert.assertTrue(instancia.getPOIbyNombre(poiDTOCGP.getNombre()).size() >= 1);
	}

	@Test
	public void altaLocalComercial() {
		boolean respuesta = instancia.agregarPOI(local);
		Assert.assertTrue(respuesta);
		Assert.assertTrue(instancia.getPOIbyNombre(poiDTOComercial.getNombre()).size() >= 1);
	}

	@Test
	public void altaParadaColectivo() {
		boolean respuesta = instancia.agregarPOI(parada);
		Assert.assertTrue(respuesta);
		Assert.assertTrue(instancia.getPOIbyNombre(poiDTOColectivo.getNombre()).size() >= 1);
	}

	@After
	public void outtro() {

		repositorio.remove(banco);
		repositorio.remove(cgp);
		repositorio.remove(local);
		repositorio.remove(parada);

	}
}
