package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class UsuariosFactory {

	public UsuariosFactory(){

	}

	public Usuario crearUsuario(String username, String password, String rol){
		Usuario nuevoUsuario = null;
		if(cantidadConMismoNombre(username)>1){
			return null;
		}else{
			nuevoUsuario = new Usuario();
			nuevoUsuario.setPassword(password);
			nuevoUsuario.setUsername(username);
			nuevoUsuario.setRol(AuthAPI.getInstance().getRol(rol));
			nuevoUsuario.setFuncionalidades(new HashSet<Accion>());
			nuevoUsuario.setMailHabilitado(true);
			nuevoUsuario.setNotificacionesActivadas(true);
			nuevoUsuario.setAuditoriaActivada(true);

			Set<Accion> funcionalidades = new HashSet<Accion>();
			AuthAPI.getInstance();
			if(rol.equals("ADMIN")){
				for(Accion accion : AuthAPI.getInstance().getAcciones()){
					if(accion.getNombreFuncion().equals("bajaPOIs")
							|| accion.getNombreFuncion().equals("actualizacionLocalesComerciales")
							|| accion.getNombreFuncion().equals("procesoMultiple")
							|| accion.getNombreFuncion().equals("busquedaPOI")
							|| accion.getNombreFuncion().equals("obtenerInfoPOI")
							|| accion.getNombreFuncion().equals("cambiarEstadoMail")){
						funcionalidades.add(accion);
					}
				}
			}else{
				for(Accion accion : AuthAPI.getInstance().getAcciones()){
					if(accion.getNombreFuncion().equals("busquedaPOI")
							|| accion.getNombreFuncion().equals("obtenerInfoPOI")
							|| accion.getNombreFuncion().equals("notificacionBusquedaLarga")
							|| accion.getNombreFuncion().equals("auditoria")){
						funcionalidades.add(accion);
					}
				}
			}
			nuevoUsuario.setFuncionalidades(funcionalidades);

			return nuevoUsuario;
		}
	}

	public int cantidadConMismoNombre(String username){
		return Repositorio.getInstance().usuarios().getEm()
				.createNamedQuery("getUsuarioByName")
				.setParameter("unombre", username)
				.getResultList()
				.size();
	}

}
