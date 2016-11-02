package ar.edu.utn.dds.grupouno.abmc;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.Rubro;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestABMC_Baja {
	private static final String PERSISTENCE_UNIT_NAME = "tp-anual";
	private EntityManagerFactory emFactory;
	private static Repositorio repositorio;
	static POI_ABMC abmc = new POI_ABMC();
	static POI_ABMC poi_abmc;
	static POI_DTO poiDTOBanco, poiDTOCGP, poiDTOComercial, poiDTOColectivo;
	static POI banco, cgp, local, parada;
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
		
		// Se crean 4 POIs (uno por cada tipo)
		instancia.agregarPOI(banco);
//		instancia.agregarPOI(cgp);
//		instancia.agregarPOI(local);
//		instancia.agregarPOI(parada);
	}

	// Se realizan 4 test de borrado, uno por cada POI
	@Test
	public void bajaBanco() {
		List<POI> lstPoi = instancia.getPOIbyNombre("unBanco");
		POI poi = lstPoi.get(0);
		int id = (int) poi.getId();
		boolean respuesta = poi_abmc.delete(id);
		Assert.assertTrue(instancia.getPOIbyNombre("unBanco").get(0).dadoDeBaja());
	}
//
//	@Test
//	public void bajaCGP() {
//		//Este falla porque como es singleton se corre un test antes que lo modifica
//		//y queda modificado
//		boolean respuesta = poi_abmc.delete(instancia.getPOIbyNombre("unCGP").get(0).getId());
//		Assert.assertTrue(respuesta);
//	}
//
//	@Test
//	public void bajaLocalComercial() {
//		boolean respuesta = poi_abmc.delete(repositorio.pois().getPOIbyNombre("unLocalComercial").get(0).getId());
//		Assert.assertTrue(respuesta);
//	}
//
//	@Test
//	public void bajaParadaColectivo() {
//		boolean respuesta = poi_abmc.delete(repositorio.pois().getPOIbyNombre("unaParadaDeColectivo").get(0).getId());
//		Assert.assertTrue(respuesta);
//	}
//
//	// Comprobamos que no se puede borrar un POI al ingresar un ID inexistente
//	@Test
//	public void borrarInexistente() {
//		boolean respuesta = poi_abmc.delete(100);
//		Assert.assertFalse(respuesta);
//	}
//
//	// Comprobamos que efectivamente se elimina el POI
//	@Test
//	public void comprobarInexistencia() {
//		instancia.agregarPOI(poiDTOColectivo.converttoPOI());
//		poi_abmc.delete(17);
//		boolean respuesta = poi_abmc.delete(17);
//		Assert.assertFalse(respuesta);
//	}
//	
//	@Test
//	public void darDeBajaUnPOI() {
//		DateTime hoy = new DateTime();
//		DB_POI.getListado().get(1).darDeBaja(hoy);
//		Assert.assertTrue(DB_POI.getListado().get(1).getFechaBaja()!=null);
//		Assert.assertTrue(DB_POI.getListado().get(1).dadoDeBaja());
//	}
//	
//	@Test
//	public void darDeBajaPoiDadoDeBaja() {
//		DateTime hoy = new DateTime();
//		DB_POI.getListado().get(1).darDeBaja(hoy);
//		Assert.assertTrue(DB_POI.getListado().get(1).getFechaBaja()!=null);
//		Assert.assertFalse(DB_POI.getListado().get(1).darDeBaja(hoy));
//		
//	}
	
//	@AfterClass
//	public static void  outtro() {
//		
//		repositorio.remove(banco);
//		repositorio.remove(cgp);
//		repositorio.remove(local);
//		repositorio.remove(parada);
//		
//	}
}
