package ar.edu.utn.dds.grupouno.RegistroHistorico;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import ar.edu.utn.dds.grupouno.db.poi.Banco;
import ar.edu.utn.dds.grupouno.db.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.RegistroHistorico;


public class testRegistroHistorico {
	RegistroHistorico unRegistroHistorico;
	ArrayList<POI> listaDePOIs;
	LocalComercial local1;
	Banco banco1;
	DateTime fecha;
	
	@Before
	public void init(){
		listaDePOIs = new ArrayList<POI>();
		local1 = new LocalComercial();
		banco1 = new Banco();
		fecha = new DateTime(2016,10,18,0,0);
		
		local1.setNombre("local1");
		local1.setId((long) 11);
		banco1.setNombre("banco1");
		banco1.setId((long) 22);
		
		
		listaDePOIs.add(local1);
		listaDePOIs.add(banco1);
		
		
	}
	
	@Test
	public void testDeRegistroHistorico(){
		
		unRegistroHistorico=new RegistroHistorico(fecha, 1, "unaStringDeBusqueda",2,12,listaDePOIs);
		
				Assert.assertEquals(0,unRegistroHistorico.getId());
				Assert.assertEquals(fecha,unRegistroHistorico.getTime());
				Assert.assertEquals(2, unRegistroHistorico.getListaDePOIs().size());
				
	}
}

