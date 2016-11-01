package ar.edu.utn.dds.grupouno;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.Rubro;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class HibernateTest {
	private static final String PERSISTENCE_UNIT_NAME = "tp-anual";
	private EntityManagerFactory emFactory;
	private Repositorio repositorio;
	private POI poi_bco, poi_local, poi_CGP, poi_parada;
	private POI_DTO poiDTO_Bco, poiDTO_Local, poiDTO_CGP, poiDTO_Parada;

	@Before
	public void setUp() throws Exception {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager());
//		poi = new POI();
//		poi.setNombre("test");
		
		poiDTO_Bco = new POI_DTO();
		poiDTO_Bco.setTipo(TiposPOI.BANCO);
		poi_bco = poiDTO_Bco.converttoPOI();
		poi_bco.setNombre("banco");
		String nombres[] = new String[1];
		nombres[0] = "etiqueta";
		poi_bco.setEtiquetas(nombres);
		
		poiDTO_Local = new POI_DTO();
		poiDTO_Local.setTipo(TiposPOI.LOCAL_COMERCIAL);
		poi_local = poiDTO_Local.converttoPOI();
		poi_local.setNombre("local");
		
		poiDTO_CGP = new POI_DTO();
		poiDTO_CGP.setTipo(TiposPOI.CGP);
		poi_CGP = poiDTO_CGP.converttoPOI();
		poi_CGP.setNombre("cgp");
		
		poiDTO_Parada = new POI_DTO();
		poiDTO_Parada.setTipo(TiposPOI.PARADA_COLECTIVO);
		poi_parada = poiDTO_Parada.converttoPOI();
		poi_parada.setNombre("parada");
	}
	
	@Test
	public void aPersistir() {
		
	//	POI poi = poiDTO.converttoPOI();
		repositorio.pois().agregarPOI(poi_bco);
		repositorio.pois().agregarPOI(poi_local);
		repositorio.pois().agregarPOI(poi_CGP);
		repositorio.pois().agregarPOI(poi_parada);
		List<POI> poilst;
//		poilst = repositorio.pois().getPOIbyNombre("Banco");
//		POI poi = poilst.get(0);
//		System.out.println(poi.getNombre());
//		repositorio.pois().eliminarPOI(1L);
//		poilst = repositorio.pois().getPOIbyNombre("Banco");
//		poi = poilst.get(0);
//		System.out.println(poi.getNombre());
		
		
		poilst = repositorio.pois().getPOIbyNombre("local");
		POI poi_loc = poilst.get(0);
		System.out.println(poi_loc.getNombre());
		poi_loc.setNombre("loal");
		repositorio.pois().actualizarPOI(poi_loc);
		poilst = repositorio.pois().getPOIbyNombre("loal");
		POI poi_loc2 = poilst.get(0);
		System.out.println(poi_loc2.getNombre());
		
		
		
	}
//	@Test
//	public void buscarPOI() {
//		List<POI> poilst = repositorio.pois().getPOIbyNombre("banco");
//		POI poi = poilst.get(0);
//		System.out.println(poi.getNombre());
//	}
//	
	
}
