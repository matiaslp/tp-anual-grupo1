package ar.edu.utn.dds.grupouno.entrega7;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.query.Query;

import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.RepoMongo;

public class testMongo {
	
	UsuariosFactory fact = new UsuariosFactory();
	Usuario user;
	
	@Before
	public void init(){
		user = fact.crearUsuario("admin", "password", "TERMINAL");
	}
	
	@Test
	public void persistirUser(){
		Rol admin = new Rol("ADMIN");
		RepoMongo.getInstance().getDatastore().save(admin);
		RepoMongo.getInstance().getDatastore().save(user);
		
		Query<Usuario> query = RepoMongo.getInstance().getDatastore().find(Usuario.class);
		List<Usuario> lista = query.asList();
		
		for(Usuario nuevo : lista){
			if(nuevo.getUsername().equals("admin")){
				Assert.assertTrue(true);
			}
		}
		
	}
}
