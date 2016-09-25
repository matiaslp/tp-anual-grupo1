package db;

import java.util.ArrayList;

import poi.POI;

public class DB_AgregarAccionesTransaction {

	private static ArrayList<AgregarAccionesTransaction> listTransactions;
	private static DB_AgregarAccionesTransaction instance = null;

	public DB_AgregarAccionesTransaction() {
		listTransactions = new ArrayList<AgregarAccionesTransaction>();
	}

	public static DB_AgregarAccionesTransaction getInstance() {
		if (instance == null)
			instance = new DB_AgregarAccionesTransaction();
		return instance;
	}

	public static boolean eliminarTransactions(int d) {

		for (AgregarAccionesTransaction Transaction : listTransactions) {
			if (Long.compare(Transaction.getId(), d) == 0) {
				listTransactions.remove(Transaction);
				return true;
			}
		}
		return false;
	}

	public static boolean agregarTransactions(AgregarAccionesTransaction Transaction) {
		try {
			// testear
			Transaction.setId(listTransactions.size() + 1);
			listTransactions.add(Transaction);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public AgregarAccionesTransaction getTransactionsbyId(double d) {
		for (AgregarAccionesTransaction Transaction : listTransactions) {
			if (Long.compare(Transaction.getId(), (int) d) == 0)
				return Transaction;
		}
		return null;
	}
	
	
	
}
