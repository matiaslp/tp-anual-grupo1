package ar.edu.utn.dds.grupouno.abmc.poi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.helpers.LevDist;
import ar.edu.utn.dds.grupouno.helpers.MetodosComunes;

@Entity
@Table(name = "LOCAL")
@PrimaryKeyJoinColumn(name = "id")
public class LocalComercial extends POI {

	//@OneToOne(cascade = CascadeType.ALL)
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "rubro_id")//, referencedColumnName = "id")
	Rubro rubro;
	@ElementCollection
	@CollectionTable(name = "LOCAL_DIAS")
	@Column(name = "dia")
	public List<Long> dias = new ArrayList<Long>();
	@ElementCollection
	@CollectionTable(name = "LOCAL_HORAS")
	@Column(name = "dia")
	public List<Long> horas = new ArrayList<Long>();

	public int getDistancia() {
		if (rubro != null)
			return rubro.getCercania();
		else
			return 0;
	}

	public LocalComercial(String nombre, double latitud, double longitud, Rubro rubro) {
		this.ubicacion = GeoLocation.fromDegrees(latitud, longitud);
		this.setNombre(nombre);
		this.setRubro(rubro);
		this.setTipo(TiposPOI.LOCAL_COMERCIAL);
	}

	public LocalComercial() {

	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public List<Long> getDias() {
		return this.dias;
	}

	public List<Long> getHoras() {
		return this.horas;
	}

	public void setDias(ArrayList<Long> diasDisponibles) {
		this.dias = diasDisponibles;
	}

	public void setHoras(ArrayList<Long> horasDisponibles) {
		this.horas = horasDisponibles;
	}

	public boolean disponible() {
		Calendar calendario = Calendar.getInstance();
		Iterator<Long> iteradorDias = dias.iterator(); // obtengo los dias
		while (iteradorDias.hasNext()) { // recorro los dias
			Long dia = iteradorDias.next(); // elijo un dia en particular
			if (calendario.get(Calendar.DAY_OF_WEEK) == dia) { // comparo si el
																// dia de hoy es
																// ese dia
				Iterator<Long> iteradorHoras = horas.iterator(); // obtengo
																	// las horas
				while (iteradorHoras.hasNext()) { // recorro las horas
					Long hora = iteradorHoras.next(); // elijo una hora en
														// particular
					if (calendario.get(Calendar.HOUR_OF_DAY) == hora) { // comparo
																		// si la
																		// hora
																		// de
																		// ahora
																		// es
																		// esa
																		// hora
						return true;
					} // sino paso a la siguiente hora
				} // sino paso al siguiente dia
			}
		} // sino es falso
		return false;

	}

	public void setDatos(POI_DTO dto, boolean esNuevo) {
		super.setDatos(dto);
		this.setDias(dto.getDias());
		this.setHoras(dto.getHoras());

		if (!esNuevo) {
			this.setRubro(dto.getRubro());
		}
	}

	@Override
	public boolean busquedaEstandar(String filtros[]) {

		if (super.busquedaEstandar(filtros)) {
			return true;
		}
		for (String filtro : filtros) {
			if (rubro != null && compararAtributo(filtro, rubro.getNombre())) {
				return true;
			} else {
				if (MetodosComunes.isNumeric(filtro)) {
					Long valor = Long.parseLong(filtro);
					if (dias.contains(valor)) {
						return true;
					} else if (horas.contains(valor)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean compararPOI(POI poi) {
		if (!super.compararPOI(poi)) {
			return false;
		}
		LocalComercial other = (LocalComercial) poi;
		if (rubro == null) {
			if (other.rubro != null)
				return false;
		} else if (rubro.getNombre().equals(other.rubro.getNombre())) {
			return true;
		}
		return false;
	}

}