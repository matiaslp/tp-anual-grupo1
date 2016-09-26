package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.DB_POI;
import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.POI;
import poi.ParadaColectivo;

public class TestABMC_Timer {
	POI_ABMC abmc;
	String ServicioAPI;
	DB_POI instancia;
	
	Banco banco = new Banco("Santander", 0, 0);
	LocalComercial local = new LocalComercial("Localcito", 0, 0, null);
	ParadaColectivo parada = new ParadaColectivo("47", 0, 0);
	CGP cgp = new CGP("Mataderos", 0, 0);

	@Before
	public void inicializar() {
		abmc = new POI_ABMC();
		instancia = DB_POI.getInstance();

		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = "http://trimatek.org/Consultas/";
	}

	@Test
	public void testTimer() throws JSONException, MalformedURLException, IOException, MessagingException {
		instancia.agregarPOI(cgp);
		instancia.agregarPOI(parada);
		instancia.agregarPOI(local);
		instancia.agregarPOI(banco);

		// new timer
		Timer timer = new Timer();

		List<POI> lista = timer.buscar(ServicioAPI, "Mataderos a b r t", 1);
		Assert.assertTrue(timer.getSeconds() > 1);
	}
}
