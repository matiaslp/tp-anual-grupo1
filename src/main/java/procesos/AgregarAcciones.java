package procesos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.java.util.jar.pack.Package.File;

import antlr.collections.List;
import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import procesos.Proceso;

public class AgregarAcciones extends Proceso {

	String filePath;

	@Override
	public void execute() {
		// TODO Auto-generated method stub

		// lee prmer renglon
		// for
		// agrega funcionabilidad con metodo de auth api
		// lee siguiente renglon
	}

	public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, boolean disableAccion, String filePath,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		this.filePath = filePath;
		// archivo esta de esta forma
		// unUsuario nomAccion nomAccion nomAccion
		String linea;
		String palabras[];
		String unUsername;
		String listaAcciones[];

		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		// lee linea a linea
		while ((linea = br.readLine()) != null) {
			System.out.println(linea);

			palabras = linea.split(" ");
			unUsername = palabras[0];

			// arma la lista de acciones para un usuario
			for (int i = 1; i <= palabras.length; i++) {
				listaAcciones[i - 1] = palabras[i];
			}

			AgregarAcciones.AgregarAccionesAUsuario(unUsername, listaAcciones);
		}
		br.close();

	}

	// REVISAR
	public static boolean AgregarAccionesAUsuario(String unUsername, String[] listaAcciones) {
		boolean agregoAccion = false;
		Usuario unUsuario;
		AuthAPI unAuthAPI = AuthAPI.getInstance();
		if (unAuthAPI.buscarUsuarioEnLista(unUsername)) {
			unUsuario = unAuthAPI.consegirUsuarioDeLista(unUsername);
		} else {
			return false;
		}

		for (String unaAccion : listaAcciones) {

			agregoAccion = AuthAPI.agregarFuncionalidad(unaAccion, unUsuario);
			if (agregoAccion == false) {
				return false;
			}
		}
		return true;
	}

	private boolean validarAccionUsuarioTerminal(Accion accion) {
		return true;
	}

	private boolean validarAccionUsuarioAdministrador(Accion accion) {
		return true;
	}

}
