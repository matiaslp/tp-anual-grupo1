package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name = "getRolId", query = "SELECT id FROM Rol r WHERE r.value = :valor"),
@NamedQuery(name = "getRolById", query = "SELECT r FROM Rol r WHERE r.id = :id")})
public class Rol{

	protected String value;
	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable(name="FUNCIONALIDAD_ROL", 
		joinColumns={@JoinColumn(name="rol_id")}, 
		inverseJoinColumns={@JoinColumn(name="func_id")})
	protected List<Accion> Acciones;
	public Rol(){
		
	}
	public Rol (String value){
		this.value=value;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
}
