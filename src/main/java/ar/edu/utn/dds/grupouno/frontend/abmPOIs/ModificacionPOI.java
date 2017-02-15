package ar.edu.utn.dds.grupouno.frontend.abmPOIs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.abmc.POI_ABMC;
import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.CGP;
import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.NodoServicio;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.abmc.poi.Rubro;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ViewScoped
public class ModificacionPOI {

	private POI poiSeleccionado;
	private String nombre;
	private String callePrincipal;
	private String calleLateral;
	private String numeracion;
	private String piso;
	private String departamento;
	private String unidad;
	private String codigoPostal;
	private String localidad;
	private String barrio;
	private String provincia;
	private String pais;
	private String latitud;
	private String longitud;
	private String comuna;
	private String tipo;
	private TiposPOI tipoPOI = TiposPOI.BANCO;
	private List<NodoServicio> servicios = new ArrayList<NodoServicio>();
	public List<Long> diasLocal = new ArrayList<Long>();
	public List<Long> horasLocal = new ArrayList<Long>();
	private String etiquetas;
	private List<String> tipos = new ArrayList<String>();
	private List<String> dias = new ArrayList<String>();
	private List<String> horas = new ArrayList<String>();
	private String cercania;

	private String[] diasSeleccionados;
	private String[] diasLocalSeleccionados;
	private String[] horasLocalSeleccionados;

	// BANCOS
	private String sucursal;
	private String gerente;

	// PARADAS
	private String linea;

	// CGP
	private String director;
	private String telefono;

	// LOCALES
	private String rubro;

	private NodoServicio nodoServicioCreando = new NodoServicio();
	private POI_DTO poiDTO;

	@SuppressWarnings("unchecked")
	public ModificacionPOI() {
		tipos.add(TiposPOI.BANCO.name());
		tipos.add(TiposPOI.CGP.name());
		tipos.add(TiposPOI.LOCAL_COMERCIAL.name());
		tipos.add(TiposPOI.PARADA_COLECTIVO.name());
		dias.add("DOMINGO");
		dias.add("LUNES");
		dias.add("MARTES");
		dias.add("MIERCOLES");
		dias.add("JUEVES");
		dias.add("VIERNES");
		dias.add("SABADO");
		for (int i = 0; i <= 24; i++)
			horas.add(Integer.toString(i));

		String username = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username"));
		String token = ((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token"));
		Usuario usuario = Repositorio.getInstance().usuarios().getUsuarioByName(username);
		FacesContext context = FacesContext.getCurrentInstance();
		long id = Long
				.parseLong(((String) context.getExternalContext().getFlash().get("poiSeleccionado" + token + usuario)));
		this.setPoiSeleccionado(Repositorio.getInstance().pois().getPOIbyId(id));
		if (this.poiSeleccionado != null) {

			context.getExternalContext().getFlash().remove("poiSeleccionado" + token + usuario);
			this.setNombre(poiSeleccionado.getNombre());
			this.setCallePrincipal(poiSeleccionado.getCallePrincipal());
			this.setCalleLateral(poiSeleccionado.getCalleLateral());
			this.setNumeracion(Long.toString(this.poiSeleccionado.getNumeracion()));
			this.setPiso(Long.toString(this.poiSeleccionado.getPiso()));
			this.setDepartamento(this.poiSeleccionado.getDepartamento());
			this.setUnidad(this.poiSeleccionado.getUnidad());
			this.setCodigoPostal(Long.toString(this.poiSeleccionado.getCodigoPostal()));
			this.setLocalidad(this.poiSeleccionado.getLocalidad());
			this.setBarrio(this.poiSeleccionado.getBarrio());
			this.setProvincia(this.poiSeleccionado.getProvincia());
			this.setPais(this.poiSeleccionado.getPais());
			this.setLatitud(Double.toString(this.poiSeleccionado.getLatitud()));
			this.setLongitud(Double.toString(this.poiSeleccionado.getLongitud()));
			this.setComuna(Long.toString(this.poiSeleccionado.getComuna()));
			this.setTipo(this.poiSeleccionado.getTipo().nombre());
			tipoPOI = TiposPOI.getEnumByString(tipo);
			String etique = "";
			for (String et : this.poiSeleccionado.getEtiquetas()) {
				etique = etique + et + " ";
			}
			this.setEtiquetas(etique);

			if (this.getTipoPOI().equals(TiposPOI.CGP)) {
				this.setDirector(((CGP) this.poiSeleccionado).getDirector());
				this.setTelefono(((CGP) this.poiSeleccionado).getTelefono());
				this.setCercania(Long.toString(((CGP) this.poiSeleccionado).getCercania()));
				List<NodoServicio> servPOI = ((CGP) this.poiSeleccionado).getServicios();
				if (servPOI != null && servPOI.size() > 0)
					for (int i = 0; i < servPOI.size(); i++)
						this.getServicios().add(servPOI.get(i));
			} else if (this.getTipoPOI().equals(TiposPOI.LOCAL_COMERCIAL)) {
				this.setRubro(((LocalComercial) this.poiSeleccionado).getRubro().getNombre());
				this.setCercania(Long.toString(((LocalComercial) this.poiSeleccionado).getCercania()));
				for (Long number : ((LocalComercial) this.poiSeleccionado).getDias())
					this.getDias().add(number.toString());
				for (Long number : ((LocalComercial) this.poiSeleccionado).getHoras())
					this.getHoras().add(number.toString());
			} else if (this.getTipoPOI().equals(TiposPOI.BANCO)) {
				this.setSucursal(((Banco) this.poiSeleccionado).getSucursal());
				this.setGerente(((Banco) this.poiSeleccionado).getGerente());
				this.setCercania(Long.toString(((Banco) this.poiSeleccionado).getCercania()));
				List<NodoServicio> servPOI = ((Banco) this.poiSeleccionado).getServicios();
				if (servPOI != null && servPOI.size() > 0)
					for (int i = 0; i < servPOI.size(); i++)
						this.getServicios().add(servPOI.get(i));
			} else if (this.getTipoPOI().equals(TiposPOI.PARADA_COLECTIVO)) {
				this.setLinea(Long.toString(((ParadaColectivo) this.poiSeleccionado).getLinea()));
			}
		}
		RequestContext.getCurrentInstance().update(":Servicios");

	}

	public void setPoiSeleccionado(POI poiSeleccionado) {
		this.poiSeleccionado = poiSeleccionado;
	}

	public void reset() {

		this.nombre = "";
		this.callePrincipal = "";
		this.calleLateral = "";
		this.numeracion = "";
		this.piso = "";
		this.departamento = "";
		this.unidad = "";
		this.codigoPostal = "";
		this.localidad = "";
		this.barrio = "";
		this.provincia = "";
		this.pais = "";
		this.latitud = "";
		this.longitud = "";
		this.comuna = "";
		this.setServicios(new ArrayList<NodoServicio>());
		this.setHorasLocal(new ArrayList<Long>());
		this.setDiasLocal(new ArrayList<Long>());
		this.etiquetas = "";
		this.cercania = "";
		this.diasSeleccionados = new String[0];
		this.diasLocalSeleccionados = new String[0];
		this.horasLocalSeleccionados = new String[0];
		this.sucursal = "";
		this.gerente = "";
		this.linea = "";
		this.director = "";
		this.telefono = "";
		this.rubro = "";
		this.nodoServicioCreando = new NodoServicio();
		this.poiDTO = null;
	}

	public String home() {
		RequestContext.getCurrentInstance().reset("form:panel");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/tp-anual/faces/welcome.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "welcome";
	}

	public POI getPoiSeleccionado() {
		return poiSeleccionado;
	}

	// Agregar un Servicio a la lista
	public void agregarServicio() {
		for (String dia : diasSeleccionados) {
			Dias diaEnum = Dias.valueOf(dia);
			this.nodoServicioCreando.agregarDia(diaEnum.getValue());
		}
		servicios.add(this.nodoServicioCreando);
		nodoServicioCreando = new NodoServicio();
		this.diasSeleccionados = new String[0];
	}

	// Eliminar servicio de la lista de servicios a crear junto con el POI
	public void removeServicio(NodoServicio servicio) {
		try {
			servicios.remove(servicio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i <= 24; i++) {
			results.add(query + i);
		}

		return results;
	}

	public String modificacionPOI() {

		// cargar valores
		poiSeleccionado.setNombre(this.getNombre());
		poiSeleccionado.setCallePrincipal(this.getCallePrincipal());
		poiSeleccionado.setCalleLateral(this.getCalleLateral());
		if (numeracion != "")
			poiSeleccionado.setNumeracion(Integer.parseInt(this.getNumeracion()));
		if (piso != "")
			poiSeleccionado.setPiso(Integer.parseInt(this.getPiso()));
		poiSeleccionado.setDepartamento(this.getDepartamento());
		poiSeleccionado.setUnidad(this.getUnidad());
		if (codigoPostal != "")
			poiSeleccionado.setCodigoPostal(Integer.parseInt(this.getCodigoPostal()));
		poiSeleccionado.setLocalidad(this.getLocalidad());
		poiSeleccionado.setBarrio(this.getBarrio());
		poiSeleccionado.setProvincia((this.getProvincia()));
		poiSeleccionado.setPais(this.getPais());
		if (latitud != "")
			poiSeleccionado.setLatitud(Double.parseDouble(this.getLatitud()));
		if (longitud != "")
			poiSeleccionado.setLongitud(Double.parseDouble(this.getLongitud()));
		if (comuna != "")
			poiSeleccionado.setComuna(Integer.parseInt(this.getComuna()));
		String[] filter = etiquetas.split("\\s+");
		String[] et = new String[filter.length];
		int contador = 0;
		for (String palabra : filter) {
			et[contador] = palabra;
			contador++;
		}
		poiSeleccionado.setEtiquetas(et);

		// Atributos particulares para distintos tipos de POIs
		if (this.getTipoPOI().equals(TiposPOI.CGP)) {
			((CGP) poiSeleccionado).setDirector(director);
			((CGP) poiSeleccionado).setTelefono(telefono);
			if (cercania != "")
				poiSeleccionado.setCercania(Integer.parseInt(cercania));
			poiSeleccionado.setServicios((ArrayList<NodoServicio>) servicios);
		} else if (this.getTipoPOI().equals(TiposPOI.LOCAL_COMERCIAL)) {
			Rubro nuevoRubro = new Rubro();
			nuevoRubro.setNombre(rubro);
			if (cercania != "")
				nuevoRubro.setCercania(Integer.parseInt(cercania));
			((LocalComercial) poiSeleccionado).setRubro(nuevoRubro);
			for (String dia : diasLocalSeleccionados) {
				Dias diaEnum = Dias.valueOf(dia);
				this.diasLocal.add((long) diaEnum.getValue());
			}
			for (String hora : horasLocalSeleccionados) {
				this.horasLocal.add(Long.parseLong(hora));
			}
			((LocalComercial) poiSeleccionado).setDias((ArrayList<Long>) diasLocal);
			((LocalComercial) poiSeleccionado).setHoras((ArrayList<Long>) horasLocal);
		} else if (this.getTipoPOI().equals(TiposPOI.BANCO)) {
			((Banco) poiSeleccionado).setSucursal(sucursal);
			((Banco) poiSeleccionado).setGerente(gerente);
			if (cercania != "")
				poiSeleccionado.setCercania(Integer.parseInt(cercania));
			poiSeleccionado.setServicios((ArrayList<NodoServicio>) servicios);
		} else if (this.getTipoPOI().equals(TiposPOI.PARADA_COLECTIVO)) {
			if (this.linea != "")
				((ParadaColectivo) poiSeleccionado).setLinea(Integer.parseInt(linea));

		}

		// Modificando el POI
		if (POI_ABMC.getInstance().modificar(poiSeleccionado)) {
			// Mensaje de POI modificado
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('poiModificadoDialog').show();");
			reset();
			return "welcome";
		} else {
			// Mensaje de Error
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('poiModificadoDialogError').show();");
			reset();
			return "welcome";
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCallePrincipal() {
		return callePrincipal;
	}

	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}

	public String getCalleLateral() {
		return calleLateral;
	}

	public void setCalleLateral(String calleLateral) {
		this.calleLateral = calleLateral;
	}

	public String getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public TiposPOI getTipoPOI() {
		return tipoPOI;
	}

	public void setTipoPOI(TiposPOI tipoPOI) {
		this.tipoPOI = tipoPOI;
	}

	public List<NodoServicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<NodoServicio> servicios) {
		this.servicios = servicios;
	}

	public List<Long> getDiasLocal() {
		return diasLocal;
	}

	public void setDiasLocal(List<Long> diasLocal) {
		this.diasLocal = diasLocal;
	}

	public List<Long> getHorasLocal() {
		return horasLocal;
	}

	public void setHorasLocal(List<Long> horasLocal) {
		this.horasLocal = horasLocal;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	public List<String> getDias() {
		return dias;
	}

	public void setDias(List<String> dias) {
		this.dias = dias;
	}

	public List<String> getHoras() {
		return horas;
	}

	public void setHoras(List<String> horas) {
		this.horas = horas;
	}

	public String getCercania() {
		return cercania;
	}

	public void setCercania(String cercania) {
		this.cercania = cercania;
	}

	public String[] getDiasSeleccionados() {
		return diasSeleccionados;
	}

	public void setDiasSeleccionados(String[] diasSeleccionados) {
		this.diasSeleccionados = diasSeleccionados;
	}

	public String[] getDiasLocalSeleccionados() {
		return diasLocalSeleccionados;
	}

	public void setDiasLocalSeleccionados(String[] diasLocalSeleccionados) {
		this.diasLocalSeleccionados = diasLocalSeleccionados;
	}

	public String[] getHorasLocalSeleccionados() {
		return horasLocalSeleccionados;
	}

	public void setHorasLocalSeleccionados(String[] horasLocalSeleccionados) {
		this.horasLocalSeleccionados = horasLocalSeleccionados;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public NodoServicio getNodoServicioCreando() {
		return nodoServicioCreando;
	}

	public void setNodoServicioCreando(NodoServicio nodoServicioCreando) {
		this.nodoServicioCreando = nodoServicioCreando;
	}

	public POI_DTO getPoiDTO() {
		return poiDTO;
	}

	public void setPoiDTO(POI_DTO poiDTO) {
		this.poiDTO = poiDTO;
	}

	public void listener() {
		tipoPOI = TiposPOI.valueOf(tipo);
	}

}
