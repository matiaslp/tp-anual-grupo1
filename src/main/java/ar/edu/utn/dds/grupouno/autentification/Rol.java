package ar.edu.utn.dds.grupouno.autentification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Rol")
public enum Rol {
	ADMIN, TERMINAL;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
