package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.Banco_Converter;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.CGP_Converter;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;

public class TestConverter {
	
	private String ServicioAPI;
	
	@Before
	public void inicializar(){
		
		ServicioAPI = LeerProperties.getInstance().prop.getProperty("Servicio_Externo");
	}

	@Test
	public void testCGP() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = CGP_Converter.getCGPs(ServicioAPI+"centro?");
		Assert.assertTrue(listado.size() == 15);
	}

	@Test
	public void testBanco() throws JSONException, MalformedURLException, IOException {
		List<POI_DTO> listado = Banco_Converter
				.getBancos(ServicioAPI+"banco?banco=Santander&servicio=Pagos");
		Assert.assertTrue(listado.size() == 1);
	}
}
