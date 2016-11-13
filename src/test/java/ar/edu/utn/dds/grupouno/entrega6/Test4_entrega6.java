package ar.edu.utn.dds.grupouno.entrega6;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class Test4_entrega6 {
	
	Repositorio repositorio;
	Usuario usuario;
	UsuariosFactory ufactory = new UsuariosFactory();
	Usuario recuperado;
	
	@Before
	public void init (){
		usuario = ufactory.crearUsuario("admin", "password","ADMIN");
		repositorio = Repositorio.getInstance();
	}
	
	@Test
	public void persistirUsuarios() throws InterruptedException{
		
		usuario.setAuditoriaActivada(true);
		usuario.setCorreo("uncorreo@correoloco.com");
		usuario.setLog(true);
		usuario.setMailHabilitado(true);
		usuario.setNombre("Shaggy");
		usuario.setNotificacionesActivadas(true);
		
		repositorio.usuarios().persistirUsuario(usuario);
		
		recuperado = repositorio.usuarios().getUsuarioByName("admin");
		
		Assert.assertTrue(recuperado.getUsername().equals("admin"));
		
		recuperado.setUsername("prueba");
		
		repositorio.usuarios().actualizarUsuario(recuperado);

		recuperado = repositorio.usuarios().getUsuarioByName("prueba");

		Assert.assertTrue(recuperado.getUsername().equals("prueba"));
	}
	
	@After
	@Transactional
	public void outtro() {
			repositorio.getEm().getTransaction().begin();
			repositorio.getEm().remove(recuperado);
			repositorio.getEm().getTransaction().commit();
	}

	
}

