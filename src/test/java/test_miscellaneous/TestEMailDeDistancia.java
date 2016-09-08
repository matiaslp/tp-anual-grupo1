package test_miscellaneous;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import email.EnviarEmail;
import helpers.LeerProperties;

public class TestEMailDeDistancia {

	boolean envio;
	EnviarEmail email;
	LeerProperties instance;

	@Before
	public void init() {

		instance = new LeerProperties();

	}

	@Test
	public void mandarCorreoDemoraXSegundosBusqueda() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		envio = EnviarEmail.mandarCorreoXSegundos("busquedaRubro", 3333.2);
		Assert.assertTrue(envio);
	}
}
