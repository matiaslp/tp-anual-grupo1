package ar.edu.utn.dds.grupouno.autentification;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ROL")
@NamedQueries({ @NamedQuery(name = "getRolId", query = "SELECT id FROM Rol r WHERE r.value = :valor"),
		@NamedQuery(name = "getRolById", query = "SELECT r FROM Rol r WHERE r.id = :id"),
		@NamedQuery(name = "Rol.getRolByName", query = "SELECT r FROM Rol r WHERE r.value LIKE :rnombre"),
		@NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r") })
public class Rol {

	@Column(name = "value")
	protected String value;
	@ManyToMany(mappedBy = "Roles")
	protected Set<Accion> Acciones;

	public Rol() {

	}

	public Rol(String value) {
		this.value = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<Accion> getAcciones() {
		return Acciones;
	}

	public void setAcciones(Set<Accion> acciones) {
		Acciones = acciones;
	}

}
