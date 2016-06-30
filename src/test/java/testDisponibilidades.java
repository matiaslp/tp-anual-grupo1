import org.junit.Before;
import org.junit.Test;

import POI.Banco;
import POI.CGP;
import POI.LocalComercial;
import POI.ParadaColectivo;
import POI.Rubro;
import POI.nodoServicioCGP;

import org.junit.Assert;

public class testDisponibilidades {
	public Banco banco;
	
	/// --------------------BANCO----------------------------
	@Before
	public void inicializarBanco(){
		banco = (Banco) Banco.ConstructorBanco("macro", 36, 53);
	}
	
	@Test
	public void chequearCajero(){
		boolean resultado = banco.disponible("cajero");
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void chequearVacio(){
		boolean resultado = banco.disponible("");
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void chequearServicioDependienteDelHorario(){
		boolean resultado = banco.disponible("caja de ahorro");
		Assert.assertFalse(resultado);
	}
	
	///-----------------------CGP-----------------------
	
	public CGP cgp;
	
	@Before
	public void inicializarCGP(){
		cgp = (CGP) CGP.ConstructorCGP("mataderos", 23, 44);
		nodoServicioCGP nuevoNodo = new nodoServicioCGP();
		nuevoNodo.setName("rentas");
		nuevoNodo.agregarDia(5); //Cambiando estos parametros probas todos los casos.
		nuevoNodo.setHoras(2,3);//el horario mas grande de los cgp es de 9 a 18
		CGP.Servicios.add(nuevoNodo);
	}
	
	@Test
	public void chequearConServicio(){
		boolean resultado = cgp.disponible("rentas");
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void chequearSinServicio(){
		boolean resultado = cgp.disponible("");
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void chequearConServicioInexistente(){
		boolean resultado = cgp.disponible("sfasfa");
		Assert.assertFalse(resultado);
	}
	
	////----------------------Local Comercial-----------------------
	
	
	LocalComercial local;
	@Before
	public void inicializarLocal(){
		Rubro rubro = new Rubro("supermercado");
		 local = (LocalComercial) LocalComercial.ConstructorLocalComercial("coto", 23, 51, rubro);
		local.dias.add(5); //Modificando estos parametros se puede testear todo. No time for writing all :(
		local.horas.add(2);
		local.horas.add(5);
	}
	
	@Test
	public void chequearDiayHorarios(){
		boolean resultado =local.disponible();
		Assert.assertTrue(resultado);
	}
	
	////---------------------------Parada Colectivo----------------------
	 ParadaColectivo parada;
	 @Before
	 public void inicializarParada(){
		 parada = (ParadaColectivo) ParadaColectivo.ConstructorParadaColectivo("55", 31, 13);
	 }
	 
	@Test
	public void chequearDispParada(){
		boolean resultado = parada.disponible();
		Assert.assertTrue(resultado);
	}
	

}
