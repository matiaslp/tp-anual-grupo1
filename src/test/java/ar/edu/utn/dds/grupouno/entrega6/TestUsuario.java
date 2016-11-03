package ar.edu.utn.dds.grupouno.entrega6;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestUsuario {
	
	private static final String PERSISTENCE_UNIT_NAME = "tp-anual";
	private EntityManagerFactory emFactory;
	Repositorio repositorio;
	Usuario usuario;
	UsuariosFactory ufactory = new UsuariosFactory();
	
	@Before
	public void init (){
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		repositorio = new Repositorio(emFactory.createEntityManager());
	}
	
	@Test
	public void persistirUsuarios() throws InterruptedException{
		usuario = ufactory.crearUsuario("admin", "password","ADMIN");
		
		repositorio.usuarios().persistirUsuario(usuario);
		
		Usuario recuperado = repositorio.usuarios().getUsuarioByName("admin");
		
		Assert.assertTrue(recuperado.getUsername().equals("admin"));
		
		repositorio.usuarios().updateUsername(recuperado.getId(), "prueba");
		
		Repositorio.getInstance().getEm().clear();
		Usuario recuperado2 = repositorio.usuarios().getUsuarioByName("prueba");
		
		Assert.assertTrue(recuperado2.getUsername().equals("prueba"));

		
	}

}

