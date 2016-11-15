package ar.edu.utn.dds.grupouno.abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.Timer;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;
import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.CGP;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestABMC_Timer {
	POI_ABMC abmc;
	String ServicioAPI;
	
	Banco banco = new Banco("Santander", 0, 0);
	LocalComercial local = new LocalComercial("Localcito", 0, 0, null);
	ParadaColectivo parada = new ParadaColectivo("47", 0, 0);
	CGP cgp = new CGP("Mataderos", 0, 0);
	Usuario usuario;
	UsuariosFactory ufactory = new UsuariosFactory();

	@Before
	public void inicializar() {
		abmc = new POI_ABMC();
		Repositorio.getInstance().pois();
	//	DB_POI.getListado().clear();

		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = "http://trimatek.org/Consultas/";
		
		usuario = ufactory.crearUsuario("admin", "password","ADMIN");
		
		usuario.setAuditoriaActivada(true);
		usuario.setCorreo("uncorreo@correoloco.com");
		usuario.setLog(true);
		usuario.setMailHabilitado(true);
		usuario.setNombre("Shaggy");
		usuario.setNotificacionesActivadas(true);
		
		Repositorio.getInstance().usuarios().persistirUsuario(usuario);
	}

	@Test
	public void testTimer() throws JSONException, MalformedURLException, IOException, MessagingException {
		Repositorio.getInstance().pois().agregarPOI(cgp);
		Repositorio.getInstance().pois().agregarPOI(parada);
		Repositorio.getInstance().pois().agregarPOI(local);
		Repositorio.getInstance().pois().agregarPOI(banco);

		// new timer
		// El test verifica contra un tiempo prefijado, si la maquina que lo corre es muy veloz puede dar error, pero no significa que este mal el test.
		Timer timer = new Timer();

		timer.buscar(ServicioAPI, "Mataderos a b r t", usuario.getId());
		Assert.assertTrue(timer.getSeconds() > 1);
	}
	
	@After
	public void outtro() {
		
		
		Repositorio.getInstance().pois().remove(usuario);
		ArrayList<RegistroHistorico> list = Repositorio.getInstance().resultadosRegistrosHistoricos().getListado();
		for (RegistroHistorico reg : list)
			Repositorio.getInstance().pois().remove(reg);
		Repositorio.getInstance().pois().remove(cgp);
		Repositorio.getInstance().pois().remove(parada);
		Repositorio.getInstance().pois().remove(local);
		Repositorio.getInstance().pois().remove(banco);
	}
}
