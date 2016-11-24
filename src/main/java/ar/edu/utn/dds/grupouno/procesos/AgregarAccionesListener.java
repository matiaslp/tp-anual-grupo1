package ar.edu.utn.dds.grupouno.procesos;

import java.util.ArrayList;
import java.util.Arrays;

import org.quartz.JobListener;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.ProcesoListener;

public class AgregarAccionesListener extends ProcesoListener implements JobListener {

	@Override
	protected void rollback(Usuario usuario) {
		// obtenemos la ultima transaccion de este usuario
		AgregarAccionesTransaction transaction = DB_AgregarAccionesTransaction.getInstance()
				.getLastTransactionByUser(usuario.getId());
		if (transaction != null) {
			// obtenemos la lista de cambios de la transaccion y la recorremos
			ArrayList<String> listadoCambios = transaction.getListadoCambios();
			for (String cambio : listadoCambios) {
				String acciones[] = cambio.split(" ");
				String unUsername = acciones[0];
				acciones = Arrays.copyOfRange(acciones, 1, acciones.length);

				// Si el usuario existe
				if (Repositorio.getInstance().usuarios().getUsuarioByName(unUsername) != null) {
					Usuario unUsuario = Repositorio.getInstance().usuarios().getUsuarioByName(unUsername);

					// Remover todas las funcionalidades que fueron agregadas
					for (int i = 0; i < acciones.length; i++) {
						AuthAPI.getInstance().sacarFuncionalidad(acciones[i], unUsuario);
					}
					Repositorio.getInstance().usuarios().actualizarUsuarioConAcciones(unUsuario);
				}
			}
			// Eliminamos la transacciones que fue rollbackeada
			DB_AgregarAccionesTransaction.getInstance().eliminarTransactions(transaction.getId());
		}
	}

}
