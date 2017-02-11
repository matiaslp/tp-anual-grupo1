package ar.edu.utn.dds.grupouno.frontend.historial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.repositorio.DB_Usuarios;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@SessionScoped
public class busquedaHistorialBean {

	private String usuario;
	private Date fechaDesde;
	private Date fechaHasta;
	private RegistroHistorico rhSeleccionado;

	private List<RegistroHistorico> listaRH = new ArrayList<RegistroHistorico>();

	public void buscar() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		DB_Usuarios usuarios = Repositorio.getInstance().usuarios();
		DB_HistorialBusquedas db_historial = Repositorio.getInstance().resultadosRegistrosHistoricos();
		List<Object[]> listaResultados = new ArrayList<Object[]>();

		String username = (String) sessionMap.get("username");
		String token = (String) sessionMap.get("token");
		Usuario usuario = usuarios.getUsuarioByName(username);

		listaRH.clear();

		try {
			Long usuarioId = null;
			Date fechaDesde = null;
			Date fechaHasta = null;

			// valida el filtro por fecha desde
			if (this.getFechaDesde() != null) {
				fechaDesde = this.getFechaDesde();
			}

			// valida el filtro por fecha hasta
			if (this.getFechaHasta() != null) {
				fechaHasta = this.getFechaHasta();
			}

			// valida el filtro por usuario
			if (!this.getUsuario().isEmpty()) {
				Usuario usuarioBuscado = usuarios.getUsuarioByName(this.getUsuario());
				if (usuarioBuscado != null) {
					usuarioId = usuarioBuscado.getId();
				}
			}

			// se realiza la busqueda si el campo usuario esta vacio o se
			// ingreso un usuario que existe
			if (this.getUsuario().isEmpty() || (!this.getUsuario().isEmpty() && usuarioId != null)) {
				listaResultados = db_historial.historialBusquedaEntreFechas(usuarioId, fechaDesde, fechaHasta);
			} else {
				listaResultados = new ArrayList<Object[]>();
			}

			if (listaResultados != null && listaResultados.size() > 0) {
				for (Object[] resultado : listaResultados) {
					if (resultado != null) {
						listaRH.add(objectToRegistro(resultado));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public busquedaHistorialBean() {

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public RegistroHistorico getRhSeleccionado() {
		return rhSeleccionado;
	}

	public void setRhSeleccionado(RegistroHistorico rhSeleccionado) {
		this.rhSeleccionado = rhSeleccionado;
	}

	public List<RegistroHistorico> getListaRH() {
		return listaRH;
	}

	public void setListaRH(List<RegistroHistorico> listaRH) {
		this.listaRH = listaRH;
	}

	@SuppressWarnings({ "unchecked" })
	private RegistroHistorico objectToRegistro(Object[] objetoHistorial) {
		RegistroHistorico registro = new RegistroHistorico();
		DateTime tiempoConvertido = new DateTime(objetoHistorial[0]);

		registro.setTime(tiempoConvertido);
		registro.setUserID((Long) objetoHistorial[1]);
		registro.setBusqueda((String) objetoHistorial[2]);
		registro.setCantResultados((Long) objetoHistorial[3]);
		registro.setTiempoDeConsulta((double) objetoHistorial[4]);
		registro.setListaDePOIs((ArrayList<POI>) objetoHistorial[5]);
		registro.setId((Long) objetoHistorial[6]);
		return registro;
	}

	public void reset() {
		setFechaHasta(null);
		setFechaDesde(null);
		setUsuario("");
		RequestContext.getCurrentInstance().reset("form:panel");
	}
	
	public String username(String userID){
		return Repositorio.getInstance().usuarios().getUsuarioById(Long.parseLong(userID)).getUsername();
	}
}
