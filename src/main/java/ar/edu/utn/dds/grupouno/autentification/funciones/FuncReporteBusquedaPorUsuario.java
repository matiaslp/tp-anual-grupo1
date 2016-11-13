package ar.edu.utn.dds.grupouno.autentification.funciones;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.Entity;

import ar.edu.utn.dds.grupouno.autentification.Accion;
import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Rol;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.DB_HistorialBusquedas;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

@Entity
public class FuncReporteBusquedaPorUsuario extends Accion {

	public FuncReporteBusquedaPorUsuario(Rol rol) {
		Roles = new HashSet<Rol>();
		// Agregar Roles para esta funcionalidad
		Roles.add(rol);
		nombre = "reporteBusquedaPorUsuario";
	}
	
	public FuncReporteBusquedaPorUsuario(){
		
	}

	public ArrayList<Object[]> obtenerBusquedaPorUsuario(Usuario user, String Token) {
		if (validarsesion(user, Token))
			return Repositorio.getInstance().resultadosRegistrosHistoricos().reporteBusquedaPorUsuario();
		else
			return null;
	}

}
