package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.email.EnviarEmail;
import ar.edu.utn.dds.grupouno.helpers.LeerProperties;
import ar.edu.utn.dds.grupouno.procesos.AgregarAcciones;

public class TestEMailProcesoError {

	boolean envio;
	EnviarEmail email;
	LeerProperties instance;
	
	Usuario unUsuario;
	ArrayList<ResultadoProceso> listaResultados;
	
	ResultadoProceso primerResultadoDeProceso;
	ResultadoProceso segundoResultadoDeProceso;
 	ResultadoProceso tercerResultadoDeProceso;
	
	private ArrayList<String> listadoAccionesQueEstanEnAdmin;
	AgregarAccionesTransaction transaction;
	

	
	@Before
	public void init() {

		instance = new LeerProperties();
		
		transaction = new AgregarAccionesTransaction(0);
		
		unUsuario= new Usuario();
		unUsuario.setUsername("Pablo");
		unUsuario.setCorreo("lag21392@gmail.com");
		unUsuario.setPassword("123");
		unUsuario.setRol(Rol.TERMINAL);
		
		listadoAccionesQueEstanEnAdmin = new ArrayList<String>();
		listadoAccionesQueEstanEnAdmin.add("obtenerInfoPOI");
		listadoAccionesQueEstanEnAdmin.add("reporteBusquedaPorUsuario");
		
		primerResultadoDeProceso= new ResultadoProceso(new DateTime().minusHours(1),new DateTime()
				,new AgregarAcciones(0, envio, "unaAccion", unUsuario),
				11,"Anda mal primerResultadoDeProceso",Resultado.ERROR);
		
		segundoResultadoDeProceso= new ResultadoProceso(new DateTime().minusHours(1),new DateTime()
				,new AgregarAcciones(0, envio, "segundaAccion", unUsuario),
				11,"Anda mal segundoResultadoDeProceso",Resultado.ERROR);
		
		tercerResultadoDeProceso= new ResultadoProceso(new DateTime().minusHours(1),new DateTime()
				,new AgregarAcciones(0, envio, "terceraAccion", unUsuario),
				11,"Anda mal tercerResultadoDeProceso",Resultado.ERROR);
		
		listaResultados = new ArrayList<ResultadoProceso>();
		listaResultados.add(primerResultadoDeProceso);
		listaResultados.add(segundoResultadoDeProceso);
		listaResultados.add(tercerResultadoDeProceso);
		
		
	}

	@Test
	public void mandarEMailProcesoError() throws MessagingException {

		// PONER LA BUSQUEDA QUE SE REALIZO Y SU TIEMPO
		envio = EnviarEmail.mandarCorreoProcesoError(unUsuario,listaResultados);
		Assert.assertTrue(envio);
	}
}
