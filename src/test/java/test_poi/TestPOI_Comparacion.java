package test_poi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.ParadaColectivo;
import poi.Rubro;
import poi.TiposPOI;

public class TestPOI_Comparacion {

	Banco unBanco = new Banco("Galicia", 50, 20);
	Banco otroBanco = new Banco("Santander", 50, 20);
	Banco bancoNull = new Banco("Galicia", 20, 40);

	CGP unCGP = new CGP("unCGP", 100, 20);
	CGP otroCGP = new CGP("otroCGP", 100, 20);

	Rubro unRubro = new Rubro("Ferreteria");
	LocalComercial unLocal = new LocalComercial("unLocal", 50, 100, unRubro);
	Rubro otroRubro = new Rubro("Farmacia");
	LocalComercial otroLocal = new LocalComercial("otroLocal", 50, 100, otroRubro);

	ParadaColectivo unaParada = new ParadaColectivo("La del 7", 10, 10);
	ParadaColectivo otraParada = new ParadaColectivo("La del 114", 10, 10);

	@Before
	public void init() {

		unBanco.setTipo(TiposPOI.BANCO);
		unBanco.setSucursal("Martinez");
		unBanco.setGerente("Carlo");
		unBanco.setPiso(1);
		unBanco.setCodigoPostal(1640);
		unBanco.setComuna(12500);
		unBanco.setCallePrincipal("221B baker street");
		unBanco.setCalleLateral("calleIzq");
		unBanco.setDepartamento("unDepartamento");
		unBanco.setUnidad("unaUnidad");
		unBanco.setLocalidad("Martinez");
		unBanco.setBarrio("Boedo");
		unBanco.setProvincia("Buenos Aires");
		unBanco.setPais("Argentina");

		otroBanco.setTipo(TiposPOI.BANCO);
		otroBanco.setSucursal("Martinez");
		otroBanco.setGerente("Luis");
		otroBanco.setPiso(1);
		otroBanco.setCodigoPostal(1640);
		otroBanco.setComuna(12500);
		otroBanco.setCallePrincipal("221B baker street");
		otroBanco.setCalleLateral("calleIzq");
		otroBanco.setDepartamento("unDepartamento");
		otroBanco.setUnidad("unaUnidad");
		otroBanco.setLocalidad("Martinez");
		otroBanco.setBarrio("Boedo");
		otroBanco.setProvincia("Buenos Aires");
		otroBanco.setPais("Argentina");

		bancoNull.setTipo(TiposPOI.BANCO);
		bancoNull.setSucursal("Martinez");
		bancoNull.setGerente("Carlo");
		bancoNull.setPiso(1);
		bancoNull.setCallePrincipal("221B baker street");
		bancoNull.setUnidad("unaUnidad");
		bancoNull.setLocalidad("Martinez");
		bancoNull.setBarrio("Boedo");
		bancoNull.setProvincia("Buenos Aires");
		bancoNull.setPais("Argentina");

		unCGP.setTipo(TiposPOI.CGP);
		unCGP.setDirector("Cacho");
		unCGP.setTelefono("42");
		unCGP.setPiso(1);
		unCGP.setCodigoPostal(1640);
		unCGP.setComuna(12500);
		unCGP.setCallePrincipal("221B baker street");
		unCGP.setCalleLateral("calleIzq");
		unCGP.setDepartamento("unDepartamento");
		unCGP.setUnidad("unaUnidad");
		unCGP.setLocalidad("Martinez");
		unCGP.setBarrio("Boedo");
		unCGP.setProvincia("Buenos Aires");
		unCGP.setPais("Argentina");

		otroCGP.setTipo(TiposPOI.CGP);
		otroCGP.setDirector("Marta");
		otroCGP.setTelefono("42221");
		otroCGP.setPiso(1);
		otroCGP.setCodigoPostal(1640);
		otroCGP.setComuna(12500);
		otroCGP.setCallePrincipal("221B baker street");
		otroCGP.setCalleLateral("calleIzq");
		otroCGP.setDepartamento("unDepartamento");
		otroCGP.setUnidad("unaUnidad");
		otroCGP.setLocalidad("Martinez");
		otroCGP.setBarrio("Boedo");
		otroCGP.setProvincia("Buenos Aires");
		otroCGP.setPais("Argentina");

		unLocal.setTipo(TiposPOI.LOCAL_COMERCIAL);
		unLocal.setPiso(1);
		unLocal.setCodigoPostal(1640);
		unLocal.setComuna(12500);
		unLocal.setCallePrincipal("221B baker street");
		unLocal.setCalleLateral("calleIzq");
		unLocal.setDepartamento("unDepartamento");
		unLocal.setUnidad("unaUnidad");
		unLocal.setLocalidad("Martinez");
		unLocal.setBarrio("Boedo");
		unLocal.setProvincia("Buenos Aires");
		unLocal.setPais("Argentina");

		otroLocal.setTipo(TiposPOI.LOCAL_COMERCIAL);
		otroLocal.setPiso(1);
		otroLocal.setCodigoPostal(1640);
		otroLocal.setComuna(12500);
		otroLocal.setCallePrincipal("221B baker street");
		otroLocal.setCalleLateral("calleIzq");
		otroLocal.setDepartamento("unDepartamento");
		otroLocal.setUnidad("unaUnidad");
		otroLocal.setLocalidad("Martinez");
		otroLocal.setBarrio("Boedo");
		otroLocal.setProvincia("Buenos Aires");
		otroLocal.setPais("Argentina");

		unaParada.setTipo(TiposPOI.PARADA_COLECTIVO);
		unaParada.setDistancia(60);
		unaParada.setPiso(1);
		unaParada.setCodigoPostal(1640);
		unaParada.setComuna(12500);
		unaParada.setCallePrincipal("221B baker street");
		unaParada.setCalleLateral("calleIzq");
		unaParada.setDepartamento("unDepartamento");
		unaParada.setUnidad("unaUnidad");
		unaParada.setLocalidad("Martinez");
		unaParada.setBarrio("Boedo");
		unaParada.setProvincia("Buenos Aires");
		unaParada.setPais("Argentina");

		otraParada.setTipo(TiposPOI.PARADA_COLECTIVO);
		otraParada.setDistancia(30);
		otraParada.setPiso(1);
		otraParada.setCodigoPostal(1640);
		otraParada.setComuna(12500);
		otraParada.setCallePrincipal("221B baker street");
		otraParada.setCalleLateral("calleIzq");
		otraParada.setDepartamento("unDepartamento");
		otraParada.setUnidad("unaUnidad");
		otraParada.setLocalidad("Martinez");
		otraParada.setBarrio("Boedo");
		otraParada.setProvincia("Buenos Aires");
		otraParada.setPais("Argentina");

	}

	/*
	 * Servicios Etiquetas
	 */

	@Test
	public void testBancosIguales() {
		Assert.assertTrue(unBanco.compararPOI(unBanco));
	}

	@Test
	public void testBancosDistinos() {
		Assert.assertFalse(unBanco.compararPOI(otroBanco));
	}

	@Test
	public void testCGPIguales() {
		Assert.assertTrue(unCGP.compararPOI(unCGP));
	}

	@Test
	public void testCGPDistintos() {
		Assert.assertFalse(unCGP.compararPOI(otroCGP));
	}

	@Test
	public void testLocalComercialIguales() {
		Assert.assertTrue(unLocal.compararPOI(unLocal));
	}

	@Test
	public void testLocalComeralDistinto() {
		Assert.assertFalse(unLocal.compararPOI(otroLocal));
	}

	@Test
	public void testParadaColectivoIgual() {
		Assert.assertTrue(unaParada.compararPOI(unaParada));
	}

	@Test
	public void testParadaColectivoDistinta() {
		Assert.assertFalse(unaParada.compararPOI(otraParada));
	}

	@Test
	public void testTiposPOI() {
		boolean respuesta = unBanco.compararPOI(unCGP);
		Assert.assertFalse(respuesta);
	}

	@Test
	public void testBancoConNull() {
		Assert.assertFalse(unBanco.compararPOI(bancoNull));
	}

}
