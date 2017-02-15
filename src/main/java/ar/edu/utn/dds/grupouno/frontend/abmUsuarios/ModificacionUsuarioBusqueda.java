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
public class ModificacionUsuarioBusqueda {

	private String busqueda;
	private Usuario usuarioSeleccionado = null;

	@SuppressWarnings("unchecked")
	public ModificacionUsuarioBusqueda() {
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String seleccionUsuario() {

		usuarioSeleccionado = Repositorio.getInstance().usuarios().getUsuarioByName(busqueda);
		if (usuarioSeleccionado == null) {
			// Mostrar pantalla de nombre repetido
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('usuarioModificadoDialogError').show();");
			return "";
		} else {
			String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("username"));
			String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("token"));
			Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().put("usuarioSeleccionado" + token + usuario,
					Long.toString(this.usuarioSeleccionado.getId()));
			reset();
			return "usuarioModificacionB";
		}
	}

	public void reset() {
		this.busqueda = "";
		this.usuarioSeleccionado = null;
	}

}
