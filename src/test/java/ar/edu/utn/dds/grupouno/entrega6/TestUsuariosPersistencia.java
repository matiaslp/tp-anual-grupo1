package ar.edu.utn.dds.grupouno.entrega6;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class TestUsuariosPersistencia {

	Usuario user;
	UsuariosFactory fact = new UsuariosFactory();
	
	@Before
	public void init(){
		user = fact.crearUsuario("administrator", "password", "ADMIN");
		Repositorio.getInstance().usuarios().persistirUsuario(user);
	}
	
	@Test
	public void testearNombre(){
		user.setNombre("abc");
		Repositorio.getInstance().usuarios().actualizarUsuario(user);
		Assert.assertTrue(Repositorio.getInstance()
				.usuarios().getUsuarioByName("abc")!=null);
	}
	
	@Test
	public void testearCorreo(){
		user.setCorreo("uncorreo@blabla.com");
		Repositorio.getInstance().usuarios().actualizarUsuario(user);
		Assert.assertTrue(Repositorio.getInstance()
				.usuarios().getUsuarioByName("administrator")
				.getCorreo().equals("uncorreo@blabla.com"));
	}
	
	@Test
	public void testearBooleanos(){
		user.setAuditoriaActivada(false);
		user.setLog(false);
		user.setNotificacionesActivadas(false);
		user.setMailHabilitado(false);
		Repositorio.getInstance().usuarios().actualizarUsuario(user);
		Usuario nuevouser = Repositorio.getInstance()
				.usuarios().getUsuarioByName("administrator");
		
		Assert.assertTrue(nuevouser.isNotificacionesActivadas()==false
				&& nuevouser.isAuditoriaActivada()==false
				&& nuevouser.isMailHabilitado()==false
				&& nuevouser.isLog() == false);
	}
	
	@Test
	public void testearPassword(){
		user.setPassword("holahola");
		Repositorio.getInstance().usuarios().actualizarUsuario(user);
		Assert.assertTrue(Repositorio.getInstance()
				.usuarios().getUsuarioByName("administrator").getPassword()
				.equals("holahola"));
	}
	
	@After
	public void outtro(){
		Repositorio.getInstance().usuarios().deleteUsuario(user.getId());
	}
}
