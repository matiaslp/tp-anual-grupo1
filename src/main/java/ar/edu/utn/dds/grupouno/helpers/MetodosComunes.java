package ar.edu.utn.dds.grupouno.helpers;

public class MetodosComunes {

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
