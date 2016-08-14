import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import ABMC.POI_DTO;
import DTOs.Banco_Converter;
import DTOs.CGP_Converter;

public class converterTest {
	
	@Test
	public void testCGP() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = CGP_Converter.getCGPs("http://trimatek.org/Consultas/centro?");
		Assert.assertTrue(listado.size() == 15);
	}
	
	@Test
	public void testBanco() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = Banco_Converter.getBancos("http://trimatek.org/Consultas/banco?banco=Santander&servicio=Pagos");
		Assert.assertTrue(listado.size() == 1);
	}
}
