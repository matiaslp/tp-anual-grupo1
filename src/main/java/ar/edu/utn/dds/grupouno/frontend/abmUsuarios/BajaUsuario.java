package ar.edu.utn.dds.grupouno.frontend.abmUsuarios;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ViewScoped
public class BajaUsuario {
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void bajaUsuario() {
		Usuario unUsuario;

		unUsuario = Repositorio.getInstance().usuarios().getUsuarioByName(nombre);
		if (unUsuario != null) {
			Repositorio.getInstance().remove(unUsuario);
			// Mostrar pantalla de Baja Exitosa
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioBajaDialog').show();");
			reset();
		}else{
			// Mostrar pantalla de Error
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioBajaDialogError').show();");
			
		}
		

	}
	
	public void reset(){
		this.setNombre("");
	}

}
