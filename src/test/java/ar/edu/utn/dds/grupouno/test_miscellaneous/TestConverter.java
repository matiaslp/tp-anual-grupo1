package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.Banco_Converter;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.CGP_Converter;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;

public class TestConverter {

	@Test
	public void testCGP() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = CGP_Converter.getCGPs("http://trimatek.org/Consultas/centro?");
		Assert.assertTrue(listado.size() == 15);
	}

	@Test
	public void testBanco() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = Banco_Converter
				.getBancos("http://trimatek.org/Consultas/banco?banco=Santander&servicio=Pagos");
		Assert.assertTrue(listado.size() == 1);
	}
}
