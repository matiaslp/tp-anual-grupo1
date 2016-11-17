package ar.edu.utn.dds.grupouno.frontend.abmPOIs;


import javax.faces.bean.ManagedBean;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.DB_POI;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;
 
@ManagedBean
public class altaUsuariosMaskView {
	 private String nombre;
	 private String auditoriaActivada;
	 private String correo;
	 private String mailHabilitado;
	 private String notificacionesActivadas;
	 private String password;
	 private String username;
	 private String rol;

	 public String getNombre() {
			return nombre;
		}




		public void setNombre(String nombre) {
			this.nombre = nombre;
		}




		public String getAuditoriaActivada() {
			return auditoriaActivada;
		}




		public void setAuditoriaActivada(String auditoriaActivada) {
			this.auditoriaActivada = auditoriaActivada;
		}




		public String getCorreo() {
			return correo;
		}




		public void setCorreo(String correo) {
			this.correo = correo;
		}




		public String getMailHabilitado() {
			return mailHabilitado;
		}




		public void setMailHabilitado(String mailHabilitado) {
			this.mailHabilitado = mailHabilitado;
		}




		public String getNotificacionesActivadas() {
			return notificacionesActivadas;
		}




		public void setNotificacionesActivadas(String notificacionesActivadas) {
			this.notificacionesActivadas = notificacionesActivadas;
		}




		public String getPassword() {
			return password;
		}




		public void setPassword(String password) {
			this.password = password;
		}




		public String getUsername() {
			return username;
		}




		public void setUsername(String username) {
			this.username = username;
		}




		public String getRol() {
			return rol;
		}




		public void setRol(String rol) {
			this.rol = rol;
		}








	
	
	public void altaUsuario() {
		Repositorio DBU;
		
		UsuariosFactory fact = new UsuariosFactory();
		Usuario unUsuario;
		DBU = Repositorio.getInstance();


		unUsuario = fact.crearUsuario(this.getUsername(), this.getPassword(), this.getRol());
		//unUsuario.setAuditoriaActivada(this.getAuditoriaActivada());
		unUsuario.setCorreo(this.getCorreo());
	//	unUsuario.setMailHabilitado(this.getMailHabilitado());
		unUsuario.setNombre(this.getNombre());

		
		DBU.usuarios().persistirUsuario(unUsuario);


	}

	
}
