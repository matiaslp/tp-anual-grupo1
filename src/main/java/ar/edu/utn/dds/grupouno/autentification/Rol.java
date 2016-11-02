package ar.edu.utn.dds.grupouno.autentification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public enum Rol{
	ADMIN("ADMIN"),
	TERMINAL("TERMINAL");
	public String value;
	
	Rol (String value){
		this.value=value;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
}
