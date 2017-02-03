package ar.edu.utn.dds.grupouno.autentification;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.Persistible;

@Entity
@Table(name = "Accion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "T", discriminatorType = DiscriminatorType.STRING, length = 40)

@NamedQueries({ @NamedQuery(name = "Accion.findAll", query = "SELECT a FROM Accion a") })
@DiscriminatorValue("A")
public abstract class Accion extends Persistible {

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "FUNCIONALIDAD_ROL", joinColumns = { @JoinColumn(name = "func_id") }, inverseJoinColumns = {
			@JoinColumn(name = "rol_id") })
	protected Set<Rol> Roles = new HashSet<Rol>();
	@ManyToMany(mappedBy = "funcionalidades")
	protected Set<Usuario> listaUsuarios = new HashSet<Usuario>();

	@Column(name = "nombre")
	protected String nombre;
	protected boolean isProcess = false;

	protected boolean validarsesion(Usuario user, String Token) {
		return AuthAPI.getInstance().validarToken(Token) && user.getFuncionalidad(nombre) != null;
	}

	public Set<Rol> getRoles() {
		return Roles;
	}

	public void setRoles(Set<Rol> roles) {
		Roles = roles;
	}

	public void setRol(Rol rol) {
		this.Roles.add(rol);
	}

	public String getNombreFuncion() {
		return nombre;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombre = nombreFuncion;
	}

	public boolean isProcess() {
		return isProcess;
	}

	public void setProcess(boolean isProcess) {
		this.isProcess = isProcess;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
