package procesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.DateTime;
import autentification.AuthAPI;
import autentification.Usuario;
import db.AgregarAccionesTransaction;
import db.DB_AgregarAccionesTransaction;
import db.DB_ResultadosProcesos;
import db.DB_Usuarios;
import db.Resultado;
import db.ResultadoProceso;
import procesos.Proceso;

public class AgregarAcciones extends Proceso {

	String filePath;

	public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, String file,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, unUser);
		filePath = file;
	}

	@Override
	public ResultadoProceso procesado() {

		// Obtenemos el tiempo de inicio de proceso
		DateTime start = new DateTime();
		// archivo esta de esta forma
		// unUsuario nomAccion nomAccion nomAccion
		String linea;
		String palabras[];
		String unUsername;
		ArrayList<String> listadoAcciones = new ArrayList<String>();
		FileReader fr = null;
		// Creamos la Transaccion
		AgregarAccionesTransaction Transaction = new AgregarAccionesTransaction(user.getID());
		// REVISA SI EXISTE O NO Y SI SE PUEDE LEER O NO
		try {

			if ((fr = new FileReader(filePath)) != null) {

				BufferedReader br = new BufferedReader(fr);
				// lee linea a linea
				while ((linea = br.readLine()) != null) {

					palabras = linea.split(" ");
					unUsername = palabras[0];

					// arma la lista de acciones para un usuario
					for (int i = 1; i < palabras.length; i++) {
						listadoAcciones.add(palabras[i]);
					}

					AgregarAcciones.AgregarAccionesAUsuario(unUsername, listadoAcciones, Transaction);

				}
				// Se agrega la transaccion a DB_AgregarAccionesTransaction
				DB_AgregarAccionesTransaction.getInstance().agregarTransactions(Transaction);
				br.close();
			}

			// Si el archivo no es encontrado
		} catch (FileNotFoundException e) {
			// Obtenemos el tiempo de fin de proceso
			DateTime end = new DateTime();
			// Armamos el Resultado del proceso que es guardado en DB_ResultadosProcesos
			ResultadoProceso resultado = new ResultadoProceso(start, end, this, user.getID(),
					"FileNotFoundException:No existe archivo " + filePath, Resultado.ERROR);
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			e.printStackTrace();
			return resultado;

			// Si el archivo no se puede leer (permisos)
		} catch (IOException e) {
			// Obtenemos el tiempo de fin de proceso
			DateTime end = new DateTime();
			// Armamos el Resultado del proceso que es guardado en DB_ResultadosProcesos
			ResultadoProceso resultado = new ResultadoProceso(start, end, this, user.getID(),
					"IOException:No se puede leer archivo " + filePath, Resultado.ERROR);
			DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
			e.printStackTrace();
			return resultado;
		}

		// Ejecucion exitosa
		// Obtenemos el tiempo de fin de proceso
		DateTime end = new DateTime();
		// Armamos el Resultado del proceso que es guardado en DB_ResultadosProcesos
		ResultadoProceso resultado = new ResultadoProceso(start, end, this, user.getID(), null, Resultado.OK);
		DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
		return resultado;
	}
	
	// Undo del ultimo proceso de AgregarAcciones ejecutado por el usuario que esta realizando el "undo"
	public void undo() {

		// obtenemos la ultima transaccion de este usuario
		AgregarAccionesTransaction transaction = DB_AgregarAccionesTransaction.getInstance()
				.getLastTransactionByUser(user.getID());
		// obtenemos la lista de cambios de la transaccion y la recorremos
		ArrayList<String> listadoCambios = transaction.getListadoCambios();
		for ( String cambio : listadoCambios) {
			String acciones[] = cambio.split(" ");
			String unUsername = acciones[0];
			acciones = Arrays.copyOfRange(acciones, 1, acciones.length);

			// Si el usuario existe
			if (DB_Usuarios.getInstance().buscarUsuarioEnLista(unUsername)) {
				Usuario unUsuario = DB_Usuarios.getInstance().getUsuarioByName(unUsername);

				// Remover todas las funcionalidades que fueron agregadas
				for (int i = 0; i < acciones.length; i++)
					AuthAPI.getInstance().sacarFuncionalidad(acciones[i], unUsuario);

			}
		}
		// Eliminamos la transacciones que fue rollbackeada
		DB_AgregarAccionesTransaction.getInstance().eliminarTransactions(transaction.getId());
	}

	static boolean AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones,
			AgregarAccionesTransaction transaction) {
		boolean agregoAccion = false;
		String transac = null;
		Usuario unUsuario;
		DB_Usuarios db_usuario = DB_Usuarios.getInstance();

		if (db_usuario.buscarUsuarioEnLista(unUsername)) {
			transac = unUsername;
			unUsuario = db_usuario.getUsuarioByName(unUsername);
			for (String unaAccion : listadoAcciones) {

				agregoAccion = AuthAPI.getInstance().agregarFuncionalidad(unaAccion, unUsuario);
				if (agregoAccion != false)
					// Agregar accion a transaccion
					transac = transac + " " + unaAccion;

			}
			// Agregamos a la transaccion los cambios realizados a este usuario
			transaction.agregarCambios(transac);
			return true;
		} else {
			return false;
		}

	}
}
