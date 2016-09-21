package abmc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.POI_ABMC;
import abmc.POI_DTO;
import db.DB_POI;
import poi.Rubro;
import poi.TiposPOI;

public class TestABMC_Alta {
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
	}

	@Test
	public void altaBanco() {
		boolean respuesta = DB_POI.agregarPOI(poiDTOBanco.converttoPOI());
		Assert.assertTrue(respuesta);
		Assert.assertTrue(poiDTOBanco.getNombre()
				.equals(DB_POI.getListado().get(DB_POI.getListado().size() - 1).getNombre()));

	}

	@Test
	public void altaCGP() {
		boolean respuesta = DB_POI.agregarPOI(poiDTOCGP.converttoPOI());
		Assert.assertTrue(respuesta);
		Assert.assertTrue(poiDTOCGP.getNombre()
				.equals(DB_POI.getListado().get(DB_POI.getListado().size() - 1).getNombre()));
	}

	@Test
	public void altaLocalComercial() {
		boolean respuesta = DB_POI.agregarPOI(poiDTOComercial.converttoPOI());
		Assert.assertTrue(respuesta);
		Assert.assertTrue(poiDTOComercial.getNombre()
				.equals(DB_POI.getListado().get(DB_POI.getListado().size() - 1).getNombre()));
	}

	@Test
	public void altaParadaColectivo() {
		boolean respuesta = DB_POI.agregarPOI(poiDTOColectivo.converttoPOI());
		Assert.assertTrue(respuesta);
		Assert.assertTrue(poiDTOColectivo.getNombre()
				.equals(DB_POI.getListado().get(DB_POI.getListado().size() - 1).getNombre()));
	}
}
