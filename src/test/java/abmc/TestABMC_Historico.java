package abmc;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.Historico;
import abmc.POI_ABMC;
import db.DB_HistorialBusquedas;
import db.DB_POI;
import db.RegistroHistorico;
import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.ParadaColectivo;

public class TestABMC_Historico {
	POI_ABMC abmc;
	String ServicioAPI;
	DB_POI instance;
	
	Banco banco = new Banco("Santander", 0, 0);
	LocalComercial local = new LocalComercial("Localcito", 0, 0, null);
	ParadaColectivo parada = new ParadaColectivo("47", 0, 0);
	CGP cgp = new CGP("Mataderos", 0, 0);
	Historico historico;

	@Before
	public void inicializar() {
		abmc = new POI_ABMC();
		instance = DB_POI.getInstance();
		
		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = "http://trimatek.org/Consultas/";
		historico = new Historico();

	}

	// La cantidad de registros aumenta como consecuencua de otros tests
	@Test
	public void testHistorico() throws JSONException, MalformedURLException, IOException, MessagingException {
		instance.agregarPOI(cgp);
		instance.agregarPOI(parada);
		instance.agregarPOI(local);
		instance.agregarPOI(banco);

		historico.buscar(ServicioAPI, "Mataderos", 1);
		Assert.assertTrue(DB_HistorialBusquedas.getInstance().cantidadRegistros() == 1);
		RegistroHistorico reg = DB_HistorialBusquedas.getInstance().registroHistoricoPorId(1);
		Assert.assertTrue(reg.getUserID() == 1);
		Assert.assertTrue(reg.getCantResultados() == 17);
		Assert.assertTrue(reg.getTime().isBeforeNow());
		Assert.assertEquals(reg.getBusqueda(), "Mataderos");
	}
}
