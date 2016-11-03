package ar.edu.utn.dds.grupouno.RegistroHistorico;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class testRegistroHistoricoPersistencia {

			private static final String PERSISTENCE_UNIT_NAME = "tp-anual";
			private EntityManagerFactory emFactory;
			Repositorio repositorio;
			
			RegistroHistorico unRegistroHistorico;
			ArrayList<POI> listaDePOIs;
			LocalComercial local1;
			Banco banco1;
			DateTime fecha;
			RegistroHistorico unRH;
			
			RegistroHistorico registroHistoricoRecuperado =new RegistroHistorico();

			@Before
			public void setUp() throws Exception {
				emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				repositorio = new Repositorio(emFactory.createEntityManager());

				
				listaDePOIs = new ArrayList<POI>();
				local1 = new LocalComercial();
				banco1 = new Banco();
				fecha = new DateTime(2016,02,02,0,0);
				
				local1.setNombre("local1");
				local1.setId((long) 11);
				banco1.setNombre("banco1");
				banco1.setId((long) 22);
				
				
				listaDePOIs.add(local1);
				listaDePOIs.add(banco1);
				
				unRegistroHistorico=new RegistroHistorico(fecha, 1, "unaStringDeBusqueda",2,12,listaDePOIs);
							
				//Persistimos el POI para tener uno en la DB
				repositorio.resultadosRegistrosHistoricos().agregarRegistroHistorico(unRegistroHistorico);
	}
			
			@Test
			public void modificarPersistirRecuperarCoordenadas() {
				//Buscamos el RegistroHistorico por su nombre, en este caso sabemos que hay uno solo con dicho nombre
				List<RegistroHistorico> registroHistoricolist;
				registroHistoricolist= repositorio.resultadosRegistrosHistoricos().getRegistroHistoricobyId((long) 1);
				unRH=registroHistoricolist.get(0);
	
				// Modificamos sus coordenadas geograficas
				
				listaDePOIs = new ArrayList<POI>();
				local1 = new LocalComercial();
				banco1 = new Banco();
				fecha = new DateTime(2016,12,18,0,0);
				
				local1.setNombre("local2");
				local1.setId((long) 33);
				banco1.setNombre("banco2");
				banco1.setId((long) 44);
				
				
				listaDePOIs.add(local1);
				listaDePOIs.add(banco1);
				
				unRH.setListaDePOIs(listaDePOIs);
				//unRH.setTime(new DateTime(2016,12,19,0,0));
				unRH.setUserID(11);
				unRH.setTiempoDeConsulta(1111);
				// Persistimos
				repositorio.resultadosRegistrosHistoricos().actualizarRegistroHistorico(unRH);
				// Lo recuperamos de la DB
				
			
				registroHistoricoRecuperado= (RegistroHistorico) repositorio.resultadosRegistrosHistoricos().getRegistroHistoricobyId(0);
				Assert.assertTrue(registroHistoricoRecuperado.getListaDePOIs() == listaDePOIs);
				Assert.assertTrue(registroHistoricoRecuperado.getTime().equals(new DateTime(2016,12,19,0,0)));
			}
			
			@After
			public void outtro() {
				
				repositorio.remove(registroHistoricoRecuperado);
				
			}
}
