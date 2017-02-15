package ar.edu.utn.dds.grupouno.frontend.abmUsuarios;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.autentification.UsuariosFactory;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;


@ManagedBean
@ViewScoped
public class ModificacionUsuario {
	
	
	private Usuario usuarioSeleccionado = null;
	private String nombre;
	private String logueo;
	private String correo;
	private String mailHabilitado;
	private String password;
	private String username;
	private String rol = "TERMINAL";
	private List<String> roles = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public ModificacionUsuario() {
		

		roles.add("TERMINAL");
		roles.add("ADMIN");
		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		FacesContext context = FacesContext.getCurrentInstance();
		long id = Long
				.parseLong(((String) context.getExternalContext().getFlash().get("usuarioSeleccionado" + token + usuario)));
		this.setUsuarioSeleccionado(Repositorio.getInstance().usuarios().getUsuarioById(id));
		if (this.usuarioSeleccionado != null) {
			
			
			this.setRol(this.getUsuarioSeleccionado().getRol().getValue());
			this.setNombre(this.usuarioSeleccionado.getNombre());
			this.setLogueo(Boolean.toString(this.getUsuarioSeleccionado().isLog()));
			this.setPassword(this.getUsuarioSeleccionado().getPassword());
			this.setUsername(this.getUsuarioSeleccionado().getUsername());
			
			if (this.usuarioSeleccionado.isAdmin()){			
				this.setCorreo(this.getUsuarioSeleccionado().getCorreo());
				this.setMailHabilitado(Boolean.toString(this.getUsuarioSeleccionado().isMailHabilitado()));
			}
			
		}

	}

	

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}



	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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
	
	public void modificacionUsuario() {

		// Modificando instancia de usuario
		if (this.getUsuarioSeleccionado() != null) {
			this.getUsuarioSeleccionado().setNombre(this.getNombre());
			this.getUsuarioSeleccionado().setLog(Boolean.parseBoolean(this.getLogueo()));
			this.getUsuarioSeleccionado().setUsername(this.getUsername());
			// Si se modifico la contraseña
			if(!(this.getPassword().equals(this.usuarioSeleccionado.getPassword())))
				this.getUsuarioSeleccionado().setPassword(this.getPassword());
			// Solo si es admin
			if (this.isAdmin()) {
				this.getUsuarioSeleccionado().setCorreo(this.getCorreo());
				this.getUsuarioSeleccionado().setMailHabilitado(Boolean.parseBoolean(this.mailHabilitado));
			}
			//Actualizar
			
			if( Repositorio.getInstance().usuarios().actualizarUsuario(this.getUsuarioSeleccionado())){
			// Mostrar pantalla de creacion exitosa
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioModificadoDialog').show();");
			reset();
			return;
			}
		}
			// Mostrar pantalla de error
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioModificadoDialogError').show();");
	}
	
	public void reset(){
		this.usuarioSeleccionado = null;
		this.setNombre("");
		this.setLogueo("");
		this.setCorreo("");
		this.setMailHabilitado("");
		this.setPassword("");
		this.setUsername("");
		this.setRol("TERMINAL");
	}
}
