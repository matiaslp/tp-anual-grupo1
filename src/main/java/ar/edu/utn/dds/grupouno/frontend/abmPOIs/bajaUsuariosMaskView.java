package ar.edu.utn.dds.grupouno.frontend.abmPOIs;


import javax.faces.bean.ManagedBean;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
 
@ManagedBean
public class bajaUsuariosMaskView {
	 private String nombre;
	

	 public String getNombre() {
			return nombre;
		}




		public void setNombre(String nombre) {
			this.nombre = nombre;
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