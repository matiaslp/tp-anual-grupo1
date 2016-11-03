package ar.edu.utn.dds.grupouno.entrega6;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class test2_entrega6 {
	//--------------------------------------------------------------------------
	// Parametros a modificar
		private static final double LATITUD = 100;
		private static final double LONGITUD = 200;
	//--------------------------------------------------------------------------
	
		private static final String PERSISTENCE_UNIT_NAME = "tp-anual";
		private EntityManagerFactory emFactory;
		Repositorio repositorio;
		private POI poi_bco, poi, poi_recuperado;
		private POI_DTO poiDTO_Bco;

		@Before
		public void setUp_test2() throws Exception {
			emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			repositorio = new Repositorio(emFactory.createEntityManager());

			// Creamos un nuevo POI
			// BANCO
			
		//	String nombres[] = new String[2];
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
//			nombres[0] = "etiqueta1";
//			nombres[1] = "etiqueta4";
//			poi_bco.setEtiquetas(nombres);
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
			//servicio
			ArrayList<Integer> diasLst = new ArrayList<Integer>();
			diasLst.add(5);
			diasLst.add(3);
			bco.agregarServicio("caja", diasLst, 9, 15);
			
			//Persistimos el POI
			repositorio.pois().agregarPOI(poi_bco);
}
		
		@Test
		public void aPersistir() {
			
			//Buscamos el poi por su nombre, en este caso sabemos que hay uno solo con dicho nombre
			List<POI> poilst;
			poilst = repositorio.pois().getPOIbyNombre("banco");
			poi = poilst.get(0);
			// Lo eliminamos (soft delete por fechaBaja)
			repositorio.pois().eliminarPOI(poi.getId());
			// Intentamos recuperarlo de la DB, la lista resultante deberia ser de size=0
			poilst = repositorio.pois().getPOIbyNombre("banco");
			Assert.assertTrue(poilst.size() == 0);
		}
		
		@After
		public void outtro() {
			
			repositorio.remove(poi);
			
		}
}
