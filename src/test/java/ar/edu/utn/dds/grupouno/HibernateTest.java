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
	private POI poi;

	@Before
	public void setUp() throws Exception {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager());
		poi = new POI();
	//	poiDTO = new POI_DTO();
	//	poiDTO.setTipo(TiposPOI.BANCO);
		poi.setNombre("test");
	}
	
	@Test
	public void aPersistir() {
		
	//	POI poi = poiDTO.converttoPOI();
		repositorio.pois().persistir(poi);
	}
	@Test
	public void buscarPOI() {
		List<POI> poilst = repositorio.pois().getPOIbyNombre("text");
		POI poi = poilst.get(0);
		System.out.println(poi.getNombre());
	}
	
	
}
