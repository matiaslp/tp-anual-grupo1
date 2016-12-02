package ar.edu.utn.dds.grupouno.abmc;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.Rubro;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class TestABMC_Baja {
	private static Repositorio repositorio;
	static POI_ABMC abmc = new POI_ABMC();
	static POI_ABMC poi_abmc;
	static POI_DTO poiDTOBanco, poiDTOCGP, poiDTOComercial, poiDTOColectivo;
	static POI banco, cgp, local, parada, parada2;
	static Rubro rubro;
	static DB_POI instancia;

	@BeforeClass
	public static void init() {

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

		poiDTOColectivo.setNombre("unaParadaDeColectivo2");
		parada2 = poiDTOColectivo.converttoPOI();

		// Se crean 4 POIs (uno por cada tipo)
		instancia.agregarPOI(banco);
		instancia.agregarPOI(cgp);
		instancia.agregarPOI(local);
		instancia.agregarPOI(parada);
	}

	// Se realizan 4 test de borrado, uno por cada POI
	@Test
	public void bajaBanco() {
		List<POI> lstPoi = instancia.getPOIbyNombre("unBanco");
		int id = (int) lstPoi.get(0).getId();
		poi_abmc.delete(id);
		Assert.assertTrue(instancia.getPOIbyNombre("unBanco").size() == 0);
	}

	@Test
	public void bajaCGP() {
		poi_abmc.delete(instancia.getPOIbyNombre("unCGP").get(0).getId());
		Assert.assertTrue(instancia.getPOIbyNombre("unCGP").size() == 0);
	}

	@Test
	public void bajaLocalComercial() {
		poi_abmc.delete(repositorio.pois().getPOIbyNombre("unLocalComercial").get(0).getId());
		Assert.assertTrue(instancia.getPOIbyNombre("unLocalComercial").size() == 0);
	}

	@Test
	public void bajaParadaColectivo() {
		poi_abmc.delete(repositorio.pois().getPOIbyNombre("unaParadaDeColectivo").get(0).getId());
		Assert.assertTrue(instancia.getPOIbyNombre("unaParadaDeColectivo").size() == 0);
	}

	// Comprobamos que no se puede borrar un POI al ingresar un ID inexistente
	@Test
	public void borrarInexistente() {
		boolean respuesta = poi_abmc.delete(100);
		Assert.assertFalse(respuesta);
	}

	// Comprobamos que efectivamente se elimina el POI
	@Test
	public void comprobarInexistencia() {
		instancia.agregarPOI(parada2);
		List<POI> lstPoi = instancia.getPOIbyNombre("unaParadaDeColectivo2");
		int id = (int) lstPoi.get(0).getId();
		poi_abmc.delete(id);
		boolean respuesta = poi_abmc.delete(id);
		Assert.assertFalse(respuesta);
	}

	@AfterClass
	public static void outtro() {

		repositorio.remove(banco);
		repositorio.remove(cgp);
		repositorio.remove(local);
		repositorio.remove(parada);
		repositorio.remove(parada2);

	}
}
