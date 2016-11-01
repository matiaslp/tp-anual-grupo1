package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public abstract class Accion {

	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable(name="FUNCIONALIDAD_ROL", 
		joinColumns={@JoinColumn(name="func_id")}, 
		inverseJoinColumns={@JoinColumn(name="rol_id")})
	protected List<Rol> Roles;
	protected String nombreFuncion;
	protected boolean isProcess = false;

	protected boolean validarsesion(Usuario user, String Token) {
		return AuthAPI.getInstance().validarToken(Token) && user.getFuncionalidad(nombreFuncion)!=null;
	}

	public List<Rol> getRoles() {
		return Roles;
	}

	public void setRoles(ArrayList<Rol> roles) {
		Roles = roles;
	}

	public String getNombreFuncion() {
		return nombreFuncion;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	public boolean isProcess() {
		return isProcess;
	}

	public void setProcess(boolean isProcess) {
		this.isProcess = isProcess;
	}

}
