package autentification;

public enum Rol {
	ADMIN("admin", 1), TERMINAL("terminal", 2);

	private String nombre;
	private long id;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getID() {
		return id;
	}

	public void setID(long unId) {
		id = unId;
	}

	Rol(String unNombre, long unID) {
		this.nombre = unNombre;
		this.id = unID;
	}

}
