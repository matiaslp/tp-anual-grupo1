package ar.edu.utn.dds.grupouno.abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.BusquedaDePOIsExternos;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;

public class TestBuscarPoiExterno {
	
	private String ServicioAPI;

	@Before
	public void inicializar(){
		
		ServicioAPI = LeerProperties.getInstance().prop.getProperty("Servicio_Externo");
	}

	@Test
	public void testBuscarPOIsExternosBanco() throws JSONException, MalformedURLException, IOException {
		String var1 = "Galicia";
		String var2 = "";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1, var2);

		Assert.assertNotNull(listaResultado);

	}

	@Test
	public void testBuscarPOIsExternosBancoTamano() throws JSONException, MalformedURLException, IOException {
		String var1 = "Santander";
		String var2 = "Pagos";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1, var2);

		Assert.assertTrue(listaResultado.size() == 1);

	}

	@Test
	public void testBuscarPOIsExternosBancoNull() throws JSONException, MalformedURLException, IOException {
		String var1 = "";
		String var2 = "";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1, var2);
		Assert.assertTrue(listaResultado.isEmpty());
	}

	@Test
	public void testBuscarPOIsExternosCGP() throws JSONException, MalformedURLException, IOException {
		String var1 = "Boedo";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1);

		Assert.assertNotNull(listaResultado);
	}

	@Test
	public void testBuscarPOIsExternosCGPTamano() throws JSONException, MalformedURLException, IOException {
		String var1 = "Boedo";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1);

		Assert.assertTrue(listaResultado.size() == 16);
	}

	@Test
	public void testBuscarPOIsExternosCGPNull() throws JSONException, MalformedURLException, IOException {
		String var1 = "";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos(ServicioAPI, var1);

		Assert.assertTrue(listaResultado.isEmpty());
	}
}
