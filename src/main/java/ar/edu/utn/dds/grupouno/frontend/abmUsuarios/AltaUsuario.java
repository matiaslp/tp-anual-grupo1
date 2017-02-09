package ar.edu.utn.dds.grupouno.frontend.abmUsuarios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ViewScoped
public class AltaUsuario {
	private String nombre;
	private String logueo;
	private String correo;
	private String mailHabilitado;
	private String password;
	private String username;
	private String rol = "TERMINAL";
	private List<String> roles = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public AltaUsuario() {
		roles.add("TERMINAL");
		roles.add("ADMIN");
		reset();
	}

	public void listener() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isAdmin() {
		if (rol != null && rol.equals("ADMIN"))
			return true;
		return false;
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

	public String getLogueo() {
		return logueo;
	}

	public void setLogueo(String logueo) {
		this.logueo = logueo;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void altaUsuario() {

		UsuariosFactory fact = new UsuariosFactory();
		Usuario unUsuario;
		// Crear instancia de usuario
		unUsuario = fact.crearUsuario(this.getUsername(), this.getPassword(), this.getRol());
		if (unUsuario != null) {
			unUsuario.setNombre(nombre);
			unUsuario.setLog(Boolean.parseBoolean(logueo));		
			// Solo si es admin
			if (this.isAdmin()) {
				unUsuario.setCorreo(this.getCorreo());
				unUsuario.setMailHabilitado(Boolean.parseBoolean(this.mailHabilitado));
			}
			//persistir
			Repositorio.getInstance().usuarios().persistirUsuario(unUsuario);
		
			// Mostrar pantalla de creacion exitosa
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioCreadoDialog').show();");
			reset();
		} else {
			// Mostrar pantalla de nombre repetido
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioCreadoDialogError').show();");
		}
	}
	
	public void reset(){
		this.setNombre("");
		this.setLogueo("");
		this.setCorreo("");
		this.setMailHabilitado("");
		this.setPassword("");
		this.setUsername("");
		this.setRol("TERMINAL");
	}

}
