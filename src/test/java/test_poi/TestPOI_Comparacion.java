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
	Banco otroBanco = new Banco("Galicia", 50, 20);
	Banco stringDistinto = new Banco("asda",50, 20);
	Banco longDistinto = new Banco("Galicia",50,20);
	Banco bancoStringNull = new Banco("Galicia", 50, 20);
	Banco bancoLongNull = new Banco("Galicia",50,20);

	CGP unCGP = new CGP("unCGP", 100, 20);
	CGP otroCGP = new CGP("unCGP", 100, 20);

	Rubro unRubro = new Rubro("Ferreteria");
	LocalComercial unLocal = new LocalComercial("unLocal", 50, 100, unRubro);
	Rubro otroRubro = new Rubro("Farmacia");
	LocalComercial otroLocal = new LocalComercial("otroLocal", 50, 100, otroRubro);

	ParadaColectivo unaParada = new ParadaColectivo("La del 7", 10, 10);
	ParadaColectivo otraParada = new ParadaColectivo("La del 114", 10, 10);

	@Before
	public void init() {

		unBanco.setTipo(TiposPOI.BANCO); //Solo llenamos un atributo de cada tipo
		unBanco.setSucursal("Martinez");
		unBanco.setPiso(1);

		otroBanco.setTipo(TiposPOI.BANCO);
		otroBanco.setSucursal("Martinez");
		otroBanco.setPiso(1);
		
		longDistinto.setPiso(2);
		
		bancoStringNull.setPiso(1);
		
		bancoLongNull.setSucursal("Martinez");
		
		
		//No vale la pena llenar mas datos de los demas tipos porque la comparacion es igual para
		//todos, con comparar 2 bancos con los atributos básicos alcanza para los atributos comunes.
		
		//Los CGPs no tienen tipos de datos que no sean String y Long, y en todos se evaluan
		//de la misma forma, no vale la pena hacer el test.
		
		unLocal.setTipo(TiposPOI.LOCAL_COMERCIAL);
		//DatosLocal

		otroLocal.setTipo(TiposPOI.LOCAL_COMERCIAL);
		//DatosLocal

		unaParada.setTipo(TiposPOI.PARADA_COLECTIVO);
		//DatosParada

		otraParada.setTipo(TiposPOI.PARADA_COLECTIVO);

	}

	@Test
	public void compararStringYLongIguales(){
		Assert.assertTrue(unBanco.compararPOI(otroBanco));
	}
	
	@Test
	public void compararStringDistinto(){
		Assert.assertFalse(unBanco.compararPOI(stringDistinto));//nombre distinto. Sale ahi.
	}
	
	@Test
	public void compararLongDistinto(){
		Assert.assertFalse(unBanco.compararPOI(longDistinto)); //piso distinto, sale ahi.
	}
	
	@Test
	public void compararStringNull(){
		Assert.assertFalse(unBanco.compararPOI(bancoStringNull)); //Sucursal null, sale ahi
	}
	
	@Test
	public void compararLongNull(){
		Assert.assertFalse(unBanco.compararPOI(bancoLongNull)); //piso null, sale ahi
	}
	
	
}
