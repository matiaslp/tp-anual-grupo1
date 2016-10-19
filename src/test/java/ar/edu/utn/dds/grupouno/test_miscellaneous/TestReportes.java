package ar.edu.utn.dds.grupouno.test_miscellaneous;

import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;

public class TestReportes {

	private DB_HistorialBusquedas historial;
	Usuario terminal;
	UsuariosFactory fact;

	@Before
	public void init() {

		historial = DB_HistorialBusquedas.getInstance();
		
		DB_Usuarios.getInstance();
		
		fact = new UsuariosFactory();
		fact.crearUsuario("terminal", "123", Rol.TERMINAL);
		terminal = DB_Usuarios.getInstance().getUsuarioByName("terminal");
		terminal.setID(10);

		DateTime time = new DateTime(2016, 1, 1, 1, 1);
		RegistroHistorico registro = new RegistroHistorico(1, time, 10, "busqueda1", 10, 5);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 2, 2, 2, 2);
		registro = new RegistroHistorico(2, time, 10, "busqueda2", 20, 10);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 3, 3, 3, 3);
		registro = new RegistroHistorico(3, time, 10, "busqueda3", 30, 15);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro = new RegistroHistorico(4, time, 10, "busqueda4", 40, 20);
		historial.agregarHistorialBusqueda(registro);

		time = new DateTime(2016, 4, 4, 4, 4);
		registro = new RegistroHistorico(5, time, 1, "busqueda5", 400, 20);
		historial.agregarHistorialBusqueda(registro);

	}

	@Test
	public void testreporteCantidadResultadosPorTerminal() {
		Map<Long, Long> resultado = historial.reporteCantidadResultadosPorTerminal(10);
		// System.out.printf("Usuario:10\nIdBusqueda cantidadResultados \n");
		// for (Map.Entry<Long, Long> registro : resultado.entrySet())
		// System.out.printf("%s \t\t %s \n",
		// registro.getKey().toString(),registro.getValue().toString());
		Assert.assertTrue(resultado.size() == 4);
	}

	@Test
	public void testReporteBusquedaPorFecha() {
		Map<String, Long> resultado = historial.reporteBusquedasPorFecha();
		
		Locale lenguaje = Locale.getDefault();
		if(lenguaje.equals(Locale.US)||lenguaje.equals(Locale.ENGLISH)||lenguaje.equals(Locale.UK)){
			// Computadoras en ingles
			Assert.assertTrue(resultado.get("4/4/16") == 440);
			Assert.assertTrue(resultado.get("3/3/16") == 30);
			Assert.assertTrue(resultado.get("2/2/16") == 20);
			Assert.assertTrue(resultado.get("1/1/16") == 10);
		}else{
			// Computadoras en espa�ol
			Assert.assertTrue(resultado.get("04/04/16") == 440);
			Assert.assertTrue(resultado.get("03/03/16") == 30);
			Assert.assertTrue(resultado.get("02/02/16") == 20);
			Assert.assertTrue(resultado.get("01/01/16") == 10);

		}



	}

	@Test
	public void testReporteCantidadResultadosPorUsuario() {
		fact.crearUsuario("otro", "admin", Rol.ADMIN);
		DB_Usuarios.getInstance().getUsuarioByName("otro").setID(1);
		Map<Long, Long> resultado = historial.reporteBusquedaPorUsuario();

		// System.out.printf("\nIdUsuario cantidadResultados \n");
		// for (Map.Entry<Long, Long> registro : resultado.entrySet())
		// System.out.printf("%s \t\t %s \n",
		// registro.getKey().toString(),registro.getValue().toString());

		Assert.assertTrue(resultado.size() == 2);
	}
}
