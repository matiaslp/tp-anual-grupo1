package ar.edu.utn.dds.grupouno.frontend.abmPOIs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.abmc.poi.Banco;
import ar.edu.utn.dds.grupouno.abmc.poi.CGP;
import ar.edu.utn.dds.grupouno.abmc.poi.Etiqueta;
import ar.edu.utn.dds.grupouno.abmc.poi.LocalComercial;
import ar.edu.utn.dds.grupouno.abmc.poi.NodoServicio;
import ar.edu.utn.dds.grupouno.abmc.poi.POI;
import ar.edu.utn.dds.grupouno.abmc.poi.ParadaColectivo;
import ar.edu.utn.dds.grupouno.abmc.poi.Rubro;
import ar.edu.utn.dds.grupouno.abmc.poi.TiposPOI;
import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

@ManagedBean
@ViewScoped
public class AltaPOI {
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

	
	// Se generan los String necesarios para mostrar como opciones en pantalla
	@SuppressWarnings("unchecked")
	public AltaPOI() {
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

	public void altaPOI() {

		poiDTO = new POI_DTO();

		poiDTO.setNombre(this.getNombre());
		poiDTO.setCallePrincipal(this.getCallePrincipal());
		poiDTO.setCalleLateral(this.getCalleLateral());
		if (numeracion != "")
			poiDTO.setNumeracion(Integer.parseInt(this.getNumeracion()));
		if (piso != "")
			poiDTO.setPiso(Integer.parseInt(this.getPiso()));
		poiDTO.setDepartamento(this.getDepartamento());
		poiDTO.setUnidad(this.getUnidad());
		if (codigoPostal != "")
			poiDTO.setCodigoPostal(Integer.parseInt(this.getCodigoPostal()));
		poiDTO.setLocalidad(this.getLocalidad());
		poiDTO.setBarrio(this.getBarrio());
		poiDTO.setProvincia((this.getProvincia()));
		poiDTO.setPais(this.getPais());
		if (latitud != "")
			poiDTO.setLatitud(Double.parseDouble(this.getLatitud()));
		if (longitud != "")
			poiDTO.setLongitud(Double.parseDouble(this.getLongitud()));
		if (comuna != "")
			poiDTO.setComuna(Integer.parseInt(this.getComuna()));
		poiDTO.setTipo(TiposPOI.valueOf(tipo));
		String[] filter = etiquetas.split("\\s+");
		String[] et = new String[filter.length];
		int contador = 0;
		for (String palabra : filter) {
			et[contador] = palabra;
			contador++;
		}
		poiDTO.setEtiquetas(et);

		// Atributos particulares para distintos tipos de POIs
		if (this.getTipoPOI().equals(TiposPOI.CGP)) {
			poiDTO.setDirector(director);
			poiDTO.setTelefono(telefono);
			if (cercania != "")
				poiDTO.setCercania(Integer.parseInt(cercania));
			poiDTO.setServicios((ArrayList<NodoServicio>) servicios);
		} else if (this.getTipoPOI().equals(TiposPOI.LOCAL_COMERCIAL)) {
			Rubro nuevoRubro = new Rubro();
			nuevoRubro.setNombre(rubro);
			if (cercania != "")
				nuevoRubro.setCercania(Integer.parseInt(cercania));
			poiDTO.setRubro(nuevoRubro);
			for (String dia : diasLocalSeleccionados) {
				Dias diaEnum = Dias.valueOf(dia);
				this.diasLocal.add((long) diaEnum.getValue());
			}
			for (String hora : horasLocalSeleccionados) {
				this.horasLocal.add(Long.parseLong(hora));
			}
			poiDTO.setDias((ArrayList<Long>) diasLocal);
			poiDTO.setHoras((ArrayList<Long>) horasLocal);
		} else if (this.getTipoPOI().equals(TiposPOI.BANCO)) {
			poiDTO.setSucursal(sucursal);
			poiDTO.setGerente(gerente);
			if (cercania != "")
				poiDTO.setCercania(Integer.parseInt(cercania));
			poiDTO.setServicios((ArrayList<NodoServicio>) servicios);
		} else if (this.getTipoPOI().equals(TiposPOI.PARADA_COLECTIVO)) {
			if (this.linea != "")
				poiDTO.setLinea(Integer.parseInt(linea));

		}
		
		// Creacion de POI
		POI nuevoPOI = poiDTO.converttoPOI();
		nuevoPOI.setEsLocal(true);
		
		// Persistiendo POI
		if (Repositorio.getInstance().pois().agregarPOI(nuevoPOI)) {
			// Mensaje de POI Creado
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('poiCreadoDialog').show();");
			reset();
		} else {
			// Mensaje de Error
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('poiCreadoDialogError').show();");
		}

	}

	public List<String> completeText(String query) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i <= 24; i++) {
			results.add(query + i);
		}

		return results;
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
		this.servicios.clear();
		this.diasLocal.clear();
		this.horasLocal.clear();
		this.etiquetas = "";
		this.horas.clear();
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
		RequestContext.getCurrentInstance().reset("form:panel");
	}

	public void listener() {
		tipoPOI = TiposPOI.valueOf(tipo);
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

	public void setCallePrincipal(String callePrinsipal) {
		this.callePrincipal = callePrinsipal;
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

	public List<NodoServicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<NodoServicio> servicios) {
		this.servicios = servicios;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
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

	public TiposPOI getTipoPOI() {
		return tipoPOI;
	}

	public void setTipoPOI(TiposPOI tipoPOI) {
		this.tipoPOI = tipoPOI;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getCercania() {
		return cercania;
	}

	public void setCercania(String cercania) {
		this.cercania = cercania;
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

	public List<String> getDias() {
		return dias;
	}

	public void setDias(List<String> dias) {
		this.dias = dias;
	}

	public String[] getDiasSeleccionados() {
		return diasSeleccionados;
	}

	public void setDiasSeleccionados(String[] diasSeleccionados) {
		this.diasSeleccionados = diasSeleccionados;
	}

	public NodoServicio getNodoServicioCreando() {
		return nodoServicioCreando;
	}

	public void setNodoServicioCreando(NodoServicio nodoServicioCreando) {
		this.nodoServicioCreando = nodoServicioCreando;
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

	public List<String> getHoras() {
		return horas;
	}

	public void setHoras(List<String> horas) {
		this.horas = horas;
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

}
