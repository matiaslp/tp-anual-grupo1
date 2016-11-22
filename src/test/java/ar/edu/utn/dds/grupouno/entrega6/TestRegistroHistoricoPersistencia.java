package ar.edu.utn.dds.grupouno.entrega6;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestRegistroHistoricoPersistencia {

	Repositorio repositorio;

	RegistroHistorico unRegistroHistorico;
	ArrayList<POI> listaDePOIs;
	POI banco1, local1, banco2, local2;
	DateTime fecha;
	RegistroHistorico unRH;
	POI_DTO local_dto, banco_dto;

	RegistroHistorico registroHistoricoRecuperado;

	@Before
	public void setUp() throws Exception {

		repositorio = Repositorio.getInstance();
		listaDePOIs = new ArrayList<POI>();
		local_dto = new POI_DTO();
		banco_dto = new POI_DTO();
		fecha = new DateTime(2016, 02, 02, 0, 0);

		local_dto.setNombre("local1");
		local_dto.setId((long) 11);
		local_dto.setTipo(TiposPOI.LOCAL_COMERCIAL);
		banco_dto.setNombre("banco1");
		banco_dto.setId((long) 22);
		banco_dto.setTipo(TiposPOI.BANCO);
		local1 = local_dto.converttoPOI();
		banco1 = banco_dto.converttoPOI();

		listaDePOIs.add(local1);
		listaDePOIs.add(banco1);

		repositorio.pois().agregarPOI(local1);
		repositorio.pois().agregarPOI(banco1);
		unRegistroHistorico = new RegistroHistorico(fecha, 1, "unaStringDeBusqueda", 2, 12, listaDePOIs);

		// Persistimos el POI para tener uno en la DB
		Repositorio.getInstance().resultadosRegistrosHistoricos().agregarHistorialBusqueda(unRegistroHistorico);
	}

	@Test
	public void modificarPersistirRecuperarCoordenadas() {
		// Buscamos el RegistroHistorico por su userid, en este caso sabemos que
		// hay uno solo con dicho id
		List<RegistroHistorico> registroHistoricolist;
		registroHistoricolist = repositorio.resultadosRegistrosHistoricos().getHistoricobyUserId(1L);
		unRH = registroHistoricolist.get(0);

		// Modificamos su lista de pois, fecha, userID y tiempo de consulta

		listaDePOIs.clear();
		local_dto.setNombre("local2");
		local_dto.setId((long) 11);
		local_dto.setTipo(TiposPOI.LOCAL_COMERCIAL);
		banco_dto.setNombre("banco2");
		banco_dto.setId((long) 22);
		banco_dto.setTipo(TiposPOI.BANCO);
		local2 = local_dto.converttoPOI();
		banco2 = banco_dto.converttoPOI();

		listaDePOIs.add(local2);
		listaDePOIs.add(banco2);
		repositorio.pois().agregarPOI(local2);
		repositorio.pois().agregarPOI(banco2);

		unRH.setListaDePOIs(listaDePOIs);
		unRH.setTime(new DateTime(2016, 12, 19, 0, 0));
		unRH.setUserID(11);
		unRH.setTiempoDeConsulta(1111);
		// Persistimos
		repositorio.resultadosRegistrosHistoricos().actualizarRegistroHistorico(unRH);
		// Lo recuperamos de la DB

		registroHistoricoRecuperado = (RegistroHistorico) repositorio.resultadosRegistrosHistoricos().getListado().get(0);
		
		//comprobamos que las modificaciones se hayan realizado
		Assert.assertTrue(registroHistoricoRecuperado.getListaDePOIs().get(0).equals(local2));
		Assert.assertTrue(registroHistoricoRecuperado.getListaDePOIs().get(1).equals(banco2));
		Assert.assertTrue(registroHistoricoRecuperado.getUserID() == 11);
		Assert.assertTrue(registroHistoricoRecuperado.getBusqueda().equals("unaStringDeBusqueda"));
		Assert.assertTrue(registroHistoricoRecuperado.getTime().equals(new DateTime(2016, 12, 19, 0, 0)));
	}

	@After
	 public void outtro() {
	 repositorio.remove(registroHistoricoRecuperado);
	 repositorio.remove(banco1);
	 repositorio.remove(banco2);
	 repositorio.remove(local1);
	 repositorio.remove(local2);
	
	 }
}
