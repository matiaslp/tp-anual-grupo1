package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.modelo.Persistible;
import ar.edu.utn.dds.grupouno.modelo.PersistibleConNombre;

@Entity
@Table (name = "Accion")
public abstract class Accion extends Persistible{

	@ManyToMany (mappedBy="Acciones")
	protected List<Rol> Roles = new ArrayList<Rol>();
	@ManyToMany (mappedBy="funcionalidades")
	protected Set<Usuario> listaUsuarios = new HashSet<Usuario>();
	
	protected String nombre;
	protected boolean isProcess = false;

	protected boolean validarsesion(Usuario user, String Token) {
		return AuthAPI.getInstance().validarToken(Token) && user.getFuncionalidad(nombre)!=null;
	}

	public List<Rol> getRoles() {
		return Roles;
	}

	public void setRoles(ArrayList<Rol> roles) {
		Roles = roles;
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

}
