package ar.edu.utn.dds.grupouno.entrega6;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class Test1_entrega6 {
	// --------------------------------------------------------------------------
	// Parametros a modificar
	private static final double LATITUD = 101;
	private static final double LONGITUD = 200;
	// --------------------------------------------------------------------------

	Repositorio repositorio;
	private POI poi_bco, poi, poi_recuperado;
	private POI_DTO poiDTO_Bco;

	@Before
	public void setUp() throws Exception {
		repositorio = Repositorio.getInstance();

		// BANCO

		String nombres[] = new String[2];
		poiDTO_Bco = new POI_DTO();
		poiDTO_Bco.setTipo(TiposPOI.BANCO);
		poi_bco = poiDTO_Bco.converttoPOI();
		poi_bco.setNombre("banco");
		poi_bco.setBarrio("barrio");
		poi_bco.setCalleLateral("calleLateral");
		poi_bco.setCallePrincipal("callePrincipal");
		poi_bco.setCecania(1000);
		poi_bco.setCodigoPostal(1500);
		poi_bco.setComuna(12);
		poi_bco.setDepartamento("b");
		poi_bco.setEsLocal(true);
		nombres[0] = "etiqueta1";
		nombres[1] = "etiqueta4";
		poi_bco.setEtiquetas(nombres);
		poi_bco.setLocalidad("nuñez");
		poi_bco.setNumeracion(1405);
		poi_bco.setPais("Argentina");
		poi_bco.setPiso(3);
		poi_bco.setProvincia("CABA");
		poi_bco.setUnidad("3");
		poi_bco.setLatitud(-34.5664823);
		poi_bco.setLongitud(-34.5664823);
		Banco bco = (Banco) poi_bco;
		bco.setGerente("gerente");
		bco.setSucursal("sucursal");
		// servicio
		ArrayList<Integer> diasLst = new ArrayList<Integer>();
		diasLst.add(5);
		diasLst.add(3);
		bco.agregarServicio("caja", diasLst, 9, 15);

		// Persistimos el POI para tener uno en la DB
		repositorio.pois().agregarPOI(poi_bco);
	}

	@Test
	public void modificarPersistirRecuperarCoordenadas() {
		// Buscamos el poi por su nombre, en este caso sabemos que hay uno solo
		// con dicho nombre
		List<POI> poilst;
		poilst = repositorio.pois().getPOIbyNombre("banco");
		poi = poilst.get(0);
		// Modificamos sus coordenadas geograficas
		poi.setLatitud(LATITUD);
		poi.setLongitud(LONGITUD);
		// Persistimos
		repositorio.pois().actualizarPOI(poi);
		// Lo recuperamos de la DB
		poi_recuperado = repositorio.pois().getPOIbyNombre("banco").get(0);
		Assert.assertTrue(poi_recuperado.getLatitud() == LATITUD);
		Assert.assertTrue(poi_recuperado.getLongitud() == LONGITUD);
	}

	@After
	public void outtro() {

		repositorio.remove(poi_recuperado);

	}
}
