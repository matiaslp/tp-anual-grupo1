package test_poi;

import org.junit.Before;
import org.junit.Test;

import poi.Banco;
import poi.CGP;
import poi.LocalComercial;
import poi.NodoServicio;
import poi.ParadaColectivo;
import poi.Rubro;

import java.util.Calendar;

import org.junit.Assert;

public class TestDisponibilidades {
	public Banco banco;

	/// --------------------BANCO----------------------------
	@Before
	public void inicializarBanco() {
		banco = new Banco("macro", 36, 53);
	}

	@Test
	public void chequearCajero() {
		boolean resultado = banco.disponible("cajero");
		Assert.assertTrue(resultado);
	}

	@Test
	public void chequearVacio() {
		boolean resultado = banco.disponible("");
		Assert.assertTrue(resultado);
	}

	@Test
	public void chequearServicioDependienteDelHorario() {
		boolean resultado = banco.disponible("caja de ahorro");
		Assert.assertFalse(resultado);
	}

	/// -----------------------CGP-----------------------

	public CGP cgp;

	@Before
	public void inicializarCGP() {
		cgp = new CGP("mataderos", 23, 44);
		NodoServicio nuevoNodo = new NodoServicio();
		nuevoNodo.setName("rentas");
		nuevoNodo.agregarDia(5); // Cambiando estos parametros probas todos los
									// casos.
		nuevoNodo.setHoras(2, 3);// el horario mas grande de los cgp es de 9 a
									// 18
		cgp.setServicio(nuevoNodo);
	}
	
	//Este teste que haya un servicio rentas, se puede jugar con el horario igualmente.
	@Test
	public void chequearConServicio() {
		Calendar calendario = Calendar.getInstance();
		cgp.getServicios().get(0).agregarDia(calendario.get(Calendar.DAY_OF_WEEK));//para que se agrege el dia actual, sino devuelve falso
		cgp.getServicios().get(0).setHoras(0, 24); //para que se pueda testear a la hora que sea, si no devuelve falso
		boolean resultado = cgp.disponible("rentas");
		Assert.assertTrue(resultado);
	}

	//La idea de este es chequear los horarios mas que la existencia de un servicio
	@Test
	public void chequearSinServicio() {
		Calendar calendario = Calendar.getInstance();
		cgp.getServicios().get(0).agregarDia(calendario.get(Calendar.DAY_OF_WEEK));//para que se agrege el dia actual, sino devuelve falso
		cgp.getServicios().get(0).setHoras(0, 24); //para que se pueda testear a la hora que sea, si no devuelve falso
		//Modificar el horario para testear
		boolean resultado = cgp.disponible("");
		Assert.assertTrue(resultado);
	}

	@Test
	public void chequearConServicioInexistente() {
		boolean resultado = cgp.disponible("sfasfa");
		Assert.assertFalse(resultado);
	}

	//// ----------------------Local Comercial-----------------------

	LocalComercial local;

	@Before
	public void inicializarLocal() {
		Rubro rubro = new Rubro("supermercado");
		local = new LocalComercial("coto", 23, 51, rubro);
		local.dias.add((long) 1);
		local.dias.add((long) 2);
		local.dias.add((long) 3);
		local.dias.add((long) 4);
		local.dias.add((long) 5);
		local.dias.add((long) 6);
		local.dias.add((long) 7);// Modificando estos parametros se puede testear
							// todo. No time for writing all :(
		local.horas.add((long) 13);
		local.horas.add((long) 14);
		local.horas.add((long) 15);
		local.horas.add((long) 16);
		local.horas.add((long) 17);
		local.horas.add((long) 18);
		local.horas.add((long) 19);
		local.horas.add((long) 20);
		local.horas.add((long) 21);
		local.horas.add((long) 22);
		local.horas.add((long) 23);
	}

	@Test
	public void chequearDiayHorarios() {
		
		boolean resultado = local.disponible();
		Assert.assertTrue(resultado);
	}

	//// ---------------------------Parada Colectivo----------------------
	ParadaColectivo parada;

	@Before
	public void inicializarParada() {
		parada = new ParadaColectivo("55", 31, 13);
	}

	@Test
	public void chequearDispParada() {
		boolean resultado = parada.disponible();
		Assert.assertTrue(resultado);
	}

}
