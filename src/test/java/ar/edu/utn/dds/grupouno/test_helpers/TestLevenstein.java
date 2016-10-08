package ar.edu.utn.dds.grupouno.test_helpers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.helpers.LeerProperties;
import ar.edu.utn.dds.grupouno.helpers.LevDist;

public class TestLevenstein {
	String a, b, c, d, e;
	boolean resultado;
	LeerProperties instance;

	@Before
	public void inicializar() {
		a = "Esto es una prueba";
		b = "Exto es una prueba";
		c = "Exto es una prueva";
		d = "Exto es una praeva";
		instance = new LeerProperties();
	}

	@Test
	public void testSinDiferencias() {
		resultado = LevDist.calcularDistancia(a, a);
		Assert.assertTrue(resultado);

	}

	@Test
	public void test1Diferencia() {
		resultado = LevDist.calcularDistancia(a, b);
		Assert.assertTrue(resultado);

	}

	@Test
	public void test2Diferencias() {
		resultado = LevDist.calcularDistancia(a, c);
		Assert.assertFalse(resultado);
	}

	@Test
	public void test3Diferencias() {
		resultado = LevDist.calcularDistancia(a, d);
		Assert.assertFalse(resultado);
	}

	@Test
	public void testNull() {
		resultado = LevDist.calcularDistancia(a, e);
		Assert.assertFalse(resultado);

	}
}
