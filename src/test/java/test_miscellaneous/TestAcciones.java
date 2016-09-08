package test_miscellaneous;

import java.util.HashMap;

import org.junit.Before;

import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;

public class TestAcciones {

	private Usuario prueba;
	private AuthAPI Autenticador;
	
	@Before
	public void init(){
		prueba = new Usuario();
		prueba.setID(1);
		prueba.setPassword("password");
		prueba.setUsername("usuario");
		prueba.setRol(Rol.ADMIN);
		prueba.setFuncionalidades(new HashMap<String, Accion>());
		prueba.getFuncionalidades().put("enviarMail", AuthAPI.Acciones.get("enviarMail"));
		
		Autenticador = AuthAPI.getInstance();
		
		Autenticador.getListaUsuarios().add(prueba);
	}
}
