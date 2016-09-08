package test_abmc;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import abmc.POI_ABMC;
import abmc.Timer;
import db.DB_Server;
import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.POI;
import poi.ParadaColectivo;

public class TestABMC_Timer {
	POI_ABMC abmc;
	String ServicioAPI;
	
	Banco banco = new Banco("Santander", 0, 0);
	LocalComercial local = new LocalComercial("Localcito", 0, 0,null);
	ParadaColectivo parada = new ParadaColectivo("47", 0, 0);
	CGP cgp = new CGP("Mataderos", 0, 0);
	
	@Before
	public void inicializar(){
		abmc = new POI_ABMC();

		new DB_Server();
		
		
		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = "http://trimatek.org/Consultas/";
	}
	
	@Test
	public void testTimer() throws JSONException, MalformedURLException, IOException{
		ArrayList<POI> lista=null;
		DB_Server.agregarPOI(cgp);
		DB_Server.agregarPOI(parada);
		DB_Server.agregarPOI(local);
		DB_Server.agregarPOI(banco);
		
		// new timer
		Timer timer = new Timer();
		
		lista = timer.buscar(ServicioAPI, "Mataderos a b r t",1);
		Assert.assertTrue(timer.getSeconds() > 1);
	}
}
