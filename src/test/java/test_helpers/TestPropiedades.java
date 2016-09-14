package test_helpers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import helpers.LeerProperties;

public class TestPropiedades {

	LeerProperties instance;

	@Before
	public void init() {

		instance = new LeerProperties();

	}

	@Test
	public void testLeer() {
		// System.out.printf("%d\n",Integer.valueOf(LeerProperties.getInstance().prop.getProperty("distanciaLeveinstein")));
		Assert.assertEquals(LeerProperties.getInstance().prop.getProperty("distanciaLeveinstein"), "2");
	}

}
