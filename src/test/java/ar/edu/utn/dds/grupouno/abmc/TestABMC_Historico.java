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

import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.CGP;
import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class TestABMC_Historico {
	POI_ABMC abmc;
	String ServicioAPI;
	DB_POI instance;

	Banco banco = new Banco("Santander", 0, 0);
	LocalComercial local = new LocalComercial("Localcito", 0, 0, null);
	ParadaColectivo parada = new ParadaColectivo("47", 0, 0);
	CGP cgp = new CGP("Mataderos", 0, 0);
	Historico historico;
	Usuario usuario;
	UsuariosFactory ufactory = new UsuariosFactory();

	@Before
	public void inicializar() {
		abmc = new POI_ABMC();
		instance = Repositorio.getInstance().pois();

		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = "http://trimatek.org/Consultas/";
		historico = new Historico();

		usuario = ufactory.crearUsuario("admin", "password", "ADMIN");

		usuario.setAuditoriaActivada(true);
		usuario.setCorreo("uncorreo@correoloco.com");
		usuario.setLog(true);
		usuario.setMailHabilitado(true);
		usuario.setNombre("Shaggy");
		usuario.setNotificacionesActivadas(true);

		Repositorio.getInstance().usuarios().persistirUsuario(usuario);

	}

	// La cantidad de registros aumenta como consecuencua de otros tests
	@Test
	public void testHistorico() throws JSONException, MalformedURLException, IOException, MessagingException {
		instance.agregarPOI(cgp);
		instance.agregarPOI(parada);
		instance.agregarPOI(local);
		instance.agregarPOI(banco);

		historico.buscar(ServicioAPI, "Mataderos", usuario.getId());
		// Assert.assertTrue(DB_HistorialBusquedas.getInstance().cantidadRegistros()
		// == 1);
		RegistroHistorico reg = Repositorio.getInstance().resultadosRegistrosHistoricos()
				.getHistoricobyUserId(usuario.getId()).get(0);
		Assert.assertTrue(reg.getCantResultados() == 17);
		Assert.assertTrue(reg.getTime().isBeforeNow());
		Assert.assertEquals(reg.getBusqueda(), "Mataderos");
	}

	@After
	public void outtro() {

		instance.remove(usuario);
		ArrayList<RegistroHistorico> list = Repositorio.getInstance().resultadosRegistrosHistoricos().getListado();
		for (RegistroHistorico reg : list)
			instance.remove(reg);
		instance.remove(cgp);
		instance.remove(parada);
		instance.remove(local);
		instance.remove(banco);
	}
}
