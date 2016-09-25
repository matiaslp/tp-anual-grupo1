package db;

import autentification.Usuario;

public class AgregarAccionesTransaction {
	
	private long id;
	private long userID;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public AgregarAccionesTransaction(long id, long userID) {
		super();
		this.id = id;
		this.userID = userID;
	}
	
	

}
