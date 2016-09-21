package abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import poi.BusquedaDePOIsExternos;
import poi.POI;

public class TestBuscarPoiExterno {

	@Test
	public void testBuscarPOIsExternosBanco() throws JSONException, MalformedURLException, IOException {
		String var1 = "Galicia";
		String var2 = "";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1, var2);

		Assert.assertNotNull(listaResultado);

	}

	@Test
	public void testBuscarPOIsExternosBancoTamano() throws JSONException, MalformedURLException, IOException {
		String var1 = "Santander";
		String var2 = "Pagos";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1, var2);

		Assert.assertTrue(listaResultado.size() == 1);

	}

	@Test
	public void testBuscarPOIsExternosBancoNull() throws JSONException, MalformedURLException, IOException {
		String var1 = "";
		String var2 = "";
		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1, var2);
		Assert.assertTrue(listaResultado.isEmpty());
	}

	@Test
	public void testBuscarPOIsExternosCGP() throws JSONException, MalformedURLException, IOException {
		String var1 = "Boedo";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1);

		Assert.assertNotNull(listaResultado);
	}

	@Test
	public void testBuscarPOIsExternosCGPTamano() throws JSONException, MalformedURLException, IOException {
		String var1 = "Boedo";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1);

		Assert.assertTrue(listaResultado.size() == 16);
	}

	@Test
	public void testBuscarPOIsExternosCGPNull() throws JSONException, MalformedURLException, IOException {
		String var1 = "";

		List<POI> listaResultado = null;
		listaResultado = BusquedaDePOIsExternos.buscarPOIsExternos("http://trimatek.org/Consultas/", var1);

		Assert.assertTrue(listaResultado.isEmpty());
	}
}
