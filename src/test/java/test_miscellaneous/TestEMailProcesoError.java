package test_miscellaneous;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import autentification.Rol;
import autentification.Usuario;
import autentification.UsuariosFactory;
import db.AgregarAccionesTransaction;
import db.Resultado;
import db.ResultadoProceso;
import email.EnviarEmail;
import helpers.LeerProperties;
import procesos.AgregarAcciones;

public class TestEMailProcesoError {

	boolean envio;
	EnviarEmail email;
	LeerProperties instance;
	
	Usuario unUsuario;
	ArrayList<ResultadoProceso> listaResultados;
	
	ResultadoProceso primerResultadoDeProceso;
	//ResultadoProceso segundoResultadoDeProceso;
//	ResultadoProceso tercerResultadoDeProceso;
	
	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	AgregarAccionesTransaction transaction;
	

	
	@Before
	public void init() {

		instance = new LeerProperties();
		
		transaction = new AgregarAccionesTransaction(0);
		
		unUsuario.setUsername("Pablo");
		unUsuario.setCorreo("lag2392@gmail.com");
		unUsuario.setPassword("123");
		unUsuario.setRol(Rol.TERMINAL);
		
		listadoAccionesQueEstanEnAdmin.add("obtenerInfoPOI");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedaPorUsuario");
		
		primerResultadoDeProceso= new ResultadoProceso(new DateTime().minusHours(1),new DateTime()
				,new AgregarAcciones(0, envio, "unaAccion", unUsuario),
				11,"anda mal",Resultado.ERROR);
		
		listaResultados.add(primerResultadoDeProceso);
		
		
	}

	@Test
	public void mandarEMailProcesoError() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		envio = EnviarEmail.mandarCorreoProcesoError(unUsuario,listaResultados);
		Assert.assertTrue(envio);
	}
}
