package ar.edu.utn.dds.grupouno.entrega6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.DatoHistoricoPOIMorphia;
import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.RegistroHistoricoMorphia;
import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.CGP;
import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;
import ar.edu.utn.dds.grupouno.repositorio.RepoMongo;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

public class Test3_entrega6 {
	POI_ABMC abmc;
	String ServicioAPI;
	Repositorio repositorio;
	Banco banco;
	LocalComercial local;
	ParadaColectivo parada;
	CGP cgp;
	Usuario usuario;
	UsuariosFactory ufactory = new UsuariosFactory();

	@Before
	public void setUp() throws Exception {
		abmc = new POI_ABMC();
		repositorio = Repositorio.getInstance();
		// DB_POI.getListado().clear();
		banco = new Banco("Santander", 0, 0);
		banco.setBarrio("Mataderos");
		banco.setPais("Argentina");
		banco.setCallePrincipal("Alberdi");
		banco.setCalleLateral("Escalada");
		ServicioAPI = LeerProperties.getInstance().prop.getProperty("Servicio_Externo");
		local = new LocalComercial("Localcito", 0, 0, null);
		parada = new ParadaColectivo("47", 0, 0);
		cgp = new CGP("Mataderos", 0, 0);
		repositorio.pois().agregarPOI(cgp);
		repositorio.pois().agregarPOI(parada);
		repositorio.pois().agregarPOI(local);
		repositorio.pois().agregarPOI(banco);

		usuario = ufactory.crearUsuario("admin", "password", "ADMIN");

		usuario.setAuditoriaActivada(true);
		usuario.setCorreo("uncorreo@correoloco.com");
		usuario.setLog(true);
		usuario.setMailHabilitado(true);
		usuario.setNombre("Shaggy");
		usuario.setNotificacionesActivadas(true);

		Repositorio.getInstance().usuarios().persistirUsuario(usuario);

	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	//Este dependiendo del estado de la cache es 3 o 18, por el orden en que se ejecutan los test  //
	// y porque el servicio anda mal, si tiene cache es 3, sino 18 porque devuelve todo.           //
	/////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void modificarPersistirRecuperarCoordenadas() {
		// Realizamos una busqueda, la misma se persiste
		ArrayList<POI> lista = null;
		try {
			lista = abmc.buscar(ServicioAPI, "Galicia Mataderos", usuario.getId());
		} catch (JSONException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(lista.size() == 3);

		// Comprobamos que se persistio
//		List<RegistroHistorico> lstHistorico = repositorio.resultadosRegistrosHistoricos()
//				.getHistoricobyUserId(usuario.getId());
		List<RegistroHistoricoMorphia> lstHistorico = RepoMongo.getInstance().getDatastore().createQuery(RegistroHistoricoMorphia.class)
		.filter("userID", usuario.getId()).asList();
		RegistroHistoricoMorphia reg = lstHistorico.get(0);

		// Comprobamos que corresponde al objecto de esa busqueda
		Assert.assertTrue(reg.getCantResultados() == lista.size());
		Assert.assertTrue(reg.getBusqueda().equals("Galicia Mataderos"));
		Assert.assertTrue(reg.getUserID() == usuario.getId());

		// Comprobamos referencias a los POIs
		for (DatoHistoricoPOIMorphia poi : reg.getPois()) {
			Assert.assertTrue(lista.contains(Repositorio.getInstance().pois().getPOIbyId(poi.getId())));

		}
	}

	@After
	public void outtro() {
		
		RepoMongo.getInstance().getDatastore().delete(RepoMongo.getInstance()
				.getDatastore().createQuery(RegistroHistoricoMorphia.class));
//		ArrayList<RegistroHistorico> list = Repositorio.getInstance().resultadosRegistrosHistoricos().getListado();
//		for (RegistroHistorico reg : list)
//			repositorio.remove(reg);
		repositorio.remove(cgp);
		repositorio.remove(banco);
		repositorio.remove(parada);
		repositorio.remove(local);
		repositorio.remove(usuario);

	}
}
