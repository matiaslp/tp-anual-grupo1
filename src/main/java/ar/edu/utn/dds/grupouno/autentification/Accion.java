package ar.edu.utn.dds.grupouno.autentification;

import java.util.ArrayList;

public abstract class Accion {

	protected ArrayList<Rol> Roles;
	protected String nombreFuncion;
	protected boolean isProcess = false;

	protected boolean validarsesion(Usuario user, String Token) {
		return AuthAPI.getInstance().validarToken(Token) && user.getFuncionalidad(nombreFuncion)!=null;
	}

	public ArrayList<Rol> getRoles() {
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
