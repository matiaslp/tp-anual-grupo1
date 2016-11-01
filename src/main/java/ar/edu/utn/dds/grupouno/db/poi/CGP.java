package ar.edu.utn.dds.grupouno.db.poi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.geolocation.GeoLocation;
import ar.edu.utn.dds.grupouno.helpers.LevDist;

@Entity
@Table(name = "CGP")
@PrimaryKeyJoinColumn(name="id")
public class CGP extends POI {

	String director;// 3
	String telefono;// 5

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

	public void setServicios(ArrayList<NodoServicio> servicios) {
		this.servicios = servicios;
	}

	public void agregarServicio(String nombre, ArrayList<Integer> dias, int horaInicio, int horaFin) {
		NodoServicio nuevoNodo = new NodoServicio();
		nuevoNodo.setNombre(nombre);
		nuevoNodo.listaDias = dias;
		nuevoNodo.horaInicio = horaInicio;
		nuevoNodo.horaFin = horaFin;
		servicios.add(nuevoNodo);
	}

	public boolean disponible(String servicio) {
		Calendar calendario = Calendar.getInstance();
		Iterator<NodoServicio> iterador = servicios.iterator();
		while (iterador.hasNext()) {
			// Agarro el proximo nodo, busco un nodo especifico o recorro todo
			// con ""
			NodoServicio nodo = iterador.next();
			if (nodo.getName().equals(servicio) || "".equals(servicio)) {

				if (nodo.listaDias.contains(calendario.get(Calendar.DAY_OF_WEEK))) {
					// chequear si el dia esta en la lista de dias disponibles
					// que el horario actual este disponible
					if (nodo.horaInicio <= calendario.get(Calendar.HOUR_OF_DAY)
							&& calendario.get(Calendar.HOUR_OF_DAY) < nodo.horaFin) { // chequear

						return true;
					}
				}
			}
		}
		return false;
	}

	public long getDistancia() {
		return cercania;
	}

	public void setDistancia(long distancia) {
		this.cercania = distancia;
	}

	// Se le pregunta a un POI si es cercano.
	@Override
	public boolean esCercano(POI poi) {

		long comuna1 = this.getComuna();
		long comuna2 = poi.getComuna();
		if (comuna1 == comuna2)
			return true;
		else
			return false;
	}

	public CGP(String nombre, double latitud, double longitud) {
		this.ubicacion = GeoLocation.fromDegrees(latitud, longitud);
		this.setNombre(nombre);
		this.setTipo(TiposPOI.CGP);
		this.servicios = new ArrayList<NodoServicio>();
	}
	
	public CGP() {
		
	}

	@Override
	public boolean busquedaEstandar(String filtros[]) {

		if (super.busquedaEstandar(filtros))
			return true;

		for (String filtro : filtros) {
			if (LevDist.calcularDistancia(filtro, this.director)) {
				return true;
			} else if (LevDist.calcularDistancia(filtro, this.telefono)) {
				return true;
			} else {
				this.buscarServicios(filtro);
			}
		}

		return false;
	}

	@Override
	public boolean compararPOI(POI poi) {
		if (!super.compararPOI(poi)) {
			return false;
		}

		CGP other = (CGP) poi;

		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public void setDatos(POI_DTO dto) {
		super.setDatos(dto);
		for (NodoServicio servicio : dto.getServicios()) {
			this.agregarServicio(servicio.getName(), servicio.getListaDias(), servicio.getHoraInicio(),
					servicio.getHoraFin());
		}
	}

	public ArrayList<NodoServicio> getServicios() {
		return servicios;
	}

	public void setServicio(NodoServicio servicio) {
		servicios.add(servicio);
	}

}
