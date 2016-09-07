package autentification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class AuthAPI {


	Map<String, String> diccionarioTokenUser = new HashMap<String, String>();
	public static Map<String,Accion> Acciones;
	// ESTA LISTA DE USUARIOS DEBERIA SER LA BASE DE DATOS
	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

	public AuthAPI(){
		diccionarioTokenUser = new HashMap<String, String>();
		Acciones = new HashMap<String,Accion>();
		// ESTA LISTA DE USUARIOS DEBERIA SER LA BASE DE DATOS
		listaUsuarios = new ArrayList<Usuario>();
		Acciones.put("busquedaPorId", new funcBusquedaPorId());
		Acciones.put("busquedaPorUsuario", new funcBusquedaPorUsuario());
		Acciones.put("busquedasPorFecha", new funcBusquedasPorFecha());
		Acciones.put("CantidadResultadosPorTerminal", new funcCantidadResultadosPorTerminal());
		Acciones.put("EnviarMail", new funcEnviarMail());
	}

	public ArrayList<Usuario> getListaUsuarios(){
		return listaUsuarios;
	}

	public Usuario crearUsuario(String username, String password, Rol rol){
		Usuario user = new Usuario();
		user.setID(listaUsuarios.size()+1);
		user.setPassword(password);
		user.setUsername(username);
		user.setRol(rol);
		if (rol.getNombre().equals("admin")){
			user.funcionalidades = new HashMap<String,Accion>();
		}
		return user;
	}

	public boolean agregarFuncionalidad(String funcionalidad, Usuario user){
		if(user.rol.getNombre().equals("admin")){
			if(user.funcionalidades.get(funcionalidad)!=null){
				return false; //ya existe
			}else{
				if(user.funcionalidades.put(funcionalidad, Acciones.get(funcionalidad)) != null){
					return true;
				}else{
					return false; //la funcionalidad no existe.
				}
			}
		}else{
			return false; //El usuario no es admin
		}
	}

	public boolean sacarFuncionalidad(String funcionalidad, Usuario user){
		if(user.rol.getNombre().equals("admin")){
			if(user.funcionalidades.remove(funcionalidad)!=null){
				return true;
			}else{
				return false; //No existe la funcionalidad
			}
		}else{
			return false; //El usuario no es admin
		}
	}
	
	public boolean chequearFuncionalidad(String funcionalidad, Usuario user){
		if(user.funcionalidades.get(funcionalidad)!=null){
			return true;
		}else{
			return false;
		}
	}


	public String iniciarSesion(String user, String pass) throws NoSuchAlgorithmException {

		// LA PASS YA DEBERIA LLEGAR HASHEADA AL ENTRAR A ESTA FUNCION,
		// preguntarme si no captan el por que

		for (Usuario usuario : listaUsuarios) {
			if (usuario.getUsername().equals(user) && usuario.getPassword().equals(pass)) {
				String token = generarToken(user, pass);
				diccionarioTokenUser.put(token, user);
				return token;
			}
		}

		return null;
	}

	public String hashear(String string) throws NoSuchAlgorithmException {
		// Esta funcion en una de esas quizas va en las comunes
		String userPass = string;
		MessageDigest hasher = MessageDigest.getInstance("SHA-256");
		hasher.update(userPass.getBytes());

		return DatatypeConverter.printHexBinary(hasher.digest());
	}

	public String generarToken(String user, String pass) throws NoSuchAlgorithmException {
		Date fecha = new Date();
		fecha.getTime();

		String stringGenerador = user + pass + fecha.toString();

		return hashear(stringGenerador);
	}

	public Boolean validarToken(String Token) {

		if (diccionarioTokenUser.get(Token) != null) {
			return true;
		}

		return false;
	}

}
