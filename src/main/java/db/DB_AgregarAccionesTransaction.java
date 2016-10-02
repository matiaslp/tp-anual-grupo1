package db;

import java.util.ArrayList;


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

	public boolean eliminarTransactions(long l) {

		for (AgregarAccionesTransaction Transaction : listTransactions) {
			if (Long.compare(Transaction.getId(), l) == 0) {
				listTransactions.remove(Transaction);
				return true;
			}
		}
		return false;
	}

	public void agregarTransactions(AgregarAccionesTransaction transaction) {
		transaction.setId(listTransactions.size() + 1);
		listTransactions.add(transaction);
	}

	public AgregarAccionesTransaction getTransactionsbyId(double d) {
		for (AgregarAccionesTransaction Transaction : listTransactions) {
			if (Long.compare(Transaction.getId(), (int) d) == 0)
				return Transaction;
		}
		return null;
	}

	public AgregarAccionesTransaction getLastTransactionByUser(long userID) {

		long tempId = 0;
		AgregarAccionesTransaction resultado = null;
		for (AgregarAccionesTransaction transaction : listTransactions) {
			if (Long.compare(transaction.getUserID(), userID) == 0 && transaction.getId() > tempId)
				resultado = transaction;
		}
		return resultado;
	}

}
