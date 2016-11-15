package ar.edu.utn.dds.grupouno.db;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.edu.utn.dds.grupouno.repositorio.Persistible;
@Entity
@Table(name = "SESION")
@NamedQueries({
@NamedQuery(name = "Sesion.getSesionbyUser", query = "SELECT s FROM Sesion s WHERE s.username LIKE :susername"),
@NamedQuery(name = "Sesion.findAll", query = "SELECT s FROM Sesion s"),
@NamedQuery(name = "Sesion.validarToken", query = "SELECT s.username FROM Sesion s WHERE s.token LIKE :stoken"),
@NamedQuery(name = "Sesion.getSesionbyUserAndToken", query = "SELECT s FROM Sesion s WHERE s.username LIKE :susername AND s.token LIKE :stoken")})
public class Sesion extends Persistible{
	
	private String username;
	private String token;
	
	public Sesion(String token2, String user) {
		this.token = token2;
		this.username = user;
	}
	
	public Sesion(){
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
