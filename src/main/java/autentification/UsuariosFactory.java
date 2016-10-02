package autentification;

import java.util.HashMap;
import java.util.Map;

import db.DB_Usuarios;

public class UsuariosFactory {
	
	public UsuariosFactory(){
		
	}
	
	public Usuario crearUsuario(String username, String password, Rol rol){
		Usuario nuevoUsuario = null;
		if(DB_Usuarios.getInstance().getUsuarioByName(username) != null){
			return null;
		}else{
			nuevoUsuario = new Usuario();
			nuevoUsuario.setID(DB_Usuarios.getInstance().getListaUsuarios().size() + 1);
			nuevoUsuario.setPassword(password);
			nuevoUsuario.setUsername(username);
			nuevoUsuario.setRol(rol);
			nuevoUsuario.setFuncionalidades(new HashMap<String,Accion>());
			nuevoUsuario.setMailHabilitado(true);
			
			Map<String, Accion> funcionalidades = new HashMap <String, Accion>();
			if(rol.equals(Rol.ADMIN)){
				funcionalidades .put("bajaPOIs", AuthAPI.Acciones.get("bajaPOIs"));
				funcionalidades.put("actualizacionLocalesComerciales", AuthAPI.Acciones.get("actualizacionLocalesComerciales"));
				funcionalidades.put("procesoMultiple", AuthAPI.Acciones.get("procesoMultiple"));
				funcionalidades.put("busquedaPOI", AuthAPI.Acciones.get("busquedaPOI"));
				funcionalidades.put("obtenerInfoPOI", AuthAPI.Acciones.get("obtenerInfoPOI"));
				funcionalidades.put("cambiarEstadoMail", AuthAPI.Acciones.get("cambiarEstadoMail"));
			}else{
				funcionalidades.put("busquedaPOI", AuthAPI.Acciones.get("busquedaPOI"));
				funcionalidades.put("obtenerInfoPOI", AuthAPI.Acciones.get("obtenerInfoPOI"));
			}
			
		//AGREGADO POR LUCAS
			DB_Usuarios.getInstance().agregarUsuarioALista(nuevoUsuario);
			return nuevoUsuario;
		}
	}

}
